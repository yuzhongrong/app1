package com.blockchain.server.cmc.service;

import com.blockchain.common.base.dto.WalletBalanceDTO;
import com.blockchain.server.cmc.dto.WalletDTO;
import com.blockchain.server.cmc.entity.Wallet;
import com.blockchain.server.cmc.entity.WalletTransfer;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

public interface WalletService {

    /**
     * 创建托管账户
     *
     * @param userOpenId 系统用户ID
     * @return
     */
    Integer insertWallet(String userOpenId);

    /**
     * 获取用户某应用钱包的所有区块
     *
     * @param userOpenId 用户id
     * @param walletType 应用类型，币币交易等
     * @return
     */
    List<WalletDTO> selectAllByUserOpenId(String userOpenId, String walletType);

    /**
     * 获取用户钱包
     *
     * @param userOpenId 用户id
     * @param tokenId    币种id
     * @param walletType 应用类型，币币交易等
     * @return
     */
    WalletDTO selectByUserOpenId(String userOpenId, Integer tokenId, String walletType);

    /**
     * 获取用户钱包，通过地址
     *
     * @param addr       用户id
     * @param tokenId    币种id
     * @param walletType 应用类型，币币交易等
     * @return
     */
    WalletDTO selectByAddr(String addr, Integer tokenId, String walletType);

    /**
     * 加减钱包地址可用余额、冻结余额、总额
     *
     * @param address      地址
     * @param tokenId      币种id
     * @param freeAmount   可用余额加减数量
     * @param freezeAmount 冻结余额加减数量
     * @param totalAmount  总额加减数量
     * @param modifyTime   修改时间
     * @return
     */
    Integer updateBalanceByAddrInRowLock(String address, Integer tokenId, BigDecimal freeAmount, BigDecimal freezeAmount, BigDecimal totalAmount, Date modifyTime);

    /**
     * 获取所有用户托管钱包地址
     *
     * @return
     */
    Set<String> getAllWalletAddr();

    /**
     * 应用提现失败业务
     *
     * @param walletTransfer 提现记录
     */
    void updateTxOutError(WalletTransfer walletTransfer);

    /**
     * 应用提现成功业务
     *
     * @param walletTransfer 提现记录
     */
    void updateTxOutSuccess(WalletTransfer walletTransfer);

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
     * @param userId   用户ID
     * @param fromType 钱包类型
     * @param toType   钱包类型
     * @param coinName 币种名称
     * @param amount   币种金额
     * @return
     */
    WalletTransfer handleTransfer(String userId, String fromType, String toType, String coinName, BigDecimal amount);

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
