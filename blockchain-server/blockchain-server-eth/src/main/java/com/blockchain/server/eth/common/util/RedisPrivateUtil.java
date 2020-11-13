package com.blockchain.server.eth.common.util;

import com.blockchain.common.base.util.RSACoderUtils;
import com.blockchain.server.eth.common.enums.EthWalletEnums;
import com.blockchain.server.eth.common.exception.EthWalletException;
import org.apache.commons.codec.binary.Base64;
import org.springframework.data.redis.core.RedisTemplate;

import java.security.KeyPair;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

public class RedisPrivateUtil {
    public static final String PRIVATE_KEY = "wallet:private:";   //  储存私钥的key值，用于解密公钥加密的密码
    public static final int INVALID_DAYS = 1; // 设置过期时间一天

    /**
     * 缓存加密私钥
     *
     * @param key
     * @param privateKey    私钥
     * @param redisTemplate
     */
    private static void setPrivateKey(String key, String privateKey, RedisTemplate redisTemplate) {
        String _key = RedisPrivateUtil.PRIVATE_KEY + key;
        redisTemplate.opsForValue().set(_key, privateKey, RedisPrivateUtil.INVALID_DAYS, TimeUnit.MINUTES);
    }

    /**
     * 获取缓存中的私钥
     *
     * @param key
     * @param redisTemplate
     * @return 私钥
     */
    public static String getPrivateKey(String key, RedisTemplate redisTemplate) {
        String _key = RedisPrivateUtil.PRIVATE_KEY + key;
        String privateKey = (String) redisTemplate.opsForValue().get(_key);
        redisTemplate.delete(RedisPrivateUtil.PRIVATE_KEY + key);
        return privateKey;
    }

    /**
     * 把私钥存入缓存，返回公钥
     *
     * @param key
     * @param redisTemplate
     * @return 公钥
     */
    public static String getPublicKey(String key, RedisTemplate redisTemplate) {
        try {
            KeyPair keyPair = RSACoderUtils.initKey();
            String puclicKey = Base64.encodeBase64String(keyPair.getPublic().getEncoded()); // 获取公钥
            String privateKey = Base64.encodeBase64String(keyPair.getPrivate().getEncoded());  // 获取私钥
            RedisPrivateUtil.setPrivateKey(key, privateKey, redisTemplate); // 存入私钥
            return puclicKey;
        } catch (Exception e) {
            throw new EthWalletException(EthWalletEnums.SERVER_IS_TOO_BUSY);
        }
    }

    public static String getWalletPublicKey(String key, RedisTemplate redisTemplate) {
        try {
            KeyPair keyPair = RSACoderUtils.initKey();
            String puclicKey = Base64.encodeBase64String(keyPair.getPublic().getEncoded());
            String regEx = "[`~!@#$%^&*()+=|{}:;\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？']";
            puclicKey = Pattern.compile(regEx).matcher(puclicKey).replaceAll("").trim();
            RedisPrivateUtil.setPrivateKey(key, puclicKey, redisTemplate); // 存入私钥
            return puclicKey;
        } catch (Exception e) {
            throw new EthWalletException(EthWalletEnums.SERVER_IS_TOO_BUSY);
        }
    }

}
