package com.blockchain.server.tron.service.impl;

import com.blockchain.common.base.dto.GasDTO;
import com.blockchain.common.base.util.ExceptionPreconditionUtils;
import com.blockchain.common.base.util.JsonUtils;
import com.blockchain.server.tron.common.constant.TronConstant;
import com.blockchain.server.tron.entity.TronConfigWalletParam;
import com.blockchain.server.tron.mapper.TronConfigWalletParamMapper;
import com.blockchain.server.tron.service.TronConfigWalletParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Harvey
 * @date 2019/2/20 10:47
 * @user WIN10
 */
@Service
public class TronConfigWalletParamServiceImpl implements TronConfigWalletParamService {

    @Autowired
    private TronConfigWalletParamMapper configWalletParamMapper;

    /**
     * 查询单个手续费
     * @param tokenType
     * @return
     */
    @Override
    public TronConfigWalletParam selectOne(String tokenType) {
        // 查询单个手续费配置信息
        TronConfigWalletParam tronConfigWalletParam = new TronConfigWalletParam();
        tronConfigWalletParam.setModuleType(TronConstant.TRON_TOKEN_TYPE);
        tronConfigWalletParam.setParamName(TronConstant.TronGasConfig.TRON_GAS_PRIFIX + tokenType);
        tronConfigWalletParam.setStatus(TronConstant.TronGasConfig.TRON_GAS_STATUS_USABLE);
        return configWalletParamMapper.selectOne(tronConfigWalletParam);
    }

    @Override
    public GasDTO selectConfigWalletParam(TronConfigWalletParam tronConfigWalletParam) {
        ExceptionPreconditionUtils.notEmpty(tronConfigWalletParam);
        ExceptionPreconditionUtils.notEmpty(tronConfigWalletParam.getParamValue());
        try {
            return JsonUtils.jsonToPojo(tronConfigWalletParam.getParamValue(), GasDTO.class);
        } catch (Exception e) {
            return null;
        }
    }
}
