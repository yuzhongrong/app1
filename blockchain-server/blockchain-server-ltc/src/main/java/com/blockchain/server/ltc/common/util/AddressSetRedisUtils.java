package com.blockchain.server.ltc.common.util;

import com.blockchain.server.ltc.common.constants.AddressConstans;
import com.blockchain.server.ltc.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class AddressSetRedisUtils {
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private WalletService walletService;

    /**
     * 保存地址集合到redis
     *
     * @param address 新增地址
     */
    public void insert(String address) {
        redisTemplate.opsForSet().add(AddressConstans.ADDRESS_KEY, address);
    }

    /**
     * 从redis中获取地址集合
     *
     * @return
     */
    public Set<String> get() {
        return redisTemplate.opsForSet().members(AddressConstans.ADDRESS_KEY);
    }

    /**
     * 判断缓存中是否存在该地址
     *
     * @param addr 地址
     * @return
     */
    public boolean isExistsAddr(String addr) {
        Set<String> addressSet = get();
        if (addressSet == null || addressSet.size() == 0) {
            addressSet = walletService.getAllWalletAddr();
            redisTemplate.opsForSet().add(AddressConstans.ADDRESS_KEY, addressSet.toArray());
        }

        return addressSet.contains(addr);
    }

}
