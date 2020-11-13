package com.blockchain.server.databot.service.impl;

import com.alibaba.fastjson.JSON;
import com.blockchain.server.databot.common.constant.CommonConstant;
import com.blockchain.server.databot.common.enums.DataBotEnums;
import com.blockchain.server.databot.common.exception.DataBotExeption;
import com.blockchain.server.databot.controller.TradingController;
import com.blockchain.server.databot.entity.CurrencyConfig;
import com.blockchain.server.databot.mapper.CurrencyConfigMapper;
import com.blockchain.server.databot.redis.CurrencyConfigCache;
import com.blockchain.server.databot.service.CurrencyConfigService;
import com.esotericsoftware.minlog.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CurrencyConfigServiceImpl implements CurrencyConfigService {
    private static final Logger LOG = LoggerFactory.getLogger(CurrencyConfigServiceImpl.class);
    @Autowired
    private CurrencyConfigMapper currencyConfigMapper;
    @Autowired
    private CurrencyConfigCache currencyConfigCache;

    @Override
    public CurrencyConfig selectByCurrencyPair(String currencyPair) {
        return currencyConfigMapper.selectByCurrencyPair(currencyPair);
    }

    @Override
    public List<CurrencyConfig> selectByStatus(String status) {
        boolean hasKey = currencyConfigCache.hasConfigListKey();
        //是否存在缓存
        if (hasKey) {
            //存在，从缓存中获取并返回
            return currencyConfigCache.getHashToList(status);
        } else {
            //不存在
            List<CurrencyConfig> configs = currencyConfigMapper.selectByStatus(status);
            LOG.info("读取到配置信息为："+ JSON.toJSONString(configs));
            //锁key
            String lockKey = currencyConfigCache.getCurrencyConfigLockKey();
            //是否成功获取锁
            boolean lockFlag = false;
            //循环获取锁
            while (!lockFlag) {
                lockFlag = currencyConfigCache.tryFairLock(lockKey);
                //获取成功
                if (lockFlag) {
                    //查询数据库并将数据放入缓存中
                    for (CurrencyConfig config : configs) {
                        currencyConfigCache.setHashValue(currencyConfigCache.getCurrencyConfigListKey(),
                                config.getCurrencyPair(), config);
                    }
                    //释放锁
                    currencyConfigCache.unFairLock(lockKey);
                }
            }
            return configs;
        }
    }

    @Override
    public boolean checkCurrencyPairStatusIsY(String currencyPair) {
        CurrencyConfig config = selectByCurrencyPair(currencyPair);
        if (config == null) {
            return false;
        }
        if (config.getStatus().equals(CommonConstant.YES)) {
            return true;
        }
        if (config.getStatus().equals(CommonConstant.NO)) {
            return false;
        }
        throw new DataBotExeption(DataBotEnums.UNKNOWN_STATUS);
    }


}
