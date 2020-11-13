package com.blockchain.server.currency.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.blockchain.server.currency.dto.CurrencyMarketDTO;
import com.blockchain.server.currency.dto.MyTokenDTO;
import com.blockchain.server.currency.service.CurrencyMarketService;
import com.blockchain.server.currency.service.MyTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @Datetime: 2020/4/29   13:59
 * @Author: Xia rong tao
 * @title
 */
@Service
public class MyTokenServiceImpl  implements MyTokenService {

    @Value("${market.huobi.currencys}")
    private String huobiCurrencys;


    @Autowired
    private CurrencyMarketService currencyMarketService;


    @Autowired
    private RedisTemplate redisTemplate;


    @Override
    public List<MyTokenDTO> getMyToken() {


        List<List> currencys = JSONObject.parseArray(huobiCurrencys, List.class);
        List<MyTokenDTO>  resultData = new ArrayList<>(currencys.size());
        for (List data : currencys) {
            String key = data.get(1).toString();
            String val = data.get(2).toString();
            String keyVal = key+"-"+val;
            //获取FK_USDT
            CurrencyMarketDTO fkUsdt =   currencyMarketService.get(keyVal) ;

            //获取24h交易金额
            Double sell = (Double) redisTemplate.opsForValue().get("market:list:history:1DAY:SELL-"+keyVal);
            Double buy = (Double) redisTemplate.opsForValue().get("market:list:history:1DAY:BUY-"+keyVal);
            float amount_24h  = (float) (sell + buy);
            //封装返回
            resultData.add(new MyTokenDTO(key,val,fkUsdt.getAmount().floatValue(),fkUsdt.getTotal().longValue(),amount_24h,fkUsdt.getTimestamp()));


        }
        CurrencyMarketDTO fkUsdt =  currencyMarketService.get(FK_USDT) ;
         //FK-USDT 切割
        String[] pair = fkUsdt.getCurrencyPair().split("-");
        //获取24h交易金额
        Double sell = (Double) redisTemplate.opsForValue().get("market:list:history:1DAY:SELL-FK-USDT");
        Double buy = (Double) redisTemplate.opsForValue().get("market:list:history:1DAY:BUY-FK-USDT");
        float amount_24h  = (float) (sell + buy);
        //封装返回
        resultData.add(new MyTokenDTO(pair[0],pair[1],fkUsdt.getAmount().floatValue(),fkUsdt.getTotal().longValue(),amount_24h,fkUsdt.getTimestamp()));

        return resultData;

       /*

       //获取FK_USDT
        CurrencyMarketDTO fkUsdt =  currencyMarketService.get(FK_USDT) ;
        List<MyTokenDTO>  resultData = new ArrayList<>(1);
        //FK-USDT 切割
        String[] pair = fkUsdt.getCurrencyPair().split("-");
        //获取24h交易金额
        Double sell = (Double) redisTemplate.opsForValue().get("market:list:history:1DAY:SELL-FK-USDT");
        Double buy = (Double) redisTemplate.opsForValue().get("market:list:history:1DAY:BUY-FK-USDT");
        float amount_24h  = (float) (sell + buy);
        //封装返回
        resultData.add(new MyTokenDTO(pair[0],pair[1],fkUsdt.getAmount().floatValue(),fkUsdt.getTotal().longValue(),amount_24h,fkUsdt.getTimestamp()));
        return resultData;

        */
    }

}
