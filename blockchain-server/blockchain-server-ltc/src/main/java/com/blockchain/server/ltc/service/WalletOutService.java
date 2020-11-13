package com.blockchain.server.ltc.service;

import com.blockchain.server.ltc.entity.WalletOut;

public interface WalletOutService {

    /**
     * 插入一条资金提现资金钱包
     *
     * @param walletOut
     */
    void InsertWalletOut(WalletOut walletOut);

}
