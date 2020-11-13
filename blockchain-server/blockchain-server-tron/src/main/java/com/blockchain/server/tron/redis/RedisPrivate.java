package com.blockchain.server.tron.redis;

import com.blockchain.common.base.util.RSACoderUtils;
import com.blockchain.server.tron.common.enums.TronWalletEnums;
import com.blockchain.server.tron.common.exception.TronWalletException;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.security.KeyPair;
import java.util.concurrent.TimeUnit;

@Component
public class RedisPrivate {

    @Autowired
    private RedisTemplate redisTemplate;
    public final String PRIVATE_KEY = "wallet:private:";   //  储存私钥的key值，用于解密公钥加密的密码
    public final int INVALID_DAYS = 1; // 设置过期时间一天

    /**
     * 缓存加密私钥
     *
     * @param key
     * @param privateKey    私钥
     */
    private void setPrivateKey(String key, String privateKey) {
        String _key = PRIVATE_KEY + key;
        redisTemplate.opsForValue().set(_key, privateKey, INVALID_DAYS, TimeUnit.DAYS);
    }

    /**
     * 获取缓存中的私钥
     *
     * @param key
     * @return 私钥
     */
    public String getPrivateKey(String key) {
        String _key = PRIVATE_KEY + key;
        String privateKey = (String) redisTemplate.opsForValue().get(_key);
        return privateKey;
    }

    /**
     * 把私钥存入缓存，返回公钥
     *
     * @param key
     * @return 公钥
     */
    public String getPublicKey(String key) {
        try {
            KeyPair keyPair = RSACoderUtils.initKey();
            String puclicKey = Base64.encodeBase64String(keyPair.getPublic().getEncoded()); // 获取公钥
            String privateKey = Base64.encodeBase64String(keyPair.getPrivate().getEncoded());  // 获取私钥
            setPrivateKey(key, privateKey); // 存入私钥
            return puclicKey;
        } catch (Exception e) {
            throw new TronWalletException(TronWalletEnums.SERVER_IS_TOO_BUSY);
        }
    }
}
