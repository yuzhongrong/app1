package com.blockchain.server.teth.common.util;

import org.springframework.data.redis.core.RedisTemplate;

import java.math.BigInteger;

/**
 * 缓存区块充值记录数据爬取的缓存
 */
public class RedisBlockUtil {
    //  储存区块的初始化高度
    static final String ETH_BLOCKCHAIN_BEGIN = "teth:blockchain:begin";
    //  储存区块的当前最新高度
    static final String ETH_BLOCKCHAIN_CURRENT = "teth:blockchain:current";
    //  储存区块的链上最新高度
    static final String ETH_BLOCKCHAIN_NEWEST = "teth:blockchain:newest";
    //  储存所有遗漏区块的爬取状态
    static final String ETH_BLOCKCHAIN_OPT_HASH = "teth:blockchain:opt:hash";

    /**
     * 缓存初始化的区块高度
     *
     * @param blockNumber
     * @param redisTemplate
     */
    public static void setBlockBegin(BigInteger blockNumber, RedisTemplate redisTemplate) {
        redisTemplate.opsForValue().set(ETH_BLOCKCHAIN_BEGIN, blockNumber);
    }

    /**
     * 获取初始化的区块高度
     *
     * @param redisTemplate
     * @return
     */
    public static BigInteger getBlockBegin(RedisTemplate redisTemplate) {
        Object blockBegin = redisTemplate.opsForValue().get(ETH_BLOCKCHAIN_BEGIN);
        return DataCheckUtil.objToBigInteger(blockBegin);
    }

    /**
     * 缓存初始化的区块高度
     *
     * @param blockNumber
     * @param redisTemplate
     */
    public static void setBlockNewest(BigInteger blockNumber, RedisTemplate redisTemplate) {
        redisTemplate.opsForValue().set(ETH_BLOCKCHAIN_NEWEST, blockNumber);
    }

    /**
     * 获取初始化的区块高度
     *
     * @param redisTemplate
     * @return
     */
    public static BigInteger getBlockNewest(RedisTemplate redisTemplate) {
        Object blockBegin = redisTemplate.opsForValue().get(ETH_BLOCKCHAIN_NEWEST);
        return DataCheckUtil.objToBigInteger(blockBegin);
    }

    /**
     * 缓存链上的最新区块高度
     *
     * @param blockNumber
     * @param redisTemplate
     */
    public static void setBlockCurrent(BigInteger blockNumber, RedisTemplate redisTemplate) {
        redisTemplate.opsForValue().set(ETH_BLOCKCHAIN_CURRENT, blockNumber);
    }

    /**
     * 获取链上的最新高度
     *
     * @param redisTemplate
     * @return
     */
    public static BigInteger getBlockCurrent(RedisTemplate redisTemplate) {
        Object blockBegin = redisTemplate.opsForValue().get(ETH_BLOCKCHAIN_CURRENT);
        return DataCheckUtil.objToBigInteger(blockBegin);
    }



    /**
     * 根据区块高度，获取爬取的次数
     *
     * @param hashKey 区块高度
     * @return
     */
    public static Integer getBlockOptMapCount(BigInteger hashKey, RedisTemplate redisTemplate) {
        Object val = redisTemplate.opsForHash().get(ETH_BLOCKCHAIN_OPT_HASH, hashKey);
        Integer count = DataCheckUtil.objToInt(val);
        return count != null ? count : 0;
    }

    /**
     * 存入爬取次数
     *
     * @param hashKey       区块高度
     * @param redisTemplate
     */
    public static void setBlockOptMapCount(BigInteger hashKey, RedisTemplate redisTemplate) {
        redisTemplate.opsForHash().put(ETH_BLOCKCHAIN_OPT_HASH, hashKey, 1);
    }

    /**
     * 存入爬取次数
     *
     * @param hashKey       区块高度
     * @param redisTemplate
     */
    public static void incrementBlockOptMapCount(BigInteger hashKey, RedisTemplate redisTemplate) {
        redisTemplate.opsForHash().increment(ETH_BLOCKCHAIN_OPT_HASH, hashKey, 1);
    }

    /**
     * 移除区块的爬取记录
     *
     * @param hashKey       区块高度
     * @param redisTemplate
     */
    public static void delBlockOptMapCount(BigInteger hashKey, RedisTemplate redisTemplate) {
        redisTemplate.opsForHash().delete(ETH_BLOCKCHAIN_OPT_HASH, hashKey);
    }


}
