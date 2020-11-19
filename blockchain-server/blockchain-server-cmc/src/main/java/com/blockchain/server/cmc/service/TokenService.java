package com.blockchain.server.cmc.service;

import com.blockchain.server.cmc.dto.TokenDTO;
import com.blockchain.server.cmc.entity.Token;

import java.util.List;

public interface TokenService {

    /**
     * 获取币种
     *
     * @return
     */
    List<TokenDTO> listToken();

    /**
     * 获取币种，根据tokenid
     *
     * @param tokenId 币种id
     * @return
     */
    TokenDTO selectTokenById(Integer tokenId);

    /**
     * 通过币种名称获取币种id，仅限系统 0-LTC
     *
     * @param tokenName 币种名称
     * @return
     */
    int getAndVerifyTokenIdByName(String tokenName);

    /**
     * 通过币种id获取币种名称，仅限系统 0-LTC
     *
     * @param tokenId 币种id
     * @return
     */
    String getAndVerifyTokenNameById(int tokenId);

    /**
     * 獲取币种，根据名称
     * @param coinName
     * @return
     */
    Token selectByTokenName(String coinName);

}
