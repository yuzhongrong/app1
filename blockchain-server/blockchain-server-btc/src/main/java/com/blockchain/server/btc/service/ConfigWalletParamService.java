package com.blockchain.server.btc.service;

import com.blockchain.common.base.dto.GasDTO;

/**
 * 配置表——业务接口
 */
public interface ConfigWalletParamService {

    /**
     * 获取手续费详情
     *
     * @param tokenSymbol 币种
     * @return
     */
    GasDTO getGasConfig(String tokenSymbol);

}
