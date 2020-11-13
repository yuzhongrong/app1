package com.blockchain.server.cct.service;

import com.blockchain.server.cct.entity.TradingRecord;

import java.util.List;

public interface TradingRecordService {

    /***
     * 插入成交双方订单记录
     * @param record
     * @return
     */
    int insertTradingRecord(TradingRecord record);

    /***
     * 根据交易币对查询成交记录
     * @param coinName
     * @param unitName
     * @return
     */
    List<TradingRecord> listRecordByCoinAndUnit(String coinName, String unitName);

    /***
     * 根据交易币对查询最新一条成交记录
     * @param coinName
     * @param unitName
     * @return
     */
    TradingRecord getRecordByCoinAndUnitLimitOne(String coinName, String unitName);
}
