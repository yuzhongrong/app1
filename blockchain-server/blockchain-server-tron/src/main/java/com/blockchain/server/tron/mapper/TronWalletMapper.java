package com.blockchain.server.tron.mapper;

import com.blockchain.common.base.dto.WalletBalanceBatchDTO;
import com.blockchain.common.base.dto.WalletBalanceDTO;
import com.blockchain.server.tron.entity.TronWallet;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author Harvey
 * @date 2019/2/18 16:50
 * @user WIN10
 */
@Repository
public interface TronWalletMapper extends Mapper<TronWallet> {

    /**
     * 根据地址修改用户钱包
     *
     * @param balance
     * @param addr
     */
    Integer updateWalletBalanceByAddrInRowLock(@Param("balance") BigDecimal balance, @Param("addr") String addr, @Param("tokenAddr") String tokenAddr, @Param("modifyTime") Date modifyTime);

    /**
     * 根据用户id查询用户钱包
     *
     * @param walletType
     * @param userOpenId
     * @return
     */
    List<TronWallet> listWalletByWalletType(@Param("walletType") String walletType, @Param("userOpenId") String userOpenId);

    /**
     * 通过用户id和币种名称和钱包标识查找用户
     *
     * @param userOpenId
     * @param tokenAddr
     * @param walletType
     * @return
     */
    TronWallet selectWallet(@Param("userOpenId") String userOpenId, @Param("tokenAddr") String tokenAddr, @Param("walletType") String walletType);

    /**
     * 修改用户钱包
     *
     * @param balance
     * @param freeBalance
     * @param freezeBalance
     * @param userOpenId
     * @param tokenAddr
     * @param walletType
     * @return
     */
    Integer updateWalletAllBalanceInRowLock(@Param("balance") BigDecimal balance,
                                            @Param("freeBalance") BigDecimal freeBalance,
                                            @Param("freezeBalance") BigDecimal freezeBalance,
                                            @Param("userOpenId") String userOpenId,
                                            @Param("tokenAddr") String tokenAddr,
                                            @Param("walletType") String walletType,
                                            @Param("modifyTime") Date modifyTime);

    /**
     * 通过用户id统计用户钱包
     *
     * @param userId
     * @return
     */
    Integer selectCountWalletByUserOpenId(@Param("userId") String userId);

    /**
     * 根据符号查询钱包
     *
     * @param userId
     * @param tokenSymbol
     * @param walletType
     * @return
     */
    TronWallet selectWalletByTokenSymbol(@Param("userId") String userId, @Param("tokenSymbol") String tokenSymbol, @Param("walletType") String walletType);

    /**
     * 通过base58地址查询16进制地址
     *
     * @param addr
     * @return
     */
    String selectHexAddrByAddr(@Param("addr") String addr);

    /**
     * 通过16进制地址查询base58地址
     *
     * @param hexAddr
     * @return
     */
    String selectAddrByHexAddr(@Param("hexAddr") String hexAddr);

    /**
     * 获取用户钱包
     *
     * @param userOpenId 用户id
     * @param tokenAddr  币种id
     * @param walletType 应用类型，币币交易等
     * @return
     */
    TronWallet selectByUserOpenId(@Param("userOpenId") String userOpenId, @Param("tokenAddr") String tokenAddr, @Param("walletType") String walletType);

    /**
     * 获取用户钱包，通过地址
     *
     * @param addr       用户id
     * @param tokenAddr  币种id
     * @param walletType 应用类型，币币交易等
     * @return
     */
    TronWallet selectByAddr(@Param("addr") String addr, @Param("tokenAddr") String tokenAddr, @Param("walletType") String walletType);

    /**
     * 获取用户钱包，通过hex地址
     *
     * @param addr
     * @param tokenAddr
     * @param walletType
     * @return
     */
    TronWallet selectByHexAddr(@Param("addr") String addr, @Param("tokenAddr") String tokenAddr, @Param("walletType") String walletType);

    /**
     * 加减钱包地址可用余额、冻结余额、总额
     *
     * @param addr         地址
     * @param tokenAddr    币种id
     * @param freeAmount   可用余额加减数量
     * @param freezeAmount 冻结余额加减数量
     * @param totalAmount  总额加减数量
     * @return
     */
    Integer updateBalanceByAddrInRowLock(@Param("addr") String addr, @Param("tokenAddr") String tokenAddr, @Param("freeAmount") Double freeAmount,
                                         @Param("freezeAmount") Double freezeAmount, @Param("totalAmount") Double totalAmount, @Param("modifyTime") Date modifyTime);

    /**
     * 获取所有用户托管钱包地址
     *
     * @return
     */
    Set<String> getAllWalletAddr();

    /**
     * 获取用户某应用钱包的所有区块
     *
     * @param userOpenId 用户id
     * @param walletType 应用类型，币币交易等
     * @return
     */
    List<TronWallet> selectAllByUserOpenId(@Param("userOpenId") String userOpenId, @Param("walletType") String walletType);

    /**
     * 修改用户指定钱包的金额
     *
     * @param addr          钱包地址
     * @param tokenAddr     币种地址
     * @param walletType    应用钱包
     * @param balance       总金额
     * @param freeBalance   可用金额
     * @param freezeBalance 冻结金额
     * @return
     */
    int updateBalanceAllBalanceByAddrInRowLock(@Param("addr") String addr,
                                               @Param("tokenAddr") String tokenAddr,
                                               @Param("walletType") String walletType,
                                               @Param("balance") BigDecimal balance,
                                               @Param("freeBalance") BigDecimal freeBalance,
                                               @Param("freezeBalance") BigDecimal freezeBalance);

    /**
     * 获取钱包余额，通过用户id，与钱包类型
     *
     * @param userOpenId  用户id
     * @param walletTypes 钱包类型
     * @return 币种对应的可用、冻结余额
     */
    List<WalletBalanceDTO> getBalanceByIdAndTypes(@Param("userOpenId") String userOpenId, @Param("walletTypes") String[] walletTypes);

    List<WalletBalanceBatchDTO> getBalanceByIdAndTypesBatch(@Param("walletTypes") String[] walletTypes);
}
