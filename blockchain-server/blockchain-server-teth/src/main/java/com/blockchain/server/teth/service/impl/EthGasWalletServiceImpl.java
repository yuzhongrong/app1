package com.blockchain.server.teth.service.impl;


import com.blockchain.server.teth.entity.EthGasWallet;
import com.blockchain.server.teth.mapper.EthGasWalletMapper;
import com.blockchain.server.teth.service.IEthGasWalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 以太坊钱包表——业务接口
 *
 * @author YH
 * @date 2019年2月16日17:25:02
 */
@Service
public class EthGasWalletServiceImpl implements IEthGasWalletService {

    @Autowired
    EthGasWalletMapper ethGasWalletMapper;

    @Override
    public boolean isGasWallet(String addr) {
        EthGasWallet gasWallet = ethGasWalletMapper.selectByPrimaryKey(addr.toLowerCase());
        return null != gasWallet;
    }
}
