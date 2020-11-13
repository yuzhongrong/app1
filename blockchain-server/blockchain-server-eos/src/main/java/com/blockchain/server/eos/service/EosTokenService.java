package com.blockchain.server.eos.service;

import com.blockchain.server.eos.dto.TokenDTO;
import com.blockchain.server.eos.entity.Token;

import java.util.HashSet;
import java.util.List;

/**
 * @author Harvey
 * @date 2019/2/25 17:08
 * @user WIN10
 */
public interface EosTokenService {

    /**
     * 根据币种名称查询币种信息
     * @param tokenName
     * @return
     */
    TokenDTO selectEosTokenByTokenName(String tokenName);

    /**
     * 查询所有代币地址
     * @return
     */
    HashSet<String> listTokenNameAll();

    /**
     * 查找eos所有币种信息
     * @return
     */
    List<Token> listEosToken();

    /**
     * 根据代币名称查询
     *
     * @param tokenSymbol
     * @return
     */
    Token selectByTokenSymbol(String tokenSymbol);
}
