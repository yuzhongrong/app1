package com.blockchain.server.currency.Scheduling;

import com.alibaba.fastjson.JSONObject;
import com.blockchain.server.currency.common.constant.BaseCoinEnums;
import com.blockchain.server.currency.common.constant.RatesEnums;
import com.blockchain.server.currency.redis.MarketLegalCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Component
@Configurable
@EnableScheduling
public class CurrencyMarketScheduling {

    @Autowired
    private MarketLegalCache legalCache;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${market.coinbase.USD}")
    private String usdMarketUrl;

    @Value("${market.kraken.url}")
    private String krakenUrl;

    @Scheduled(cron = "*/5 * * * * ?")
    public void getCurrencyMarket() {
        try {
            setLegalMarket();
        } catch (Exception e) {
             e.printStackTrace();
        }
    }

    private void setLegalMarket() {
        Map<String, Object> rates = getCoinbaseMarket();
        //USD
        double usdcny = new Double(rates.get(RatesEnums.CNY.getValue()).toString());
        double usdhkd = new Double(rates.get(RatesEnums.HKD.getValue()).toString());
        double usdeur = new Double(rates.get(RatesEnums.EUR.getValue()).toString());

        //BTC
        setLegalMarket(BaseCoinEnums.BTC.getValue(), usdcny, usdhkd, usdeur, 1d / new Double(rates.get(BaseCoinEnums.BTC.getValue()).toString()));
        //ETH
        setLegalMarket(BaseCoinEnums.ETH.getValue(), usdcny, usdhkd, usdeur, 1d / new Double(rates.get(BaseCoinEnums.ETH.getValue()).toString()));
        //USDT
        setLegalMarket(BaseCoinEnums.USDT.getValue(), usdcny, usdhkd, usdeur, 1d);
        //EOS
        setLegalMarket(BaseCoinEnums.EOS.getValue(), usdcny, usdhkd, usdeur, getKrakenMarket("EOSUSD"));
    }

    private void setLegalMarket(String coin, double usdcny, double usdhkd, double usdeur, double coinusd) {
        legalCache.setMarketCache(coin + RatesEnums.USD.getValue(), coinusd);
        legalCache.setMarketCache(coin + RatesEnums.CNY.getValue(), coinusd * usdcny);
        legalCache.setMarketCache(coin + RatesEnums.HKD.getValue(), coinusd * usdhkd);
        legalCache.setMarketCache(coin + RatesEnums.EUR.getValue(), coinusd * usdeur);
    }

    /**
     * coinbase上的美元行情
     *
     * @return
     */
    private Map<String, Object> getCoinbaseMarket() {
        JSONObject result = restTemplate.getForEntity(usdMarketUrl, JSONObject.class).getBody();
        Map<String, Object> dataMap = (Map<String, Object>) result.get("data");
        return (Map<String, Object>) dataMap.get("rates");
    }

    /**
     * 获取kraken上的行情
     *
     * @param currencyPair ：行情币对
     * @return
     */
    private double getKrakenMarket(String currencyPair) {
        JSONObject result = restTemplate.getForEntity(krakenUrl + currencyPair, JSONObject.class).getBody();
        Map<String, Object> resultMap = (Map<String, Object>) result.get("result");
        List<List<Object>> trades = (List<List<Object>>) resultMap.get(currencyPair);
        return new Double(trades.get(trades.size() - 1).get(0).toString());
    }

}
