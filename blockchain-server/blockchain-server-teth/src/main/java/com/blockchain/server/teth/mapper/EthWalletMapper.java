package com.blockchain.server.teth.mapper;

import com.blockchain.common.base.dto.WalletBalanceBatchDTO;
import com.blockchain.common.base.dto.WalletBalanceDTO;
import com.blockchain.server.teth.dto.EthWalletDTO;
import com.blockchain.server.teth.entity.EthWallet;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * EthWalletMapper 数据访问类
 *
 * @version 1.0
 * @date 2019-02-16 15:44:06
 */
@Repository
public interface EthWalletMapper extends Mapper<EthWallet> {

    /**
     * 根据钱包地址、币种地址、钱包类型查询钱包信息
     *
     * @param addr      钱包地址
     * @param tokenAddr 币种地址
     * @return
     */
    EthWalletDTO selectByAddrAndTokenAddr(@Param("addr") String addr,
                                          @Param("tokenAddr") String tokenAddr);

    /**
     * 根据钱包地址、币种地址、钱包类型查询钱包信息
     *
     * @param addr       钱包地址
     * @param tokenAddr  币种地址
     * @param walletType 钱包类型
     * @return
     */
    EthWalletDTO selectByAddrAndTokenAddrAndWalletType(@Param("addr") String addr,
                                                       @Param("tokenAddr") String tokenAddr,
                                                       @Param("walletType") String walletType);

    /**
     * 根据用户ID、币种地址、钱包类型查询钱包信息
     *
     * @param userOpenId 用户ID
     * @param tokenAddr  币种地址
     * @param walletType 钱包类型
     * @return
     */
    EthWalletDTO selectByUserOpenIdAndTokenAddrAndWalletType(@Param("userOpenId") String userOpenId,
                                                             @Param("tokenAddr") String tokenAddr,
                                                             @Param("walletType") String walletType);

    /**
     * 根据钱包地址、钱包类型查询钱包信息
     *
     * @param addr       钱包地址
     * @param walletType 钱包类型
     * @return
     */
    List<EthWalletDTO> selectByAddrAndWalletType(@Param("addr") String addr,
                                                 @Param("walletType") String walletType);

    /**
     * 根据用户ID、钱包类型查询钱包信息
     *
     * @param userOpenId 用户ID
     * @param walletType 钱包类型
     * @return
     */
    List<EthWalletDTO> selectByUserOpenIdAndWalletType(@Param("userOpenId") String userOpenId,
                                                       @Param("walletType") String walletType);


    /**
     * 修改用户指定钱包的金额
     *
     * @param userOpenId    用户标识
     * @param tokenAddr     币种地址
     * @param walletType    应用钱包
     * @param balance       总金额
     * @param freeBalance   可用金额
     * @param freezeBalance 冻结金额
     * @return
     */
    int updateBalanceByUserIdInRowLock(@Param("userOpenId") String userOpenId,
                                       @Param("tokenAddr") String tokenAddr,
                                       @Param("walletType") String walletType,
                                       @Param("balance") BigDecimal balance,
                                       @Param("freeBalance") BigDecimal freeBalance,
                                       @Param("freezeBalance") BigDecimal freezeBalance,
                                       @Param("modifyTime") Date updateTime);

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
    int updateBalanceByAddrInRowLock(@Param("addr") String addr,
                                     @Param("tokenAddr") String tokenAddr,
                                     @Param("walletType") String walletType,
                                     @Param("balance") BigDecimal balance,
                                     @Param("freeBalance") BigDecimal freeBalance,
                                     @Param("freezeBalance") BigDecimal freezeBalance,
                                     @Param("modifyTime") Date updateTime);

    /**
     * 获取钱包余额，通过用户id，与钱包类型
     *
     * @param userOpenId  用户id
     * @param walletTypes 钱包类型
     * @return 币种对应的可用、冻结余额
     */
    List<WalletBalanceDTO> getBalanceByIdAndTypes(@Param("userOpenId") String userOpenId, @Param("walletTypes") String[] walletTypes);

    /**
     * 获取用户某个币种所有金额
     */
    BigDecimal getAllBalanceByToken(@Param("userOpenId") String userOpenId, @Param("coinName") String coinName);

    List<WalletBalanceBatchDTO> getBalanceByIdAndTypesBatch(@Param("walletTypes") String[] walletTypes);
}