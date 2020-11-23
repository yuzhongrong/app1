package com.blockchain.server.cmc.service;

public interface BtcWalletKeyService {

    void insertWalletKey(String address, String privateKey);

}
