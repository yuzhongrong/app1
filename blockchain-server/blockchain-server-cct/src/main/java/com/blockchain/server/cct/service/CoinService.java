package com.blockchain.server.cct.service;

import com.blockchain.server.cct.dto.coin.TradingOnDTO;
import com.blockchain.server.cct.entity.Coin;

import java.util.List;

public interface CoinService {

    /***
     * 根据币对、状态查询币种信息
     * @param coinName
     * @param unitName
     * @param status
     * @return
     */
    Coin selectCoin(String coinName, String unitName, String status);

    /***
     * 校验币对是否存在、是否可用
     * @param coinName
     * @param unitName
     * @return
     */
    Coin checkCoinIsYes(String coinName, String unitName);

    /***
     * 查询可用币种列表
     * @return
     */
    List<TradingOnDTO> listCoinByUnit(String unitName, String stauts);

    /***
     * 查询主币列表
     * @return
     */
    List<String> listCoinGroupByUnit();

}
