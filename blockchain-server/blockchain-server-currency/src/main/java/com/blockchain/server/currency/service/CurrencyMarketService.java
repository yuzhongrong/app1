package com.blockchain.server.currency.service;


import com.blockchain.server.currency.dto.CurrencyMarketDTO;
import com.blockchain.server.currency.dto.CurrencyMarketHistoryDTO;
import com.blockchain.server.currency.dto.CurrencyMarketKDTO;
import com.blockchain.server.currency.dto.CurrencyMinMarketKDTO;
import com.blockchain.server.currency.model.CurrencyMarket;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface CurrencyMarketService {

    /**
     * 保存行情信息
     *
     * @param currencyMarket
     * @return
     */
    CurrencyMarket save(CurrencyMarket currencyMarket);


    /**
     * 保存最新交易行情信息
     *
     * @param coinName  币种名称
     * @param unitName  币种单位
     * @param amount
     * @param total
     * @param timestamp
     * @return
     */
    CurrencyMarketDTO save(String coinName,
                           String unitName,
                           BigDecimal amount,
                           BigDecimal total,
                           Long timestamp);

    /**
     * 保存最新交易行情信息
     *
     * @param coinName    币种名称
     * @param unitName    币种单位
     * @param amount
     * @param total
     * @param timestamp
     * @param tradingType
     * @param  tradingVolume 交易量
     * @return
     */
    CurrencyMarketDTO save(String coinName,
                           String unitName,
                           BigDecimal amount,
                           BigDecimal total,
                           Long timestamp,
                           String tradingType,BigDecimal tradingVolume);

    /**
     * 获取币对的最新行情信息
     *
     * @param currencyPair
     * @return
     */
    CurrencyMarketDTO get(String currencyPair);

    /***
     * 获取币对的今天之前的最新行情信息
     * @param currencyPair
     * @return
     */
    CurrencyMarketDTO getLast(String currencyPair);

    /**
     * 获取法币对应的所有主要数字货币行情
     *
     * @param coin
     * @return
     */
    Map<String, Double> getRates(String coin);

    /**
     * 获取可用行情列表
     *
     * @return
     */
    List<CurrencyMarketDTO> getList();

    /**
     * 获取首页行情列表
     *
     * @return
     */
    List<CurrencyMarketDTO> getHomeList();

    /**
     * 获取行情涨跌榜
     *
     * @return
     */
    List<CurrencyMarketDTO> getTopList();

    /**
     * 获取历史成交列表
     *
     * @param currencyPair
     * @return
     */
    List getHistoryList(String currencyPair);

    /**
     * 根据主要货币获取其所有币对行情
     *
     * @param currencyName
     * @return
     */
    List<CurrencyMarketDTO> getQuoteList(String currencyName);

    /**
     * 查询行情K线图数据
     *
     * @param currencyPair
     * @param timeType     时间段类型：MINUTE，HOUR，DAY，WEEK，MONTH
     * @param timeNumber   时间间隔数据
     * @param start
     * @param stop
     * @return
     */
    List<CurrencyMarketKDTO> queryForK(String currencyPair, String timeType, Integer timeNumber, Integer start, Integer stop);

    /**
     * 查询行情K线图数据 - 倒序
     *
     * @param currencyPair
     * @param timeType     时间段类型：MINUTE，HOUR，DAY，WEEK，MONTH
     * @param timeNumber   时间间隔数据
     * @param start
     * @param stop
     * @return
     */
    List<CurrencyMarketKDTO> queryForKSortDESC(String currencyPair, String timeType, Integer timeNumber, Integer start, Integer stop);
}
