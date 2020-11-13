package com.blockchain.server.teth.service;

import com.blockchain.common.base.dto.GasDTO;

/**
 * 配置表——业务接口
 *
 * @author YH
 * @date 2019年2月16日17:09:19
 */
public interface IConfigWalletParamService {
    /**
     * 获取手续费详情
     *
     * @param tokenSymbol 币种
     * @return
     */
    GasDTO getGasConfig(String tokenSymbol);

}
