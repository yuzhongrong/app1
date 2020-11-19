package com.blockchain.server.cmc.common.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class BlockRedisUtils {
    @Autowired
    private RedisTemplate redisTemplate;

    private static final String ETH_BLOCKCHAIN_OPT_HASH = "ltc:blockchain:opt:block";

    /**
     * 根据区块高度，获取爬取的次数
     *
     * @param blockHeight 区块高度
     * @return
     */
    public int getBlockOptMapCount(int blockHeight) {
        Integer val = (Integer) redisTemplate.opsForHash().get(ETH_BLOCKCHAIN_OPT_HASH, blockHeight);
        return val != null ? val : 0;
    }

    /**
     * 爬取次数加1
     *
     * @param blockHeight 区块高度
     */
    public void incrementBlockOptMapCount(int blockHeight) {
        redisTemplate.opsForHash().increment(ETH_BLOCKCHAIN_OPT_HASH, blockHeight, 1);
    }

    /**
     * 移除区块的爬取记录
     *
     * @param blockHeight 区块高度
     */
    public void delBlockOptMapCount(int blockHeight) {
        redisTemplate.opsForHash().delete(ETH_BLOCKCHAIN_OPT_HASH, blockHeight);
    }

}
