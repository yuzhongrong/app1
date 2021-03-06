package com.blockchain.server.eth.service.impl;

import com.blockchain.common.base.dto.GasDTO;
import com.blockchain.common.base.util.JsonUtils;
import com.blockchain.server.eth.common.constants.EthConfigConstants;
import com.blockchain.server.eth.dto.EthGasDTO;
import com.blockchain.server.eth.entity.ConfigWalletParam;
import com.blockchain.server.eth.mapper.ConfigWalletParamMapper;
import com.blockchain.server.eth.service.IConfigWalletParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 配置表——业务接口
 *
 * @author YH
 * @date 2019年2月16日17:09:19
 */
@Service
public class ConfigWalletParamServiceImpl implements IConfigWalletParamService {
    @Autowired
    ConfigWalletParamMapper configMapper;


    @Override
    public GasDTO getGasConfig(String tokenSymbol) {
        ConfigWalletParam where = new ConfigWalletParam();
        where.setModuleType(EthConfigConstants.MODULE_TYPE);
        where.setStatus(EthConfigConstants.STATUS_NORMAL);
        String paramName = EthConfigConstants.EthGasConfig.PARAM_NAME + tokenSymbol;
        where.setParamName(paramName.toLowerCase());
        ConfigWalletParam config = configMapper.selectOne(where);
        if (config == null) return null;
        String val = config.getParamValue();
        return JsonUtils.jsonToPojo(val, GasDTO.class);
    }
}
