package com.blockchain.server.cct.common.redistool;

import com.blockchain.server.cct.common.enums.CctDataEnums;
import com.blockchain.server.cct.service.PublishOrderService;
import com.blockchain.server.cct.service.TradingRecordService;
import com.blockchain.server.cct.service.impl.PublishOrderServiceImpl;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***
 * redis工具类
 */
@Component
public class RedisTool {

    @Autowired
    private PublishOrderService orderService;
    @Autowired
    private TradingRecordService recordService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    private static final String BUY_ORDERS_KSY = "BUY_ORDERS";
    private static final String SELL_ORDERS_KSY = "SELL_ORDERS";

    /***
     * 发送广播逻辑
     *
     * @param coinName
     * @param unitName
     */
    public void send(String coinName, String unitName) {
//        //间隔时间
//        Object intervalTime = redisTemplate.opsForValue().get(CctDataEnums.REDIS_SEND_INTERVAL.getStrVlue());
//        //如果不存在间隔时间
//        if (intervalTime == null) {
//            //设置时间
//            redisTemplate.opsForValue().set(CctDataEnums.REDIS_SEND_INTERVAL.getStrVlue(), new Date().getTime() + "");
//            //发送消息
//            template.convertAndSend(CctDataEnums.SUBSCRIPTION.getStrVlue(), payload);
//            return;
//        }
//        //如果发送时间间隔太短
//        if (new Date().getTime() - Long.valueOf(intervalTime.toString()) < CctDataEnums.REDIS_SEND_INTERVAL.getIntValue()) {
//            //返回
//            return;
//        }

        //盘口买单
        List buyOrders = listBuyOrder(coinName, unitName);
        //盘口卖单
        List sellOrders = listSellOrder(coinName, unitName);

        //封装返回前端参数
        Map<String, Object> sendObj = new HashMap<>();
        sendObj.put(BUY_ORDERS_KSY, buyOrders);
        sendObj.put(SELL_ORDERS_KSY, sellOrders);

        //发送消息，根据币种推送对应的通道
        simpMessagingTemplate.convertAndSend(CctDataEnums.SUBSCRIPTION.getStrVlue() + "/" + coinName + "-" + unitName, sendObj);
//        //重新设置间隔时间
//        redisTemplate.opsForValue().set(CctDataEnums.REDIS_SEND_INTERVAL.getStrVlue(), new Date().getTime() + "");
    }
    private static final Logger LOG = LoggerFactory.getLogger(RedisTool.class);
    /***
     * 查询盘口买单
     * @param coinName
     * @param unitName
     * @return
     */
    public List listBuyOrder(String coinName, String unitName) {
        List publishOrders = null;
        LOG.info("coinName:"+coinName+"unitName"+unitName);
        //查询如果是量化交易，从火币获取数据构造返回前端
        if (getTradingOn(coinName, unitName)) {
            LOG.info("get HUOBI");
            String key = CctDataEnums.REDIS_HUOBI_ORDER.getStrVlue() + "BUY:" + coinName + "-" + unitName;
            publishOrders = (List) redisTemplate.opsForValue().get(key);
        } else {
            publishOrders = orderService.listOrder(
                    coinName, unitName, CctDataEnums.ORDER_TYPE_BUY.getStrVlue(),
                    CctDataEnums.ORDER_STATUS_MATCH.getStrVlue(),
                    CctDataEnums.PUBLISH_TYPE_LIMIT.getStrVlue(), CctDataEnums.SORT_DESC.getStrVlue());
        }
        return publishOrders;
    }

    /***
     * 查询盘口卖单
     * @param coinName
     * @param unitName
     * @return
     */
    public List listSellOrder(String coinName, String unitName) {
        List publishOrders = null;
        //查询如果是量化交易，从火币获取数据构造返回前端
        if (getTradingOn(coinName, unitName)) {
            String key = CctDataEnums.REDIS_HUOBI_ORDER.getStrVlue() + "SELL:" + coinName + "-" + unitName;
            publishOrders = (List) redisTemplate.opsForValue().get(key);
        } else {
            publishOrders = orderService.listOrder(
                    coinName, unitName, CctDataEnums.ORDER_TYPE_SELL.getStrVlue(),
                    CctDataEnums.ORDER_STATUS_MATCH.getStrVlue(),
                    CctDataEnums.PUBLISH_TYPE_LIMIT.getStrVlue(), CctDataEnums.SORT_ASC.getStrVlue());
        }

        return publishOrders;
    }

    /***
     * 查询成交记录
     * @param coinName
     * @param unitName
     * @return
     */
    public List listRecord(String coinName, String unitName, Integer pageNum, Integer pageSize) {
        //查询如果是量化交易，从redis拿到最新成交价数据 cct:huobi:record:BTC-ETH
        if (getTradingOn(coinName, unitName)) {
            String key = CctDataEnums.REDIS_HUOBI_RECORD.getStrVlue() + coinName + "-" + unitName;
            return (List) redisTemplate.opsForValue().get(key);
        } else {
            PageHelper.startPage(pageNum, pageSize);
            return recordService.listRecordByCoinAndUnit(coinName, unitName);
        }
    }

    /***
     * 检查币对是否是量化交易币对
     * @param coinName
     * @param unitName
     * @return
     */
    private boolean getTradingOn(String coinName, String unitName) {
        return orderService.getTradingOn(coinName, unitName);
    }
}
