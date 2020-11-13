package com.blockchain.server.tron.service;

import com.blockchain.server.tron.dto.TronTokenDTO;
import com.blockchain.server.tron.entity.TronToken;

import java.util.HashSet;
import java.util.List;

/**
 * @author Harvey
 * @date 2019/2/25 17:08
 * @user WIN10
 */
public interface TronTokenService {

    /**
     * 根据币种名称查询币种信息
     *
     * @param tokenAddr
     * @return
     */
    TronToken selectTronTokenByTokenName(String tokenAddr);

    /**
     * 查找eos所有币种信息
     *
     * @return
     */
    List<TronToken> listTronToken();

    /**
     * 根据代币名称查询
     *
     * @param tokenSymbol
     * @return
     */
    TronToken selectByTokenSymbol(String tokenSymbol);

    /**
     * 根据币种地址获取以太坊的币种信息
     *
     * @param tokenAddr 币种地址
     * @return
     */
    TronToken findByTokenAddr(String tokenAddr);

    /**
     * 查找所有币种信息
     * @return
     */
    List<TronToken> selectAll();

    /**
     * 从币种列表中获取单个币种信息
     * @param listToken
     * @param tronTokenId
     * @return
     */
    TronToken listTokenGetTronToken(List<TronToken> listToken, String tronTokenId);

    /**
     * 查询TRC20币种信息
     * @param tokenSymbol
     * @return
     */
    TronTokenDTO selectTRC20Token(String tokenSymbol);
}
