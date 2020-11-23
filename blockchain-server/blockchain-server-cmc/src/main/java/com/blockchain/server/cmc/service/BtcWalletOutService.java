package com.blockchain.server.cmc.service;

import com.blockchain.server.cmc.entity.BtcWalletOut;

public interface BtcWalletOutService {

    /**
     * 插入一条资金提现资金钱包
     *
     * @param btcWalletOut
     */
    void InsertWalletOut(BtcWalletOut btcWalletOut);

}
