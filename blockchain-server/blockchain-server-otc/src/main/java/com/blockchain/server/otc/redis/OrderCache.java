package com.blockchain.server.otc.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.concurrent.TimeUnit;

@Component
public class OrderCache {

    @Autowired
    private RedisTemplate redisTemplate;

    //新建订单key的前缀，用于自动撤单
    private static final String REDIS_KEY_NEW_ORDER_PRE = "otc:new:order:";
    //新建订单key
    private static final String REDIS_KEY_NEW_ORDER = REDIS_KEY_NEW_ORDER_PRE + "{0}";

    /***
     * 设置新建订单id到redis中
     * 失效时间通过外部传递
     * 失效时间的单位是：分钟
     *
     * @param orderId 订单id
     * @param offset 失效时间/分钟
     */
    public void setNewOrderCache(String orderId, long offset) {
        String key = this.getNewOrderKey(orderId);
        redisTemplate.opsForValue().set(key, System.currentTimeMillis(), offset, TimeUnit.MINUTES);
    }

    /***
     * 获取新建订单key前缀
     * @return
     */
    public static String getNewOrderPre() {
        return REDIS_KEY_NEW_ORDER_PRE;
    }

    /***
     * 获取新建订单key
     * @param orderId
     * @return
     */
    public static String getNewOrderKey(String orderId) {
        return MessageFormat.format(REDIS_KEY_NEW_ORDER, orderId);
    }
}
