package com.blockchain.server.otc.service;

import java.math.BigDecimal;

public interface WalletService {

    /***
     * 冻结、解冻余额
     * @param userId
     * @param publishId 订单id
     * @param coinName 基本货币
     * @param unitName 二级货币
     * @param freeBalance 可用余额
     * @param freezeBalance 冻结余额
     */
    void handleBalance(String userId, String publishId, String coinName,
                       String unitName, BigDecimal freeBalance, BigDecimal freezeBalance);

    /***
     * 扣除、增加真实余额
     * @param userId
     * @param detailId 成交记录id
     * @param coinName 基本货币
     * @param unitName 二级货币
     * @param freeBalance 可用余额
     * @param freezeBalance 冻结余额
     * @param gasBalance 手续费
     */
    void handleRealBalance(String userId, String detailId, String coinName,
                           String unitName, BigDecimal freeBalance, BigDecimal freezeBalance, BigDecimal gasBalance);

    /***
     * 验证密码
     * @param pass 公钥加密后的密码
     */
    void isPassword(String pass);
}
