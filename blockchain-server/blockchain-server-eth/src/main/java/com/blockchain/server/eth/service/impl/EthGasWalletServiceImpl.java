package com.blockchain.server.eth.service.impl;


import com.blockchain.common.base.util.ExceptionPreconditionUtils;
import com.blockchain.common.base.util.RSACoderUtils;
import com.blockchain.server.eth.common.enums.EthWalletEnums;
import com.blockchain.server.eth.common.exception.EthWalletException;
import com.blockchain.server.eth.common.util.RedisPrivateUtil;
import com.blockchain.server.eth.common.util.RedisWalletAddrUtil;
import com.blockchain.server.eth.dto.Web3jWalletDTO;
import com.blockchain.server.eth.entity.EthGasWallet;
import com.blockchain.server.eth.entity.EthWalletKey;
import com.blockchain.server.eth.mapper.EthGasWalletMapper;
import com.blockchain.server.eth.mapper.EthWalletKeyMapper;
import com.blockchain.server.eth.service.IEthGasWalletService;
import com.blockchain.server.eth.service.IEthWalletKeyService;
import com.blockchain.server.eth.web3j.IWalletWeb3j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Set;

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
