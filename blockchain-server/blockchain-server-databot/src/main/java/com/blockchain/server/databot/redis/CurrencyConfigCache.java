package com.blockchain.server.databot.redis;

import com.blockchain.server.databot.common.redisson.RedissonTool;
import com.blockchain.server.databot.entity.CurrencyConfig;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class CurrencyConfigCache {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private RedissonClient redissonClient;

    private static final int LOCK_WAIT_TIME = 5; //获取锁等待时间
    private static final int LOCK_LEASE_TIME = 5; //释放时间
    private static final String CURRENCY_CONFIG_LIST_CACHE = "bot:currency:config";//币对配置列表数据key
    private static final String CURRENCY_CONFIG_LOCK_CACHE = "bot:currency:config:distributed:lock";//币对配置数据锁key
    private static final String CURRENCY_K_DAY_TOTAL_AMOUNT_CACHE = "bot:currency:config:kamount:{0}"; //K线每日总数量key
    private static final String CURRENCY_K_DAY_TOTAL_AMOUNT_LOCK_CACHE = "bot:currency:config:kamount:distributed:lock:{0}"; //K线每日总数量锁key

    /***
     * 获取K线每日总数量Key
     * @param currencyPair
     * @return
     */
    public String getCurrencyKDayTotalAmountKey(String currencyPair) {
        return MessageFormat.format(CURRENCY_K_DAY_TOTAL_AMOUNT_CACHE, currencyPair);
    }

    /***
     * 获取币对配置列表数据Key
     * @return
     */
    public String getCurrencyConfigListKey() {
        return CURRENCY_CONFIG_LIST_CACHE;
    }

    /***
     * 获取币对配置列表数据锁key
     * @return
     */
    public String getCurrencyConfigLockKey() {
        return CURRENCY_CONFIG_LOCK_CACHE;
    }

    /***
     * 获取K线每日总数量锁Key
     * @param currencyPair
     * @return
     */
    public String getCurrencyKDayAmountLockKey(String currencyPair) {
        return MessageFormat.format(CURRENCY_K_DAY_TOTAL_AMOUNT_LOCK_CACHE, currencyPair);
    }

    /***
     * 获取Hash数据
     * 通过迭代器封装List返回
     * @param status
     * @return List
     */
    public List<CurrencyConfig> getHashToList(String status) {
        //获取Hash的迭代器
        Cursor<Map.Entry<String, CurrencyConfig>> scan = redisTemplate.opsForHash().scan(CURRENCY_CONFIG_LIST_CACHE, ScanOptions.NONE);
        //返回数据类getHistoryList
        List<CurrencyConfig> list = new ArrayList<>();
        //遍历，封装返回数据
        while (scan.hasNext()) {
            CurrencyConfig currency = scan.next().getValue();
            //判断状态
            if (!currency.getStatus().equals(status)) {
                continue;
            }
            list.add(currency);
        }
        return list;
    }

    /***
     * 判断是否存在币对配置key是否存在
     * @return
     */
    public boolean hasConfigListKey() {
        return redisTemplate.hasKey(CURRENCY_CONFIG_LIST_CACHE);
    }

    /***
     * 判断是否存在K线数量key是否存在
     * @param currencyPair
     * @return
     */
    public boolean hasKDayAmountKey(String currencyPair) {
        return redisTemplate.hasKey(this.getCurrencyKDayTotalAmountKey(currencyPair));
    }

    /***
     * 添加数据进缓存中
     * @param hashKey
     * @param value
     */
    public void setHashValue(String redisKey, String hashKey, Object value) {


        redisTemplate.opsForHash().put(redisKey, hashKey, value);
    }


    /***
     * 获取K线总数量缓存
     * @param currencyPair
     * @param hashKey
     * @return
     */
    public BigDecimal getHashKDayAmount(String currencyPair, String hashKey) {
        return (BigDecimal) redisTemplate.opsForHash().get(this.getCurrencyKDayTotalAmountKey(currencyPair), hashKey);
    }

    /***
     * 获取分布式锁
     * @return
     */
    public boolean tryFairLock(String redisLockKey) {
        return RedissonTool.tryFairLock(redissonClient, redisLockKey,
                LOCK_WAIT_TIME, LOCK_LEASE_TIME, TimeUnit.SECONDS);
    }

    /***
     * 释放锁
     */
    public void unFairLock(String redisLockKey) {
        RedissonTool.unFairLock(redissonClient, redisLockKey);
    }
}
