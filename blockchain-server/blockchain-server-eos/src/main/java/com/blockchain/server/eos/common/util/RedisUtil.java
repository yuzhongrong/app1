package com.blockchain.server.eos.common.util;

import com.blockchain.server.eos.common.constant.EosConstant;
import org.springframework.data.redis.core.RedisTemplate;

import java.math.BigInteger;

/**
 * @author Harvey
 * @date 2019/2/23 10:52
 * @user WIN10
 * redis工具类
 */
public class RedisUtil {

    /**
     * 获取当前区块
     *
     * @param redisTemplate
     * @return
     */
    public static BigInteger getCurrentBlock(RedisTemplate redisTemplate) {
        Object current = redisTemplate.opsForValue().get(EosConstant.RedisKey.EOS_BLOCKCHAIN_CURRENT);
        if (!(current instanceof BigInteger)) return null;
        return (BigInteger) current;
    }

    /**
     * 设置redis
     *
     * @param key
     * @param value
     * @param redisTemplate
     */
    public static void setOpsForValue(String key, Object value, RedisTemplate redisTemplate) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 获取存储处理次数
     *
     * @param mapKey
     * @param key
     * @param redisTemplate
     * @return
     */
    public static Integer getOpsForHash(String mapKey, BigInteger key, RedisTemplate redisTemplate) {
        Object number = redisTemplate.opsForHash().get(mapKey, key);
        if (!(number instanceof Integer)) return null;
        return (Integer) number;
    }

    /**
     * 判断是否存在该处理次数数据
     *
     * @param mapKey
     * @param key
     * @param redisTemplate
     * @return
     */
    public static boolean getKeyIsExist(String mapKey, BigInteger key, RedisTemplate redisTemplate) {
        return redisTemplate.opsForHash().hasKey(mapKey, key);
    }

    /**
     * 保存处理次数
     *
     * @param mapKey
     * @param key
     * @param time
     * @param redisTemplate
     */
    public static void putOpsForHash(String mapKey, BigInteger key, Integer time, RedisTemplate redisTemplate) {
        redisTemplate.opsForHash().put(mapKey, key, time);
    }

    /**
     * 删除处理次数
     *
     * @param mapKey
     * @param key
     * @param redisTemplate
     */
    public static void delOpsForHash(String mapKey, BigInteger key, RedisTemplate redisTemplate) {
        redisTemplate.opsForHash().delete(mapKey, key);
    }

}
