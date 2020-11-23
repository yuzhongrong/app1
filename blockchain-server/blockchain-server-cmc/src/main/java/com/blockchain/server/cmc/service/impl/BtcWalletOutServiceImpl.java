package com.blockchain.server.cmc.service.impl;

import com.blockchain.server.cmc.common.enums.BtcEnums;
import com.blockchain.server.cmc.common.exception.BtcException;
import com.blockchain.server.cmc.entity.BtcWalletOut;
import com.blockchain.server.cmc.mapper.BtcWalletOutMapper;
import com.blockchain.server.cmc.service.BtcWalletOutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BtcWalletOutServiceImpl implements BtcWalletOutService {

    @Autowired
    private BtcWalletOutMapper btcWalletOutMapper;

    @Override
    public void InsertWalletOut(BtcWalletOut btcWalletOut) {
        btcWalletOut.setId(UUID.randomUUID().toString());
        int countIwo = btcWalletOutMapper.insertSelective(btcWalletOut);
        if (countIwo != 1) {
            throw new BtcException(BtcEnums.INSERT_WALLET_OUT_ERROR);
        }
    }

}
