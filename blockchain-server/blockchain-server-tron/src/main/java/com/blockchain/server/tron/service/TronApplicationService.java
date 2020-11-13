package com.blockchain.server.tron.service;


import com.blockchain.server.tron.entity.TronApplication;

import java.util.List;

/**
 * @author Harvey
 * @date 2019/2/26 18:16
 * @user WIN10
 */
public interface TronApplicationService {

    /**
     * 查询是否存在该应用钱包
     *
     * @param walletType
     * @return
     */
    Integer selectWalletCountByWalletType(String walletType);

    /**
     * 查找所有应用信息
     *
     * @return
     */
    List<TronApplication> listTronApplication();

    /**
     * 验证是否有该应用体系的钱包
     *
     * @param walletType 应用标识
     */
    void checkWalletType(String walletType);
}
