package com.blockchain.server.databot.common.redisson;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import java.util.concurrent.TimeUnit;

public class RedissonTool {

    /***
     * 获得公平锁
     * @param redisson
     * @param lockKey 锁key名
     * @return
     */
    public static RLock fairLock(RedissonClient redisson, String lockKey) {
        RLock fairLock = redisson.getFairLock(lockKey);
        fairLock.lock();
        return fairLock;
    }

    /***
     * 尝试获得锁（公平锁）
     * @param redisson 客户端
     * @param lockKey 锁名
     * @param waitTime 获取锁的等待时间
     * @param leaseTime 锁超时时间
     * @param unit 参数的时间单位
     * @return
     */
    public static boolean tryFairLock(RedissonClient redisson, String lockKey, int waitTime, int leaseTime, TimeUnit unit) {
        RLock fairLock = redisson.getFairLock(lockKey);
        try {
            return fairLock.tryLock(waitTime, leaseTime, unit);
        } catch (InterruptedException e) {
            return false;
        }
    }

    /***
     * 释放锁（公平锁）
     * @param redisson
     * @param lockKey
     */
    public static void unFairLock(RedissonClient redisson, String lockKey) {
        redisson.getFairLock(lockKey).unlock();
    }
}
