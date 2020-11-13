package com.blockchain.server.otc.service;

import java.math.BigDecimal;

public interface BillService {

    /***
     * 新增资金对账记录
     * @param userId
     * @param recordNumber
     * @param freeBalance
     * @param freezeBalance
     * @param billType
     * @param coinName
     * @return
     */
    int insertBill(String userId, String recordNumber, BigDecimal freeBalance, BigDecimal freezeBalance, String billType, String coinName);
}
