package com.blockchain.server.currency.redis;

import com.blockchain.server.currency.common.constant.CommonConstants;
import com.blockchain.server.currency.dto.CurrencyMarketHistoryDTO;
import com.blockchain.server.currency.dto.CurrencyPairDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * \* <p>Desciption:</p>
 * \* CreateTime: 2019/2/12 10:08
 * \* User: XianChaoWei
 * \* Version: V1.0
 * \
 */
@Component
public class HistoryCache {
    private static final String MARKET_LIST_HISTORY_CACHE = "market:list:history:";

    public static final String MARKET_LIST_HISTORY_CACHE_1DAY = "market:list:history:1DAY:";

    @Autowired
    private RedisTemplate redisTemplate;

    public List push(String currencyPair, CurrencyMarketHistoryDTO dto){
        String key = MARKET_LIST_HISTORY_CACHE + currencyPair;


            //累加当天成交金额
        redisTemplate.opsForValue().increment(MARKET_LIST_HISTORY_CACHE_1DAY+ dto.getTradingType()+"-"+currencyPair,  (dto.getTradingNum().multiply(dto.getMakerPrice())).doubleValue());
        redisTemplate.opsForList().leftPush(key,dto);
        if (redisTemplate.opsForList().size(key) > CommonConstants.HISTORY_SIZE){
            redisTemplate.opsForList().rightPop(key);
        }
        return redisTemplate.opsForList().range(key,0, CommonConstants.HISTORY_SIZE);
    }

    public List getList(String currencyPair){
        String key = MARKET_LIST_HISTORY_CACHE + currencyPair;
        return redisTemplate.opsForList().range(key,0, CommonConstants.HISTORY_SIZE);
    }
}
