package com.blockchain.server.teth.service;

/**
 * 以太坊钱包主要信息表——业务接口
 *
 * @author YH
 * @date 2019年2月16日17:09:19
 */
public interface IEthGasWalletService {

    /**
     * 判断是否为油费钱包
     *
     * @param addr
     * @return
     */
    boolean isGasWallet(String addr);

}
