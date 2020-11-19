package com.blockchain.server.cmc.service.impl;

import com.blockchain.server.cmc.common.enums.ExceptionEnums;
import com.blockchain.server.cmc.common.exception.ServiceException;
import com.blockchain.server.cmc.common.util.RASUtils;
import com.blockchain.server.cmc.entity.WalletKey;
import com.blockchain.server.cmc.mapper.WalletKeyMapper;
import com.blockchain.server.cmc.service.WalletKeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WalletKeyServiceImpl implements WalletKeyService {
    @Autowired
    private WalletKeyMapper walletKeyMapper;

    @Override
    public void insertWalletKey(String address, String privateKey) {
        WalletKey walletKey = new WalletKey();
        walletKey.setAddr(address);
        try {
            privateKey = RASUtils.encrypt(privateKey);
        } catch (Exception e) {
            throw new ServiceException(ExceptionEnums.CREATE_WALLET_ERROR);
        }
        walletKey.setPrivateKey(privateKey);
        int countIw = walletKeyMapper.insertSelective(walletKey);
        if (countIw != 1) {
            throw new ServiceException(ExceptionEnums.CREATE_WALLET_ERROR);
        }
    }

}
