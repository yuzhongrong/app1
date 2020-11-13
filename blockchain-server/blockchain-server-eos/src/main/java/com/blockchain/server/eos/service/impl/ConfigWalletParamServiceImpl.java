package com.blockchain.server.eos.service.impl;

import com.blockchain.common.base.dto.GasDTO;
import com.blockchain.common.base.util.ExceptionPreconditionUtils;
import com.blockchain.common.base.util.JsonUtils;
import com.blockchain.server.eos.common.constant.EosConstant;
import com.blockchain.server.eos.entity.ConfigWalletParam;
import com.blockchain.server.eos.mapper.ConfigWalletParamMapper;
import com.blockchain.server.eos.service.ConfigWalletParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Harvey
 * @date 2019/2/20 10:47
 * @user WIN10
 */
@Service
public class ConfigWalletParamServiceImpl implements ConfigWalletParamService {

    @Autowired
    private ConfigWalletParamMapper configWalletParamMapper;

    /**
     * 查询单个手续费
     * @param tokenType
     * @return
     */
    @Override
    public ConfigWalletParam selectOne(String tokenType) {
        // 查询单个手续费配置信息
        ConfigWalletParam configWalletParam = new ConfigWalletParam();
        configWalletParam.setModuleType(EosConstant.EOS_TOKEN_TYPE);
        configWalletParam.setParamName(EosConstant.EosGasConfig.EOS_GAS_PRIFIX + tokenType);
        configWalletParam.setStatus(EosConstant.EosGasConfig.EOS_GAS_STATUS_USABLE);
        return configWalletParamMapper.selectOne(configWalletParam);
    }

    @Override
    public GasDTO selectConfigWalletParam(ConfigWalletParam configWalletParam) {
        ExceptionPreconditionUtils.notEmpty(configWalletParam);
        ExceptionPreconditionUtils.notEmpty(configWalletParam.getParamValue());
        try {
            return JsonUtils.jsonToPojo(configWalletParam.getParamValue(), GasDTO.class);
        } catch (Exception e) {
            return null;
        }
    }
}
