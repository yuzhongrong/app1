package com.blockchain.server.currency.service;


import com.blockchain.server.currency.dto.CurrencyPairDTO;
import com.blockchain.server.currency.model.CurrencyPair;

import java.util.List;

public interface CurrencyPairService {

    CurrencyPair get(String currencyPair);

    void update(CurrencyPair currencyPair);

    /**
     * 获取可用币对列表
     * @return
     */
    List<CurrencyPairDTO> getUsableList();


    /**
     * 获取首页列表
     * @return
     */
    List<CurrencyPairDTO> getHomeList();

    /**
     * 根据主要货币获取其所有币对
     * @param currencyName
     * @return
     */
    List<CurrencyPairDTO> getQuoteList(String currencyName);

}
