package com.blockchain.server.eth.service;

import com.blockchain.server.eth.dto.EthGFCTransfer;
import com.blockchain.server.eth.entity.EthToken;
import com.blockchain.server.eth.entity.EthWallet;
import com.blockchain.server.eth.entity.EthWalletTransfer;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 以太坊钱包记录表——业务接口
 *
 * @author YH
 * @date 2019年2月16日17:09:19
 */
public interface IEthWalletTransferService {

    /**
     * 插入一条钱包流水记录
     *
     * @param hash         转账唯一标识
     * @param fromAddr     支付地址
     * @param toAddr       接收地址
     * @param amount       转账余额
     * @param amountCoin   转账币种类型
     * @param transferType 转账类型
     * @return
     */
    EthWalletTransfer insert(String hash, String fromAddr, String toAddr, BigDecimal amount, EthToken amountCoin,
                             String transferType, Date date);

    /**
     * 插入一条钱包流水记录
     *
     * @param hash         转账唯一标识
     * @param fromAddr     支付地址
     * @param toAddr       接收地址
     * @param amount       转账余额
     * @param amountCoin   转账币种类型
     * @param transferType 转账类型
     * @param status       转账状态
     * @return
     */
    EthWalletTransfer insert(String hash, String fromAddr, String toAddr, BigDecimal amount, EthToken amountCoin,
                             String transferType, int status, Date date);

    /**
     * 插入一条钱包流水记录
     *
     * @param hash         转账唯一标识
     * @param fromAddr     支付地址
     * @param toAddr       接收地址
     * @param amount       转账余额
     * @param gas          转账手续费
     * @param amountCoin   转账币种类型
     * @param gasCoin      手续费币种类型
     * @param transferType 转账类型
     * @param status       转账状态
     * @return
     */
    EthWalletTransfer insert(String hash, String fromAddr, String toAddr, BigDecimal amount, BigDecimal gas,
                             EthToken amountCoin, EthToken gasCoin, String transferType, int status, Date date);

    /**
     * 插入一条钱包流水记录
     *
     * @param hash         转账唯一标识
     * @param fromAddr     支付地址
     * @param toAddr       接收地址
     * @param amount       转账余额
     * @param gas          转账手续费
     * @param amountCoin   转账币种类型
     * @param gasCoin      手续费币种类型
     * @param transferType 转账类型
     * @param status       转账状态
     * @param remark       转账备注
     * @return
     */
    EthWalletTransfer insert(String hash, String fromAddr, String toAddr, BigDecimal amount, BigDecimal gas,
                             EthToken amountCoin, EthToken gasCoin, String transferType, int status, String remark, Date date);

    /**
     * 修改记录gas费用
     *
     * @param ethWalletTransfer 记录
     */
    EthWalletTransfer updateGas(EthWalletTransfer ethWalletTransfer);

    /**
     * 修改记录状态
     *
     * @param ethWalletTransfer 记录
     */
    void updateStatus(EthWalletTransfer ethWalletTransfer);

    /**
     * 修改记录状态
     *
     * @param id
     * @param status 记录状态
     */
    void updateStatus(String id, int status, Date date);

    /**
     * 根据记录类型与记录状态查询记录
     *
     * @param transferType 记录类型
     * @param Status       记录状态
     * @param pageNum      当前页数
     * @param pageSize     页数展示条数
     * @return
     */
    List<EthWalletTransfer> selectByTxTypeAndStatus(String transferType, int Status, Integer pageNum, Integer pageSize);

    /**
     * 获取钱包记录
     *
     * @param userOpenId 用户id
     * @param tokenAddr  币种地址
     * @param walletType 应用类型，CCT
     * @return
     */
    List<EthWalletTransfer> selectTransfer(String userOpenId, String tokenAddr, String walletType);

    EthGFCTransfer getGFCTransfer(String orderId);
}
