package com.blockchain.server.currency.redis;

import com.blockchain.server.currency.dto.CurrencyMarketKDTO;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.SortedMap;
import java.util.concurrent.TimeUnit;

/**
 * \* <p>Desciption:</p>
 * \* CreateTime: 2019/4/13 11:56
 * \* User: XianChaoWei
 * \* Version: V1.0
 * \
 */
@Component
public class MarketKCache {
    private static final String MARKET_LIST_CACHE = "market:list:";
    private static final String MARKET_LIST_LOCK_CACHE = "market:list:lock:";
    private static final int LOCK_WAIT_TIME = 10;//获取锁等待时间
    private static final int LOCK_LEASE_TIME = 10;//释放时间

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 尝试获取K线缓存行情锁
     * @param redisson
     * @param currencyPair
     * @param timeType
     * @param timeNumber
     */
    public boolean tryFairLock(RedissonClient redisson, String currencyPair, String timeType, int timeNumber){
        return RedissonTool.tryFairLock(redisson,
                MARKET_LIST_LOCK_CACHE + currencyPair + ":" + timeNumber + timeType,
                LOCK_WAIT_TIME,LOCK_LEASE_TIME, TimeUnit.SECONDS);
    }

    /**
     * 释放K线缓存行情锁
     * @param redisson
     * @param currencyPair
     * @param timeType
     * @param timeNumber
     */
    public void unFairLock(RedissonClient redisson,String currencyPair, String timeType, int timeNumber){
        RedissonTool.unFairLock(redisson, MARKET_LIST_LOCK_CACHE + currencyPair + ":" + timeNumber + timeType);
    }

    public void setMarketKListCache(SortedMap<String,CurrencyMarketKDTO> map, String currencyPair, String timeType, int timeNumber){
        redisTemplate.opsForValue().set(MARKET_LIST_CACHE + currencyPair + ":" + timeNumber + timeType
                ,map);
    }

    public SortedMap<String,CurrencyMarketKDTO> getMarketKListCache(String currencyPair, String timeType, int timeNumber){
        String key = MARKET_LIST_CACHE + currencyPair + ":" + timeNumber + timeType;
        if (redisTemplate.hasKey(key)) {
            SortedMap<String,CurrencyMarketKDTO> map = (SortedMap<String,CurrencyMarketKDTO>) redisTemplate.opsForValue().get(key);
            return map;
        }
        return null;
    }

    public void removeMarketKListCache(String currencyPair,String timeType,int timeNumber){
        String key = MARKET_LIST_CACHE + currencyPair + ":" + timeNumber + timeType;
        if(redisTemplate.hasKey(key))
            redisTemplate.delete(key);
    }
}
