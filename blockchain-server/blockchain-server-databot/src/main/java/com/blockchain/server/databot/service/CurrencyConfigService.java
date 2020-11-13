package com.blockchain.server.databot.service;

import com.blockchain.server.databot.entity.CurrencyConfig;

import java.util.List;

public interface CurrencyConfigService {

    /***
     * 根据币对查询币种信息
     * @param currencyPair
     * @return
     */
    CurrencyConfig selectByCurrencyPair(String currencyPair);

    /***
     * 根据状态查询币种信息
     * @return
     */
    List<CurrencyConfig> selectByStatus(String status);

    /***
     * 根据币对查询币种信息，判断币种是否可用
     * true为可用
     * @param currencyPair
     * @return
     */
    boolean checkCurrencyPairStatusIsY(String currencyPair);
}
