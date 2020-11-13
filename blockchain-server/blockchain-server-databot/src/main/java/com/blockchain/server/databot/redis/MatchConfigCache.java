package com.blockchain.server.databot.redis;

import com.blockchain.server.databot.common.redisson.RedissonTool;
import com.blockchain.server.databot.entity.MatchConfig;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class MatchConfigCache {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private RedissonClient redissonClient;

    private static final int LOCK_WAIT_TIME = 5; //获取锁等待时间
    private static final int LOCK_LEASE_TIME = 5; //释放时间
    private static final String MATCH_CONFIG_CACHE = "bot:match:config:{0}-{1}"; //撮合机器人配置列表数据key
    private static final String MATCH_CONFIG_LOCK_CACHE = "bot:match:config:distributed:lock:{0}-{1}";//撮合机器人配置数据锁key

    /***
     * 获取撮合机器人配置列表数据Key
     * @return
     */
    public String getKey(String coinName, String unitName) {
        return MessageFormat.format(MATCH_CONFIG_CACHE, coinName, unitName);
    }

    /***
     * 获取撮合机器人配置数据锁Key
     * @param coinName
     * @param unitName
     * @return
     */
    public String getLockKey(String coinName, String unitName) {
        return MessageFormat.format(MATCH_CONFIG_LOCK_CACHE, coinName, unitName);
    }

    /***
     * 获取撮合机器人配置数据
     * @param key
     * @return
     */
    public MatchConfig getValue(String key) {
        return (MatchConfig) redisTemplate.opsForValue().get(key);
    }

    /***
     * 设置撮合机器人配置数据
     * @param matchConfig
     */
    public void setValue(String key, MatchConfig matchConfig) {
        redisTemplate.opsForValue().set(key, matchConfig);
    }

    /***
     * 是否存在Key
     * @param key
     * @return
     */
    public boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    /***
     * 获取分布式锁
     * @param lockKey
     * @return
     */
    public boolean tryFairLock(String lockKey) {
        return RedissonTool.tryFairLock(redissonClient, lockKey,
                LOCK_WAIT_TIME, LOCK_LEASE_TIME, TimeUnit.SECONDS);
    }

    /***
     * 释放锁
     * @param lockKey
     */
    public void unFairLock(String lockKey) {
        RedissonTool.unFairLock(redissonClient, lockKey);
    }
}
