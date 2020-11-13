package com.blockchain.server.ltc.service.impl;

import com.blockchain.common.base.dto.GasDTO;
import com.blockchain.common.base.util.JsonUtils;
import com.blockchain.server.ltc.common.constants.ConfigConstants;
import com.blockchain.server.ltc.entity.ConfigWalletParam;
import com.blockchain.server.ltc.mapper.ConfigWalletParamMapper;
import com.blockchain.server.ltc.service.ConfigWalletParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 配置表——业务接口
 *
 * @date 2019年2月16日17:09:19
 */
@Service
public class ConfigWalletParamServiceImpl implements ConfigWalletParamService {
    @Autowired
    private ConfigWalletParamMapper configMapper;

    @Override
    public GasDTO getGasConfig(String tokenSymbol) {
        ConfigWalletParam where = new ConfigWalletParam();
        where.setModuleType(ConfigConstants.MODULE_TYPE);
        where.setStatus(ConfigConstants.STATUS_NORMAL);
        String paramName = ConfigConstants.GAS_CONFIG + tokenSymbol;
        where.setParamName(paramName.toLowerCase());
        ConfigWalletParam config = configMapper.selectOne(where);
        if (config == null) return null;
        String val = config.getParamValue();
        return JsonUtils.jsonToPojo(val, GasDTO.class);
    }

}
