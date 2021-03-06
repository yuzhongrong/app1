package com.blockchain.server.btc.service;

import com.blockchain.server.btc.dto.BtcTokenDTO;
import com.blockchain.server.btc.entity.BtcToken;

import java.util.List;

public interface BtcTokenService {

    /**
     * 获取币种
     *
     * @return
     */
    List<BtcTokenDTO> listToken();

    /**
     * 获取币种，根据tokenid
     *
     * @param tokenId 币种id
     * @return
     */
    BtcTokenDTO selectTokenById(Integer tokenId);

    /**
     * 通过币种名称获取币种id，仅限系统 0-BTC，31-USDT
     *
     * @param tokenName 币种名称
     * @return
     */
    int getAndVerifyTokenIdByName(String tokenName);

    /**
     * 通过币种id获取币种名称，仅限系统 0-BTC，31-USDT
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
    BtcToken selectByTokenName(String coinName);

}
