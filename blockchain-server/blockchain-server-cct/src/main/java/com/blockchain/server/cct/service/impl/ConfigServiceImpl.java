package com.blockchain.server.cct.service.impl;

import com.blockchain.server.cct.common.enums.CctDataEnums;
import com.blockchain.server.cct.dto.config.ConfigDTO;
import com.blockchain.server.cct.entity.Config;
import com.blockchain.server.cct.mapper.ConfigMapper;
import com.blockchain.server.cct.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ConfigServiceImpl implements ConfigService {

    @Autowired
    private ConfigMapper configMapper;

    @Override
    public Config selectByKey(String key, String status) {
        return configMapper.selectByKeyAndStatus(key, status);
    }

    @Override
    public List<ConfigDTO> listServiceCharge() {
        List<ConfigDTO> configs = new ArrayList<>();
        //挂单数据
        Config makerCharge = configMapper.selectByKey(CctDataEnums.CONFIG_MAKER_CHARGE.getStrVlue());
        ConfigDTO makerChargeDTO = new ConfigDTO();
        makerChargeDTO.setDataTag(makerCharge.getDataTag());
        makerChargeDTO.setDataValue(makerCharge.getDataValue());

        //吃单数据
        Config takerCharge = configMapper.selectByKey(CctDataEnums.CONFIG_TAKER_CHARGE.getStrVlue());
        ConfigDTO takerChargeDTO = new ConfigDTO();
        takerChargeDTO.setDataTag(takerCharge.getDataTag());
        takerChargeDTO.setDataValue(takerCharge.getDataValue());

        configs.add(makerChargeDTO);
        configs.add(takerChargeDTO);
        return configs;
    }
}
