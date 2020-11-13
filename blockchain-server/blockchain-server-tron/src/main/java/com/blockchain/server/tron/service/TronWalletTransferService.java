package com.blockchain.server.tron.service;


import com.blockchain.server.tron.dto.RefundOutDTO;
import com.blockchain.server.tron.dto.TronTokenDTO;
import com.blockchain.server.tron.dto.TronWalletTransferDto;
import com.blockchain.server.tron.entity.TronToken;
import com.blockchain.server.tron.entity.TronWalletTransfer;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author Harvey
 * @date 2019/2/19 11:39
 * @user WIN10
 */
public interface TronWalletTransferService {

    /**
     * 添加记录并修改账户金额
     *
     * @param txID
     * @param fromAddress
     * @param fromHexAddress
     * @param toHexAddress
     * @param amount
     * @param transferType
     * @param tokenAddr
     * @param tokenSymbol
     * @return
     */
    Integer handleWalletAndWalletTransfer(String txID, String fromAddress, String fromHexAddress, String toHexAddress, String amount, String transferType, String tokenAddr, String tokenSymbol, Integer tokenDecimal);

    /**
     * 插入记录
     *
     * @param tronWalletTransfer
     * @return
     */
    Integer insertWalletTransfer(TronWalletTransfer tronWalletTransfer);

    /**
     * 查询钱包交易记录
     *
     * @param userOpenId
     * @param tokenAddr
     * @param walletType
     * @return
     */
    List<TronWalletTransferDto> listWalletTransfer(String userOpenId, String tokenAddr, String walletType);

    /**
     * 根据记录类型与记录状态查询记录
     *
     * @param transferType 记录类型
     * @param Status       记录状态
     * @param pageNum      当前页数
     * @param pageSize     页数展示条数
     * @return
     */
    List<TronWalletTransfer> selectByTxTypeAndStatus(String transferType, Integer Status, Integer pageNum, Integer pageSize);

    /**
     * 更新状态
     *
     * @param id
     * @param status
     * @param date
     */
    void updateStatus(String id, Integer status, Date date);

    /**
     * 根据hash查询交易记录
     *
     * @param hashId
     * @return
     */
    TronWalletTransfer selectByHashId(String hashId);

    /**
     * 根据hash查询是否存在数据
     *
     * @param hash
     * @param transferType
     * @return
     */
    Integer countByHashAndTransferType(String hash, String transferType);

    /**
     * 插入一条钱包流水记录
     *
     * @param hash         转账唯一标识
     * @param fromHexAddress     支付地址
     * @param toHexAddress       接收地址
     * @param amount       转账余额
     * @param amountCoin   转账币种类型
     * @param transferType 转账类型
     * @return
     */
    TronWalletTransfer insert(String hash, String fromHexAddress, String toHexAddress, BigDecimal amount, TronToken amountCoin,
                             String transferType);
    /**
     * 插入一条钱包流水记录
     *
     * @param hash         转账唯一标识
     * @param fromHexAddress     支付地址
     * @param toHexAddress       接收地址
     * @param amount       转账余额
     * @param amountCoin   转账币种类型
     * @param transferType 转账类型
     * @return
     */
    TronWalletTransfer insert(String hash, String fromHexAddress, String toHexAddress, BigDecimal amount, TronTokenDTO amountCoin,
                             String transferType);

    /**
     * 查询记录是否存在
     *
     * @param id
     * @param txType
     * @return
     */
    Integer findByHashAndTypeTheLime(String id, String txType);

    /**
     * 根据要求查询一条交易信息
     * @param transfer
     * @return
     */
    TronWalletTransfer selectOne(TronWalletTransfer transfer);

    /**
     * 根据要求查询多条交易信息
     * @param transfer
     * @return
     */
    List<TronWalletTransfer> select(TronWalletTransfer transfer);

    /**
     * 根据商户单号查询退款记录
     *
     * @param tradeNo 商户单号
     * @return
     */
    List<RefundOutDTO> selectRefundByTradeNo(String tradeNo);
}
