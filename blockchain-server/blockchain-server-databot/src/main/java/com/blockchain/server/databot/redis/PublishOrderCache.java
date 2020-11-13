package com.blockchain.server.databot.redis;

import com.blockchain.common.base.dto.MarketDTO;
import com.blockchain.server.databot.common.redisson.RedissonTool;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public class PublishOrderCache {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private RedissonClient redissonClient;

    private static final int LOCK_WAIT_TIME = 5; //获取锁等待时间
    private static final int LOCK_LEASE_TIME = 5; //释放时间
    private static final String ORDER_LIST_CACHE = "cct:list:order:{0}:{1}-{2}";//本地系统盘口数据key
    private static final String ORDER_LOCK_LIST_CACHE = "cct:distributed:lock:order:{0}:{1}-{2}";//本地系统盘口数据Key的分布式锁

    /***
     * 获取订单列表key
     * @param coinName
     * @param unitName
     * @param orderType
     * @return
     */
    public static String getOrderListKey(String coinName, String unitName, String orderType) {
        return MessageFormat.format(ORDER_LIST_CACHE, orderType, coinName, unitName);
    }

    /***
     * 获取订单列表锁key
     * @param coinName
     * @param unitName
     * @param orderType
     * @return
     */
    public static String getOrderLockListKey(String coinName, String unitName, String orderType) {
        return MessageFormat.format(ORDER_LOCK_LIST_CACHE, orderType, coinName, unitName);
    }

    /***
     * 根据分数获取sortSet
     * @param coinName
     * @param unitName
     * @param orderType
     * @param score
     * @return
     */
    public Set getZSetByScore(String coinName, String unitName, String orderType, double score) {
        return redisTemplate.opsForZSet().rangeByScore(getOrderListKey(coinName, unitName, orderType), score, score);
    }

    /***
     * 升序获取sortSet列表
     * @param coinName
     * @param unitName
     * @param orderType
     * @param start
     * @param end
     * @return
     */
    public Set getZSetASC(String coinName, String unitName, String orderType, int start, int end) {
        return redisTemplate.opsForZSet().range(getOrderListKey(coinName, unitName, orderType), start, end);
    }

    /***
     * 降序获取sorcSet列表
     * @param coinName
     * @param unitName
     * @param orderType
     * @param start
     * @param end
     * @return
     */
    public Set getZSetDESC(String coinName, String unitName, String orderType, int start, int end) {
        return redisTemplate.opsForZSet().reverseRange(getOrderListKey(coinName, unitName, orderType), start, end);
    }

    /***
     * 设值进sortSet中
     * @param coinName
     * @param unitName
     * @param orderType
     * @param order
     * @param score
     * @return
     */
    public boolean setZSetValue(String coinName, String unitName, String orderType, MarketDTO order, double score) {
        return redisTemplate.opsForZSet().add(getOrderListKey(coinName, unitName, orderType), order, score);
    }

    /***
     * 设值进sortSet中
     * @param coinName
     * @param unitName
     * @param orderType
     * @param tuples
     * @return
     */
    public long setZSetValue(String coinName, String unitName, String orderType, Set<ZSetOperations.TypedTuple<MarketDTO>> tuples) {
        return redisTemplate.opsForZSet().add(getOrderListKey(coinName, unitName, orderType), tuples);
    }

    /***
     * 根据分数删除sortSet中的对象
     * @param coinName
     * @param unitName
     * @param orderType
     * @param score
     * @return
     */
    public long removeZSetByScore(String coinName, String unitName, String orderType, double score) {
        return redisTemplate.opsForZSet().removeRangeByScore(getOrderListKey(coinName, unitName, orderType), score, score);
    }

    /***
     * 根据索引删除sortSet中的对象
     * @param coinName
     * @param unitName
     * @param orderType
     * @param start
     * @param end
     * @return
     */
    public long removeZSetByRange(String coinName, String unitName, String orderType, int start, int end) {
        return redisTemplate.opsForZSet().removeRange(getOrderListKey(coinName, unitName, orderType), start, end);
    }

    /***
     * 是否存在Key
     * @param coinName
     * @param unitName
     * @param orderType
     * @return
     */
    public boolean hasKey(String coinName, String unitName, String orderType) {
        return redisTemplate.hasKey(getOrderListKey(coinName, unitName, orderType));
    }

    /***
     * 获取分布式锁
     * @param coinName
     * @param unitName
     * @param orderType
     * @return
     */
    public boolean tryFairLock(String coinName, String unitName, String orderType) {
        return RedissonTool.tryFairLock(redissonClient, getOrderLockListKey(coinName, unitName, orderType), LOCK_WAIT_TIME, LOCK_LEASE_TIME, TimeUnit.SECONDS);
    }

    /***
     * 释放锁
     * @param coinName
     * @param unitName
     * @param orderType
     */
    public void unFairLock(String coinName, String unitName, String orderType) {
        RedissonTool.unFairLock(redissonClient, getOrderLockListKey(coinName, unitName, orderType));
    }

}
