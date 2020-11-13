package com.blockchain.server.eth.service.impl;


import com.blockchain.common.base.util.ExceptionPreconditionUtils;
import com.blockchain.server.eth.common.enums.EthWalletEnums;
import com.blockchain.server.eth.common.exception.EthWalletException;
import com.blockchain.server.eth.common.util.RedisWalletAddrUtil;
import com.blockchain.server.eth.common.util.RedisWalletAddrUtil;
import com.blockchain.server.eth.entity.EthToken;
import com.blockchain.server.eth.entity.EthWallet;
import com.blockchain.server.eth.mapper.EthTokenMapper;
import com.blockchain.server.eth.service.IEthTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 以太坊币种表——业务接口
 *
 * @author YH
 * @date 2019年2月16日17:25:02
 */
@Service
public class EthTokenServiceImpl implements IEthTokenService {

    @Autowired
    EthTokenMapper ethTokenMapper;

    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public EthToken findByTokenAddr(String tokenAddr) {
        ExceptionPreconditionUtils.checkStringNotBlank(tokenAddr,
                new EthWalletException(EthWalletEnums.NULL_TOKENADDR));
        String _tokenAddr = tokenAddr.toLowerCase(); // 全部转为小写
        return ethTokenMapper.selectByPrimaryKey(_tokenAddr);
    }

    @Override
    public EthToken findByTokenName(String tokenName) {
        ExceptionPreconditionUtils.checkStringNotBlank(tokenName,
                new EthWalletException(EthWalletEnums.NULL_TOKENADDR));
        EthToken where = new EthToken();
        where.setTokenSymbol(tokenName);
        EthToken ethToken = null;
        try {
            ethToken = ethTokenMapper.selectOne(where);
        } catch (Exception e) {
            throw new EthWalletException(EthWalletEnums.IEXIST_TOKENADDRS);
        }
        if (ethToken == null) {
            throw new EthWalletException(EthWalletEnums.INEXISTENCE_TOKENADDR);
        }
        return ethToken;
    }

    @Override
    public List<EthToken> selectAll() {
        return ethTokenMapper.selectAll();
    }

    @Override
    public Map<String, EthToken> selectMaps() {
        Map<String, EthToken> coins = RedisWalletAddrUtil.getWalletCoinAddrs(redisTemplate);
        if (coins.isEmpty()) {
            coins = RedisWalletAddrUtil.setWalletCoinAddrs(ethTokenMapper.selectAll(), redisTemplate);
        }
        return coins;
    }

    @Override
    public Set<String> selectTokenAddrs() {
        return ethTokenMapper.selectAllTokenAddr();
    }
}
