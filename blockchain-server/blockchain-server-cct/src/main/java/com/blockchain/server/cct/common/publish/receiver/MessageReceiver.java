package com.blockchain.server.cct.common.publish.receiver;

import com.blockchain.common.base.exception.RPCException;
import com.blockchain.server.cct.common.enums.CctDataEnums;
import com.blockchain.server.cct.common.exception.CctException;
import com.blockchain.server.cct.common.redisson.RedissonTool;
import com.blockchain.server.cct.entity.PublishOrder;
import com.blockchain.server.cct.service.MatchService;
import com.blockchain.server.cct.service.PublishOrderService;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class MessageReceiver {
    @Autowired
    private PublishOrderService publishOrderService;
    @Autowired
    private MatchService matchService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedissonClient redissonClient;

    //撮合次数
    private static final int MATCH_COUNT = 5;
    //日志
    private static final Logger LOG = LoggerFactory.getLogger(MessageReceiver.class);

    /**
     * 自动撮合发布订阅的订阅者
     * 接收一个key进行获取分布式锁
     *
     * @param orderKey
     */
    public void autoMatch(String orderKey) {
        //锁key名（锁单价）
        String lockKey = CctDataEnums.REDIS_LOCK_NUM_KEY.getStrVlue() + orderKey;
        //redis集合中存在的单价key名
        String redisListKey = CctDataEnums.REDIS_LIST_KEY.getStrVlue() + orderKey;

        //判断单价的key的集合长度是否为0
        long size = stringRedisTemplate.opsForList().size(redisListKey);
        if (size == 0) {
            return;
        }

        //获得锁（公平锁）
        boolean isgetLock = RedissonTool.tryFairLock(redissonClient, lockKey, 0, 5, TimeUnit.SECONDS);
        try {
            //获取成功，进行撮合
            if (isgetLock) {
                //自旋，将集合最右的对象放到最左
                String orderId = stringRedisTemplate.opsForList().rightPopAndLeftPush(redisListKey, redisListKey);
                //撮合
                match(orderId, redisListKey);
                //释放锁（公平锁）
                RedissonTool.unFairLock(redissonClient, lockKey);
                //另启一条线程进行递归
                new Thread(() -> {
                    //递归当前撮合方法
                    autoMatch(orderKey);
                }).start();
            }
        } catch (Exception e) {
            LOG.error("撮合入口方法异常：orderId:{}", orderKey);
        }
    }

    /***
     * 撮合逻辑
     *
     * @param orderId
     * @param redisListKey
     */
    private void match(String orderId, String redisListKey) {
        PublishOrder order = publishOrderService.selectById(orderId);
        //撮合第一步将撮合订单更新为已撮合状态
        //防止无法进入撮合方法内部，导致该订单一直为新建状态
        boolean isUpdate = updateOrderStatusToMatch(order, redisListKey);
        if (!isUpdate) {
            return;
        }

        //缓存撮合次数，并判断撮合次数是否过大
//        boolean isOversize = checkMatchCount(orderId, redisListKey, order.getPublishType());
//        if (!isOversize) {
//            return;
//        }

        //根据id查询匹配的撮合订单
        List<PublishOrder> matchs = matchService.listBeMatchOrder(orderId);

        //判断可撮合订单列表是否为空·
        boolean isNullBeMatch = checkBeMatchListSize(matchs.size(), orderId, redisListKey, order.getPublishType());
        if (!isNullBeMatch) {
            return;
        }

        //迭代撮合
        for (PublishOrder bymatch : matchs) {
            //锁key名（锁订单id）
            String lockKey = CctDataEnums.REDIS_LOCK_ORDERID_KEY.getStrVlue() + bymatch.getId();
            //获得被撮合对象订单id的锁（公平锁）
            boolean isgetLock = RedissonTool.tryFairLock(redissonClient, lockKey, 0, 5, TimeUnit.SECONDS);
            //获取成功
            if (isgetLock) {
                try {
                    //开始撮合
                    boolean flag = matchService.handleMatch(orderId, bymatch.getId());
                    //判断是否可以继续迭代撮合
                    if (!flag) {
                        break;
                    }
                } catch (RPCException | CctException e) {
                    //捕获远程调用异常
                    e.printStackTrace();
                    //如果是市价订单，进行撤单
                    cancelMarketOrder(order.getPublishType(), orderId);
                    //删除key并退出方法
                    stringRedisTemplate.opsForList().remove(redisListKey, 1, orderId);
                    //结束方法
                    return;
                } finally {
                    //释放被撮合对象订单id的锁（公平锁）
                    RedissonTool.unFairLock(redissonClient, lockKey);
                }
            } else {
                //跳过当次循环
                continue;
            }
        }

        //检查订单是否撮合完毕可以结束
        isMatchEnd(orderId, redisListKey);
    }

    /***
     * 将订单更新为已撮合状态
     * 撮合时第一步将订单改为已撮合状态
     *
     * @param order
     * @param redisListKey
     * @return
     */
    private boolean updateOrderStatusToMatch(PublishOrder order, String redisListKey) {
        //防空
        if (order == null) {
            //删除key并退出方法
            stringRedisTemplate.opsForList().remove(redisListKey, 1, order.getId());
            return false;
        }
        //已撮合，不操作返回更新成功标识
        if (order.getOrderStatus().equals(CctDataEnums.ORDER_STATUS_MATCH.getStrVlue())) {
            return true;
        }

        //使用乐观锁更新
        publishOrderService.updateStatusInVersionLock(order.getId(),
                CctDataEnums.ORDER_STATUS_NEW.getStrVlue(), CctDataEnums.ORDER_STATUS_MATCH.getStrVlue());

        return true;
    }

    /***
     * 校验订单剩余交易额是否为0
     * 如果不是，继续撮合
     * 是，结束撮合删除redis中的对象并发布信息至前端
     *
     * @param orderId
     * @param redisListKey
     */
    private void isMatchEnd(String orderId, String redisListKey) {
        //排他锁查询
        PublishOrder order = publishOrderService.selectByIdForUpdate(orderId);

        //市价卖单时判断剩余数量，其余判断剩余交易额
        //如果有剩余数量就继续撮合，否则删除redislist对象，并广播信息到前端
        if (order.getPublishType().equals(CctDataEnums.PUBLISH_TYPE_MARKET.getStrVlue()) &&
                order.getOrderType().equals(CctDataEnums.ORDER_TYPE_SELL.getStrVlue())) {

            if (order.getLastNum().compareTo(BigDecimal.ZERO) > 0) {
                //递归方法，继续撮合
                match(orderId, redisListKey);
            } else {
                //删除该集合中的第一个对象
                stringRedisTemplate.opsForList().remove(redisListKey, 1, orderId);
            }
        } else {
            if (order.getLastTurnover().compareTo(BigDecimal.ZERO) > 0) {
                //递归方法，继续撮合
                match(orderId, redisListKey);
            } else {
                //删除该集合中的第一个对象
                stringRedisTemplate.opsForList().remove(redisListKey, 1, orderId);
            }
        }
    }

    /***
     * 判断撮合次数是否超过限制
     *
     * @param orderId
     * @param redisListKey
     * @param publishType
     * @return
     */
    private boolean checkMatchCount(String orderId, String redisListKey, String publishType) {
//        //订单撮合次数的Key
//        String redisMatchCountKey = CctDataEnums.REDIS_MATCH_COUNT_KEY.getStrVlue() + orderId;
//        //判断key是否存在
//        Boolean hasKey = stringRedisTemplate.hasKey(redisMatchCountKey);
//
//        //如果存在对应的key
//        if (hasKey) {
//            //获取撮合次数
//            Integer count = Integer.valueOf(stringRedisTemplate.opsForValue().get(redisMatchCountKey));
//            //如果撮合次数大于X次
//            if (count > MATCH_COUNT) {
//                //如果是市价订单，进行撤单
//                cancelMarketOrder(publishType, orderId);
//                //删除订单key并退出方法
//                stringRedisTemplate.opsForList().remove(redisListKey, 1, orderId);
//                //删除撮合次数key
//                stringRedisTemplate.delete(redisMatchCountKey);
//                return false;
//            } else {
//                //撮合次数自增一
//                stringRedisTemplate.opsForValue().increment(redisMatchCountKey, 1);
//            }
//        } else {
//            //不存在对应的key，新增一个并赋予初始值
//            stringRedisTemplate.opsForValue().increment(redisMatchCountKey, 1);
//        }
//
        return true;
    }

    /***
     * 判断被撮合订单列表是否存在
     *
     * @param size
     * @param orderId
     * @param redisListKey
     * @param publishType
     * @return
     */
    private boolean checkBeMatchListSize(int size, String orderId, String redisListKey, String publishType) {
        //可撮合列表长度为0
        if (size == 0) {
            //如果市价撮合没有订单没有匹配的订单，进行撤单
            cancelMarketOrder(publishType, orderId);
            //删除key并退出方法
            stringRedisTemplate.opsForList().remove(redisListKey, 1, orderId);
            return false;
        }
        return true;
    }

    /***
     * 撤销市价订单
     *
     * @param publishType
     * @param orderId
     */
    private void cancelMarketOrder(String publishType, String orderId) {
        if (publishType.equals(CctDataEnums.PUBLISH_TYPE_MARKET.getStrVlue())) {
            publishOrderService.handleCancelOrder(orderId);
        }
    }
}
