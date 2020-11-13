package com.blockchain.server.tron.service;


import com.blockchain.server.tron.entity.TronWalletCreate;

/**
 * @author Harvey
 * @date 2019/2/18 16:39
 * @user WIN10
 */
public interface TronWalletCreateService {

    /**
     * 查询创建钱包资金账户
     * @return
     */
    TronWalletCreate selectByOne();

}
