package com.blockchain.server.tron.redis;

import com.blockchain.server.tron.common.constant.TronConstant;
import com.blockchain.server.tron.entity.TronWalletKeyInit;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.math.BigInteger;
import java.util.Set;

/**
 * @author Harvey
 * @date 2019/2/23 10:52
 * @user WIN10
 * redis工具类
 */
@Component
public class Redis {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 获取当前区块
     *
     * @return
     */
    public BigInteger getCurrentBlock() {
        Object current = redisTemplate.opsForValue().get(TronConstant.RedisKey.TRON_BLOCKCHAIN_CURRENT);
        if (!(current instanceof BigInteger)) return null;
        return (BigInteger) current;
    }

    /**
     * 设置redis
     *
     * @param key
     * @param value
     */
    public void setOpsForValue(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 获取存储处理次数
     *
     * @param mapKey
     * @param key
     * @return
     */
    public Integer getOpsForHash(String mapKey, BigInteger key) {
        Object number = redisTemplate.opsForHash().get(mapKey, key);
        if (!(number instanceof Integer)) return null;
        return (Integer) number;
    }

    /**
     * 判断是否存在该处理次数数据
     *
     * @param mapKey
     * @param key
     * @return
     */
    public boolean getKeyIsExist(String mapKey, BigInteger key) {
        return redisTemplate.opsForHash().hasKey(mapKey, key);
    }

    /**
     * 保存处理次数
     *
     * @param mapKey
     * @param key
     * @param time
     */
    public void putOpsForHash(String mapKey, BigInteger key, Integer time) {
        redisTemplate.opsForHash().put(mapKey, key, time);
    }

    /**
     * 删除处理次数
     *
     * @param mapKey
     * @param key
     */
    public void delOpsForHash(String mapKey, BigInteger key) {
        redisTemplate.opsForHash().delete(mapKey, key);
    }

    /**
     * 获取redis保存的数据
     *
     * @param key
     * @return
     */
    public Object getOpsForValue(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 从左边插入一条数据
     *
     * @param key
     * @param tronWalletKeyInit
     */
    public void leftPush(String key, TronWalletKeyInit tronWalletKeyInit) {
        redisTemplate.opsForList().leftPush(key, tronWalletKeyInit);
    }

    /**
     * 获取redis list长度
     * @param key
     * @return
     */
    public Long listLength(String key) {
        Long size = redisTemplate.opsForList().size(key);
        return size;
    }

    /**
     * 移除指定value的数据
     * @param key
     * @param tronWalletKeyInit
     * @return
     */
    public Long removeList(String key, TronWalletKeyInit tronWalletKeyInit) {
        return redisTemplate.opsForList().remove(key, 0, tronWalletKeyInit);
    }

    /**
     * 从右边获取一条数据
     *
     * @param key
     * @return
     */
    public Object rightPop(String key) {
        return redisTemplate.opsForList().rightPop(key);
    }

    /**
     * 从左边边获取一条数据
     *
     * @param key
     * @return
     */
    public Object leftPop(String key) {
        return redisTemplate.opsForList().leftPop(key);
    }
    /**
     * 从缓存重获取所有16进制地址
     * @return
     */
    public Set<String> getWalletHexAddrs() {
        return redisTemplate.opsForSet().members(TronConstant.RedisKey.TRON_WALLET_ADDRS);
    }

    /**
     * 把所有16进制地址存入缓存重
     * @param set
     */
    public void setWalletAddrs(Set<String> set) {
        redisTemplate.opsForSet().add(TronConstant.RedisKey.TRON_WALLET_ADDRS, set.toArray());
    }

    /**
     * 添加单个钱包地址
     * @param addr
     */
    public void setWalletAddr(String addr) {
        if (!redisTemplate.opsForSet().isMember(TronConstant.RedisKey.TRON_WALLET_ADDRS, addr))
            redisTemplate.opsForSet().add(TronConstant.RedisKey.TRON_WALLET_ADDRS, addr);
    }
}
