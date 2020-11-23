package com.blockchain.server.cmc.service.impl;

import com.blockchain.server.cmc.common.enums.BtcEnums;
import com.blockchain.server.cmc.common.exception.BtcException;
import com.blockchain.server.cmc.common.util.BtcRASUtils;
import com.blockchain.server.cmc.entity.BtcWalletKey;
import com.blockchain.server.cmc.mapper.BtcWalletKeyMapper;
import com.blockchain.server.cmc.service.BtcWalletKeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BtcWalletKeyServiceImpl implements BtcWalletKeyService {
    @Autowired
    private BtcWalletKeyMapper btcWalletKeyMapper;

    @Override
    public void insertWalletKey(String address, String privateKey) {
        BtcWalletKey btcWalletKey = new BtcWalletKey();
        btcWalletKey.setAddr(address);
        try {
            privateKey = BtcRASUtils.encrypt(privateKey);
        } catch (Exception e) {
            throw new BtcException(BtcEnums.CREATE_WALLET_ERROR);
        }
        btcWalletKey.setPrivateKey(privateKey);
        int countIw = btcWalletKeyMapper.insertSelective(btcWalletKey);
        if (countIw != 1) {
            throw new BtcException(BtcEnums.CREATE_WALLET_ERROR);
        }
    }

}
