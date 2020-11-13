package com.blockchain.server.eos.service;

import com.blockchain.common.base.dto.GasDTO;
import com.blockchain.server.eos.entity.ConfigWalletParam;

/**
 * @author Harvey
 * @date 2019/2/20 10:46
 * @user WIN10
 */
public interface ConfigWalletParamService {

    /**
     * 查询单个手续费
     * @param tokenType
     * @return
     */
    ConfigWalletParam selectOne(String tokenType);

    /**
     * 返回GasDTO
     * @param configWalletParam
     * @return
     */
    GasDTO selectConfigWalletParam(ConfigWalletParam configWalletParam);
}
