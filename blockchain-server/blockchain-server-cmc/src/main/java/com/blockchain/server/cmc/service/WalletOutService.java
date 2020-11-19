package com.blockchain.server.cmc.service;

import com.blockchain.server.cmc.entity.WalletOut;

public interface WalletOutService {

    /**
     * 插入一条资金提现资金钱包
     *
     * @param walletOut
     */
    void InsertWalletOut(WalletOut walletOut);

}
