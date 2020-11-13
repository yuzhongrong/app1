package com.blockchain.server.eos.service;

import com.blockchain.common.base.dto.WalletBalanceBatchDTO;
import com.blockchain.common.base.dto.WalletBalanceDTO;
import com.blockchain.common.base.dto.WalletChangeDTO;
import com.blockchain.common.base.dto.WalletOrderDTO;
import com.blockchain.server.eos.dto.WalletDTO;
import com.blockchain.server.eos.entity.Wallet;
import com.blockchain.server.eos.entity.WalletTransfer;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author Harvey
 * @date 2019/2/18 16:39
 * @user WIN10
 */
public interface EosWalletService {

    /**
     * 通过
     *
     * @param walletId
     * @return
     */
    Wallet selectWalletById(String walletId);

    Wallet selectWalletByIdAndTokenName(Integer walletId, String tokenName);

    /**
     * 用户充值修改钱包金额
     *
     * @param balance
     * @param id
     */
    int updateWalletBalanceByIdInRowLock(BigDecimal balance, Integer id, Date modifyTime);

    /**
     * 用户提现
     *
     * @param password
     * @param account
     * @param amount
     * @param tokenName
     * @param walletType
     * @return
     */
    int handleWithdrawDeposit(String userOpenId, String password, String account, BigDecimal amount, String tokenName,
                              String walletType, String remark, String verifyCode, String username);

    /**
     * 币币交易金额记录
     * @param userOpenId
     * @param tokenName
     * @param amount
     * @return
     */
    /*int handleTransferCCT(String userOpenId, String tokenName, BigDecimal amount);*/

    /**
     * 查询钱包
     *
     * @param walletType
     * @param userOpenId
     * @return
     */
    List<WalletDTO> listWalletByWalletType(String walletType, String userOpenId);

    /**
     * 冻结与解冻方法
     *
     * @param walletOrderDTO
     * @return
     */
    Integer updateWalletOrder(WalletOrderDTO walletOrderDTO);

    /**
     * 通过用户id和币种名称和钱包标识查找用户
     *
     * @param userOpenId
     * @param tokenName
     * @param walletType
     * @return
     */
    WalletDTO selectWallet(String userOpenId, String tokenName, String walletType);

    /**
     * 钱包余额变动方法
     *
     * @param walletChangeDTO
     */
    Integer handleWalletChange(WalletChangeDTO walletChangeDTO);

    /**
     * 初始化钱包方法
     *
     * @param userId
     * @return
     */
    Integer initEosWallet(String userId);

    /**
     * 根据符号查询钱包
     *
     * @param userId
     * @param tokenSymbol
     * @param walletType
     * @return
     */
    WalletDTO selectWalletByTokenSymbol(String userId, String tokenSymbol, String walletType);

    /**
     * 查询eos出块
     *
     * @param bolckNum
     * @param hashId
     * @return
     */
    Boolean getTransaction(String bolckNum, String hashId);

    /**
     * 提现失败处理
     *
     * @param tx
     */
    void updateTxOutError(WalletTransfer tx);

    /**
     * 提现成功处理
     *
     * @param tx
     */
    void updateTxOutSuccess(WalletTransfer tx);


    /**
     * 根据用户ID,钱包类型，币种名称查询钱包信息
     *
     * @param userId
     * @param walletType
     * @param coinName
     * @return
     */
    Wallet findWallet(String userId, String walletType, String coinName);

    /**
     * 划转
     *
     * @param userOpenId
     * @param fromType
     * @param toType
     * @param coinName
     * @param amount
     * @return
     */
    WalletTransfer handleTransfer(String userOpenId, String fromType, String toType, String coinName, BigDecimal amount);

    /**
     * 获取钱包余额，通过用户id，与钱包类型
     *
     * @param userOpenId  用户id
     * @param walletTypes 钱包类型
     * @return 币种对应的可用、冻结余额
     */
    List<WalletBalanceDTO> getBalanceByIdAndTypes(String userOpenId, String[] walletTypes);

    void getBalanceByIdAndTypesBatch(String[] walletTypes);
}
