package com.blockchain.server.eos.service;

import com.blockchain.server.eos.entity.Application;

import java.util.List;

/**
 * @author Harvey
 * @date 2019/2/26 18:16
 * @user WIN10
 */
public interface EosApplicationService {

    /**
     * 查询是否存在该应用钱包
     * @param walletType
     * @return
     */
    Integer selectWalletCountByWalletType(String walletType);

    /**
     * 查找所有应用信息
     * @return
     */
    List<Application> listEosApplication();
}
