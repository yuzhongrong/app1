package com.blockchain.server.currency.Scheduling;

import com.alibaba.fastjson.JSONObject;
import com.blockchain.common.base.util.HttpUtilManager;
import com.blockchain.server.currency.dto.CurrencyMarketDTO;
import com.blockchain.server.currency.redis.HistoryCache;
import com.blockchain.server.currency.service.CurrencyMarketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
@Configurable
@EnableScheduling
public class HuobiMarketScheduling {
    static final Logger LOG = LoggerFactory.getLogger(HuobiMarketScheduling.class);

    @Autowired
    private CurrencyMarketService currencyMarketService;

    @Value("${market.huobi.url}")
    private String huobiUrl;

    @Value("${market.huobi.currencys}")
    private String huobiCurrencys;

    private static ExecutorService cachedThreadPool = Executors.newSingleThreadExecutor();


    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 每天凌晨执行一次
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void scheduledTask() {
        List<List> currencys = JSONObject.parseArray(huobiCurrencys, List.class);
        for (List data : currencys) {
            String key = data.get(1).toString();
            String val = data.get(2).toString();
            String keyVal = key + "-" + val;
            redisTemplate.delete("market:list:history:1DAY:SELL-" + keyVal);
            redisTemplate.delete("market:list:history:1DAY:BUY-" + keyVal);

        }

        redisTemplate.delete("market:list:history:1DAY:SELL-FK-USDT");
        redisTemplate.delete("market:list:history:1DAY:BUY-FK-USDT");
    }


    @Scheduled(cron = "*/1 * * * * ?")
    public void getCurrencyMarket() {
        cachedThreadPool.execute(new Runnable() {
            @Override
            public void run() {

            }
        });

        setHuobiMarket();
    }

    private void setHuobiMarket() {
        List<List> currencys = JSONObject.parseArray(huobiCurrencys, List.class);
        for (List data : currencys) {
            saveMarket(getHuobiMarket(huobiUrl + data.get(0).toString()),
                    data.get(1).toString(), data.get(2).toString());
        }
    }

    /**
     * @param list     火币的行情列表
     * @param coinName
     * @param unitName
     */
    private void saveMarket(List<JSONObject> list, String coinName, String unitName) {
        //获取当前行情,作用只是判断时间对不对的上
        CurrencyMarketDTO now = currencyMarketService.get(coinName + "-" + unitName);
        BigDecimal amount = null;//这里amount 是总额
        boolean mark = true;
        if ("BTC".equals(coinName) && "USDT".equals(unitName)) {
            amount = getHuobiMerged();
            mark = false;
        }
        for (JSONObject obj : list) {
            //obj = {"data":[{"trade-id":100380973204,"amount":9674.79,"price":0.0000017409,"id":10125269029477595856379913,"ts":1597222595062,"direction":"buy"}],"id":101252690294,"ts":1597222595062}
            Long timestamp = Long.parseLong(obj.get("ts").toString());
            if (timestamp >= now.getTimestamp()) {
                List<JSONObject> data = (List<JSONObject>) obj.get("data");
                BigDecimal tradingVolume = new BigDecimal("0.1"); //BTC真实交易额
                if (mark) {
                    amount = new BigDecimal(data.get(0).get("amount").toString());
                } else {
                    tradingVolume = new BigDecimal(data.get(0).get("amount").toString());
                }
                currencyMarketService.save(coinName, unitName,
                        new BigDecimal(data.get(0).get("price").toString()), amount, timestamp, data.get(0).get("direction").toString().toUpperCase(), tradingVolume);

            } else {
                break;
            }
        }
    }


    /**
     * coinbase上的美元行情
     *
     * @return
     */
    private List<JSONObject> getHuobiMarket(String url) {
        HttpUtilManager manager = HttpUtilManager.getInstance();
        String result = manager.requestHttpGet(url);
        JSONObject jsonObject = JSONObject.parseObject(result);
        LOG.info("JSONObject:" + jsonObject.toJSONString());
        return (List<JSONObject>) jsonObject.get("data");
    }


    /**
     * coinbase上的美元行情
     *
     * @return
     */
    private BigDecimal getHuobiMerged() {
        HttpUtilManager manager = HttpUtilManager.getInstance();
        String result = manager.requestHttpGet("https://api.huobi.pro/market/detail/merged?period=1day&size=200&symbol=btcusdt");
        JSONObject jsonObject = JSONObject.parseObject(result);
        //因为系统业务需要，按火币上面的 30%
        BigDecimal realAmount = new BigDecimal(jsonObject.getJSONObject("tick").get("amount").toString());
        BigDecimal amount = new BigDecimal(jsonObject.getJSONObject("tick").get("amount").toString()).multiply(new BigDecimal("0.3"));
        return amount;
    }


}
