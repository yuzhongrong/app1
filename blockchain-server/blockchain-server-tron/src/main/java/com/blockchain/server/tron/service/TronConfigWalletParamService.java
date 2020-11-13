package com.blockchain.server.tron.service;

import com.blockchain.common.base.dto.GasDTO;
import com.blockchain.server.tron.entity.TronConfigWalletParam;

/**
 * @author Harvey
 * @date 2019/2/20 10:46
 * @user WIN10
 */
public interface TronConfigWalletParamService {

    /**
     * 查询单个手续费
     *
     * @param tokenType
     * @return
     */
    TronConfigWalletParam selectOne(String tokenType);

    /**
     * 返回GasDTO
     *
     * @param tronConfigWalletParam
     * @return
     */
    GasDTO selectConfigWalletParam(TronConfigWalletParam tronConfigWalletParam);
}
