package com.blockchain.server.btc.service;

public interface BtcWalletKeyService {

    void insertWalletKey(String address, String privateKey);

}
