package com.blockchain.server.otc.service;

public interface UserHandleLogService {
    /***
     * 新建用户操作日志
     * @param userId
     * @param handleNumber
     * @param handleType
     * @param handleNumberType
     * @return
     */
    int insertUserHandleLog(String userId, String handleNumber, String handleType, String handleNumberType);
}
