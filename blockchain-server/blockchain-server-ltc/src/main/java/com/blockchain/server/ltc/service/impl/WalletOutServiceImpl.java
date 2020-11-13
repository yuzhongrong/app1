package com.blockchain.server.ltc.service.impl;

import com.blockchain.server.ltc.common.enums.ExceptionEnums;
import com.blockchain.server.ltc.common.exception.ServiceException;
import com.blockchain.server.ltc.entity.WalletOut;
import com.blockchain.server.ltc.mapper.WalletOutMapper;
import com.blockchain.server.ltc.service.WalletOutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class WalletOutServiceImpl implements WalletOutService {

    @Autowired
    private WalletOutMapper walletOutMapper;

    @Override
    public void InsertWalletOut(WalletOut walletOut) {
        walletOut.setId(UUID.randomUUID().toString());
        int countIwo = walletOutMapper.insertSelective(walletOut);
        if (countIwo != 1) {
            throw new ServiceException(ExceptionEnums.INSERT_WALLET_OUT_ERROR);
        }
    }

}
