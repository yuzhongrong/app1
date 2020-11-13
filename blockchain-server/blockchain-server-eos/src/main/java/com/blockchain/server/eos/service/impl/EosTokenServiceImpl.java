package com.blockchain.server.eos.service.impl;

import com.blockchain.common.base.util.ExceptionPreconditionUtils;
import com.blockchain.server.eos.dto.TokenDTO;
import com.blockchain.server.eos.entity.Token;
import com.blockchain.server.eos.mapper.TokenMapper;
import com.blockchain.server.eos.service.EosTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

/**
 * @author Harvey
 * @date 2019/2/25 17:09
 * @user WIN10
 */
@Service
public class EosTokenServiceImpl implements EosTokenService {

    @Autowired
    private TokenMapper tokenMapper;

    /**
     * 根据币种名称查询币种信息
     * @param tokenName
     * @return
     */
    @Override
    public TokenDTO selectEosTokenByTokenName(String tokenName) {
        ExceptionPreconditionUtils.notEmpty(tokenName);
        return tokenMapper.selectEosTokenByTokenName(tokenName);
    }

    /**
     * 查询所有代币名称
     * @return
     */
    @Override
    public HashSet<String> listTokenNameAll() {
        return tokenMapper.listTokenNameAll();
    }

    /**
     * 查找所有币种信息
     * @return
     */
    @Override
    public List<Token> listEosToken() {
        return tokenMapper.selectAll();
    }


    @Override
    public Token selectByTokenSymbol(String tokenSymbol) {
        ExceptionPreconditionUtils.notEmpty(tokenSymbol);
        Token where = new Token();
        where.setTokenSymbol(tokenSymbol);
        Token token = tokenMapper.selectOne(where);
        ExceptionPreconditionUtils.notEmpty(token);
        return token;
    }
}
