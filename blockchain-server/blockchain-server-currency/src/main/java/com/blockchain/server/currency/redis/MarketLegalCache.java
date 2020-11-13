package com.blockchain.server.currency.redis;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * \* <p>Desciption:法币缓存</p>
 * \* CreateTime: 2019/2/21 20:07
 * \* User: XianChaoWei
 * \* Version: V1.0
 * \
 */
@Component
public class MarketLegalCache {
    private static final Map<String, Double> MARKET = new ConcurrentHashMap<>();

    public void setMarketCache(String currencyPair, double amount) {
        MARKET.put(currencyPair, amount);
    }

    public double getMarketCache(String currencyPair) {
        return MARKET.getOrDefault(currencyPair, 0d);
    }

}
