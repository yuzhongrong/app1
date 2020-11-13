package com.blockchain.server.otc.service.impl;

import com.blockchain.server.otc.common.constant.CommonConstans;
import com.blockchain.server.otc.common.constant.ConfigConstants;
import com.blockchain.server.otc.dto.config.ConfigDTO;
import com.blockchain.server.otc.entity.Config;
import com.blockchain.server.otc.mapper.ConfigMapper;
import com.blockchain.server.otc.service.ConfigService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class ConfigServiceImpl implements ConfigService {

    @Autowired
    private ConfigMapper configMapper;

    private static final long DEFAULT_AUTO_CANCEL_INTERVAL = 15L;//默认撤单时间间隔

    @Override
    public Config selectByKey(String key) {
        return configMapper.selectByKey(key);
    }

    @Override
    public boolean selectAutoCancelIsY() {
        Config config = configMapper.selectByKey(ConfigConstants.AUTO_CANCEL);
        if (config == null) {
            return false;
        }
        if (config.getStatus().equals(CommonConstans.NO)) {
            return false;
        }
        return true;
    }

    @Override
    public Long selectAutoCancelInterval() {
        //查询是否打开自动撤单
        boolean flag = selectAutoCancelIsY();
        //打开自动撤单
        if (flag) {
            //查询自动撤单时间间隔
            Config config = configMapper.selectByKey(ConfigConstants.AUTO_CANCEL_INTERVAL);
            //配置为空，返回默认值
            if (config == null) {
                return DEFAULT_AUTO_CANCEL_INTERVAL;
            } else {
                return Long.valueOf(config.getDataValue());
            }
        } else {
            //没打开自动撤单，返回空
            return null;
        }

    }

    @Override
    public List<ConfigDTO> selectServiceCharge() {
        Config adCharge = configMapper.selectByKey(ConfigConstants.AD_SERVICE_CHARGE);
        ConfigDTO adChargeDTO = new ConfigDTO();
        BeanUtils.copyProperties(adCharge, adChargeDTO);

        Config orderCharge = configMapper.selectByKey(ConfigConstants.ORDER_SERVICE_CHARGE);
        ConfigDTO orderChargeDTO = new ConfigDTO();
        BeanUtils.copyProperties(orderCharge, orderChargeDTO);

        List<ConfigDTO> results = new ArrayList<>();
        results.add(adChargeDTO);
        results.add(orderChargeDTO);

        return results;
    }

    @Override
    public Integer selectMarketSellAdCount() {
        Config config = configMapper.selectByKey(ConfigConstants.MARKET_SELL_COUNT);
        if (config != null && config.getStatus().equals(CommonConstans.YES)) {
            return Integer.valueOf(config.getDataValue());
        }
        return null;
    }

    @Override
    public Integer selectMarketBuyAdCount() {
        Config config = configMapper.selectByKey(ConfigConstants.MARKET_BUY_COUNT);
        if (config != null && config.getStatus().equals(CommonConstans.YES)) {
            return Integer.valueOf(config.getDataValue());
        }
        return null;
    }

    @Override
    public String selectMarketFreezeCoin() {
        Config config = configMapper.selectByKey(ConfigConstants.MARKET_FREEZE_COIN);
        if (config != null && config.getStatus().equals(CommonConstans.YES)) {
            return config.getDataValue();
        }
        return null;
    }

    @Override
    public BigDecimal selectMarketFreezeAmount() {
        Config config = configMapper.selectByKey(ConfigConstants.MARKET_FREEZE_AMOUNT);
        if (config != null && config.getStatus().equals(CommonConstans.YES)) {
            return new BigDecimal(config.getDataValue());
        }
        return BigDecimal.ZERO;
    }
}
