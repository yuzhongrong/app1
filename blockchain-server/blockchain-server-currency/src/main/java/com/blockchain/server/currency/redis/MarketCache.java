package com.blockchain.server.currency.redis;

import com.blockchain.server.currency.dto.CurrencyPairDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.SortedMap;
import java.util.concurrent.TimeUnit;

/**
 * \* <p>Desciption:</p>
 * \* CreateTime: 2019/2/12 10:08
 * \* User: XianChaoWei
 * \* Version: V1.0
 * \
 */
@Component
public class MarketCache {
    private static final String MARKET_LIST_LIST_CACHE = "market:list:list";
    private static final String MARKET_LIST_HOME_CACHE = "market:list:home";

    @Autowired
    private RedisTemplate redisTemplate;

    public void setHomeList( List<CurrencyPairDTO> dtoList){
        redisTemplate.opsForValue().set(MARKET_LIST_HOME_CACHE, dtoList, 365, TimeUnit.DAYS);
    }

    public List<CurrencyPairDTO> getHomeList(){
        String key = MARKET_LIST_HOME_CACHE ;
        if (redisTemplate.hasKey(key)) {//如果有token信息，更新token的有效时间
            List<CurrencyPairDTO> dtoList = (List<CurrencyPairDTO>) redisTemplate.opsForValue().get(key);
            return dtoList;
        }
        return null;
    }

    public void setList( List<CurrencyPairDTO> dtoList){
        redisTemplate.opsForValue().set(MARKET_LIST_LIST_CACHE, dtoList, 365, TimeUnit.DAYS);
    }

    public List<CurrencyPairDTO> getList(){
        String key = MARKET_LIST_LIST_CACHE ;
        if (redisTemplate.hasKey(key)) {//如果有token信息，更新token的有效时间
            List<CurrencyPairDTO> dtoList = (List<CurrencyPairDTO>) redisTemplate.opsForValue().get(key);
            return dtoList;
        }
        return null;
    }

}
