package com.blockchain.server.databot.controller;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.server.databot.common.constant.CommonConstant;
import com.blockchain.server.databot.entity.CurrencyConfig;
import com.blockchain.server.databot.schedule.KMarketScheduling;
import com.blockchain.server.databot.service.CurrencyConfigService;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Datetime: 2020/5/9   14:00
 * @Author: Xia rong tao
 * @title
 */
@RestController
@RequestMapping("/trading")
public class TradingController {
    private static final Logger LOG = LoggerFactory.getLogger(TradingController.class);


    @Autowired
    private CurrencyConfigService currencyConfigService;

    @Autowired
    private KMarketScheduling kMarketScheduling;


    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    @ResponseBody
    public String get(   ) {
       //查询需要设置数量的币对
        List<CurrencyConfig> currencyConfigs = currencyConfigService.selectByStatus(CommonConstant.YES);
        for (CurrencyConfig currencyConfig : currencyConfigs) {
            kMarketScheduling.setUpOneDayKTotalAmount(currencyConfig.getCurrencyPair(), BigDecimal.valueOf(currencyConfig.getKDayTotalAmount()));
        }
        return  "{ok}";
    }


    @RequestMapping(value = "/delKey", method = RequestMethod.GET)
    @ResponseBody
    public void delKey(  String redisKey )  {

        LOG.info("redisKey:"+redisKey);
        redisTemplate.opsForHash().delete(redisKey);
    }
}
