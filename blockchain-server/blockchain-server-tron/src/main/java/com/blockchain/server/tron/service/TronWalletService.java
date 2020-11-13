package com.blockchain.server.tron.service;


import com.blockchain.common.base.dto.WalletBalanceBatchDTO;
import com.blockchain.common.base.dto.WalletBalanceDTO;
import com.blockchain.common.base.dto.WalletChangeDTO;
import com.blockchain.common.base.dto.WalletOrderDTO;
import com.blockchain.server.tron.dto.PayInsertOutDTO;
import com.blockchain.server.tron.dto.RechargeInDTO;
import com.blockchain.server.tron.dto.RechargeOutDTO;
import com.blockchain.server.tron.dto.RefundOutDTO;
import com.blockchain.server.tron.entity.TronWallet;
import com.blockchain.server.tron.entity.TronWalletPrepayment;
import com.blockchain.server.tron.entity.TronWalletTransfer;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Harvey
 * @date 2019/2/18 16:39
 * @user WIN10
 */
public interface TronWalletService {


    /**
     * 通过16进制地址和币种id查询钱包
     *
     * @param hexAddress
     * @param tokenId
     * @return
     */
    TronWallet selectWalletByIdAndTokenName(String hexAddress, String tokenId);

    /**
     * 用户充值修改钱包金额
     *
     * @param balance
     * @param address
     */
    Integer updateWalletBalanceByAddressInRowLock(BigDecimal balance, String address, String tokenAddr);

    /**
     * 用户提现
     *
     * @param password
     * @param address
     * @param amount
     * @param tokenId
     * @param walletType
     * @return
     */
    int handleWithdrawDeposit(String userOpenId, String password, String address, BigDecimal amount, String tokenId,
                              String walletType, String verifyCode, String account);

    /**
     * 币币交易金额记录
     *
     * @param userOpenId
     * @param tokenId
     * @param amount
     * @return
     */
    // int handleTransferCCT(String userOpenId, String tokenId, BigDecimal amount);

    /**
     * 查询钱包
     *
     * @param walletType
     * @param userOpenId
     * @return
     */
    List<TronWallet> listWalletByWalletType(String walletType, String userOpenId);

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
     * @param tokenId
     * @param walletType
     * @return
     */
    TronWallet selectWallet(String userOpenId, String tokenId, String walletType);

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
    Integer initTronWallet(String userId);

    /**
     * 根据符号查询钱包
     *
     * @param userId
     * @param tokenSymbol
     * @param walletType
     * @return
     */
    TronWallet selectWalletByTokenSymbol(String userId, String tokenSymbol, String walletType);

    /**
     * 查询tron出块
     *
     * @param hashId
     * @return
     */
    Boolean getTransaction(String hashId);

    /**
     * 提现失败处理
     *
     * @param tx
     */
    void updateTxOutError(TronWalletTransfer tx);

    /**
     * 提现成功处理
     *
     * @param tx
     */
    void updateTxOutSuccess(TronWalletTransfer tx);


    /**
     * 根据用户ID,钱包类型，币种名称查询钱包信息
     *
     * @param userId
     * @param walletType
     * @param coinName
     * @return
     */
    TronWallet findWallet(String userId, String walletType, String coinName);

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
    TronWalletTransfer handleTransfer(String userOpenId, String fromType, String toType, String coinName, BigDecimal amount);

    /**
     * 保存账户信息
     *
     * @param address
     * @param hexAddress
     * @param tokenId
     * @param userId
     * @param tokenSymbol
     * @param tokenDecimal
     * @param balance
     * @param freeBalance
     * @param freezeBalance
     * @param appId
     * @return
     */
    Integer insert(String address, String hexAddress, String tokenId, String userId, String tokenSymbol, Integer tokenDecimal, BigDecimal balance, BigDecimal freeBalance, BigDecimal freezeBalance, String appId);

    /**
     * 通过base58地址查询16进制地址
     *
     * @param address
     * @return
     */
    String selectHexAddressByAddress(String address);

    /**
     * 通过16进制地址查询base58地址
     *
     * @param hexAddress
     * @return
     */
    String selectAddressByHexAddress(String hexAddress);

    /**
     * 根据用户ID，币种地址，钱包类型查询钱包信息
     *
     * @param openId
     * @param coinAddress
     * @param walletType
     * @return
     */
    TronWallet selectByUserOpenIdAndTokenAddrAndWalletType(String openId, String coinAddress, String walletType);

    /**
     * 支付接口
     *
     * @param walletPrepayment 预支付信息
     * @param addr             钱包地址
     * @return PayInsertOutDTO 返回数据参数
     */
    PayInsertOutDTO updatePayInRowLock(TronWalletPrepayment walletPrepayment, String addr);

    /**
     * 根据钱包地址，币种地址，钱包类型查询钱包信息
     *
     * @param addr       钱包地址
     * @param tokenAddr  币种地址
     * @param walletType 钱包类型
     * @return
     */
    TronWallet selectByAddrAndTokenAddrAndWalletType(String addr, String tokenAddr, String walletType);

    /**
     * 退款接口
     *
     * @param tronWalletTransfer 退款记录
     * @param amount             退款金额
     *                           返回数据参数
     */
    RefundOutDTO updateRefundInRowLock(TronWalletTransfer tronWalletTransfer, BigDecimal amount);

    /**
     * 根据条件查询钱包信息
     *
     * @param where
     * @return
     */
    List<TronWallet> selects(TronWallet where);

    /**
     * 发放奖金接口
     *
     * @param inParams
     * @return
     */
    RechargeOutDTO updateRechargeInRowLock(RechargeInDTO inParams, String addr);

    /**
     * 内部转账
     *
     * @param formUserOpenId
     * @param tel
     * @param amount
     * @param password
     * @return
     */
    Integer internalTransfer(String formUserOpenId, String tel, String amount, String password);

    /**
     * USDT划转
     *
     * @param tradeNo
     * @param amount
     * @param type
     * @param userOpenId
     * @param transferType
     * @return
     */
    boolean updateUsdtThransferInRowLock(String tradeNo, BigDecimal amount, String type, String userOpenId, String transferType);

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
