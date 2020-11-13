package com.blockchain.server.base.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * \* <p>Desciption:</p>
 * \* CreateTime: 2019/2/12 10:08
 * \* User: XianChaoWei
 * \* Version: V1.0
 * \
 */
@Component
public class IpInnerCache {
    private static final String IP_INNER_CACHE = "ip:inner";

    @Autowired
    private RedisTemplate redisTemplate;

    public String getIpInner(){
        if (redisTemplate.hasKey(IP_INNER_CACHE)) {//如果有token信息，更新token的有效时间
            String ipInner = redisTemplate.opsForValue().get(IP_INNER_CACHE).toString();
            return ipInner;
        }
        return null;
    }
}
