package com.blockchain.server.eos.service;

import com.blockchain.server.eos.dto.WalletTransferDTO;
import com.blockchain.server.eos.entity.WalletTransfer;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

/**
 * @author Harvey
 * @date 2019/2/19 11:39
 * @user WIN10
 */
public interface EosWalletTransferService {

    /**
     * 添加记录并修改账户金额
     * @param hash
     * @param transferType
     * @param amount
     * @param tokenName
     * @param tokenSymbol
     * @param memo
     * @param accountId
     * @return
     */
    int handleWalletAndWalletTransfer(String hash, Integer accountId, String accountName, String transferType, BigDecimal amount, String tokenName, String tokenSymbol, String memo, BigInteger blockNumber);

    /**
     * 插入记录
     * @param walletTransfer
     * @return
     */
    int insertWalletTransfer(WalletTransfer walletTransfer);

    /**
     * 插入数据
     * @param from
     * @param to
     * @param amount
     * @param tokenName
     * @param tokenSymbol
     * @param gasAmount
     * @param gasTokenName
     * @param gasTokenType
     * @param gasTokenSymbol
     * @param type
     * @param status
     * @param remark
     * @param date
     * @return
     */
    int insertWalletTransfer(Integer from, Integer to, String account, BigDecimal amount, String tokenName, String tokenSymbol, BigDecimal gasAmount, String gasTokenName, String gasTokenType, String gasTokenSymbol, String type, Integer status, String remark, Date date);

    /**
     * 查询钱包交易记录
     * @param userOpenId
     * @param tokenName
     * @param walletType
     * @return
     */
    List<WalletTransferDTO> listWalletTransfer(String userOpenId, String tokenName, String walletType);

    /**
     * 根据记录类型与记录状态查询记录
     *
     * @param transferType 记录类型
     * @param Status       记录状态
     * @param pageNum      当前页数
     * @param pageSize     页数展示条数
     * @return
     */
    List<WalletTransfer> selectByTxTypeAndStatus(String transferType, int Status, Integer pageNum, Integer pageSize);

    /**
     * 更新状态
     * @param id
     * @param status
     * @param date
     */
    void  updateStatus(String id, int status, Date date);

    /**
     * 根据hash查询交易记录
     * @param hashId
     * @return
     */
    WalletTransfer selectByHashId(String hashId);

    /**
     * 根据hash查询是否存在数据
     * @param hash
     * @param transferType
     * @return
     */
    Integer countByHashAndTransferType(String hash, String transferType);
}
