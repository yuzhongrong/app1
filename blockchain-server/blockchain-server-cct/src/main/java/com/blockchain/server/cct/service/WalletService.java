package com.blockchain.server.cct.service;

import java.math.BigDecimal;

public interface WalletService {

    /***
     * 冻结、解冻余额
     * @param userId
     * @param publishId 订单id
     * @param tokenName 处理货币
     * @param coinNet 主网标识
     * @param freeBalance 可用余额
     * @param freezeBalance 冻结余额
     */
    void handleBalance(String userId, String publishId, String tokenName,
                       String coinNet, BigDecimal freeBalance, BigDecimal freezeBalance);

    /***
     * 扣除、增加真实余额
     * @param userId
     * @param detailId 成交记录id
     * @param tokenName 处理货币
     * @param coinNet 主网标识
     * @param freeBalance 可用余额
     * @param freezeBalance 冻结余额
     * @param gasBalance 手续费
     */
    void handleRealBalance(String userId, String detailId, String tokenName,
                           String coinNet, BigDecimal freeBalance, BigDecimal freezeBalance, BigDecimal gasBalance);

    /***
     * 验证密码
     * @param pass 公钥加密后的密码
     */
    void isPassword(String pass);
}
