package com.blockchain.server.btc.service.impl;

import com.blockchain.common.base.dto.GasDTO;
import com.blockchain.common.base.util.JsonUtils;
import com.blockchain.server.btc.common.constants.BtcConfigConstants;
import com.blockchain.server.btc.entity.ConfigWalletParam;
import com.blockchain.server.btc.mapper.ConfigWalletParamMapper;
import com.blockchain.server.btc.service.ConfigWalletParamService;
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
        where.setModuleType(BtcConfigConstants.MODULE_TYPE);
        where.setStatus(BtcConfigConstants.STATUS_NORMAL);
        String paramName = BtcConfigConstants.GAS_CONFIG + tokenSymbol;
        where.setParamName(paramName.toLowerCase());
        ConfigWalletParam config = configMapper.selectOne(where);
        if (config == null) return null;
        String val = config.getParamValue();
        return JsonUtils.jsonToPojo(val, GasDTO.class);
    }

}
