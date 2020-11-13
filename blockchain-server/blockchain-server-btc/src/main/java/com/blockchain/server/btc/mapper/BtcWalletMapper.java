package com.blockchain.server.btc.mapper;

import com.blockchain.common.base.dto.WalletBalanceBatchDTO;
import com.blockchain.common.base.dto.WalletBalanceDTO;
import com.blockchain.server.btc.dto.BtcWalletDTO;
import com.blockchain.server.btc.entity.BtcWallet;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * BtcWalletMapper 数据访问类
 *
 * @version 1.0
 * @date 2019-02-16 15:08:16
 */
@Repository
public interface BtcWalletMapper extends Mapper<BtcWallet> {

    /**
     * 获取用户钱包
     *
     * @param userOpenId 用户id
     * @param tokenId    币种id
     * @param walletType 应用类型，币币交易等
     * @return
     */
    BtcWalletDTO selectByUserOpenId(@Param("userOpenId") String userOpenId, @Param("tokenId") Integer tokenId, @Param("walletType") String walletType);

    /**
     * 获取用户钱包，通过地址
     *
     * @param addr       用户id
     * @param tokenId    币种id
     * @param walletType 应用类型，币币交易等
     * @return
     */
    BtcWalletDTO selectByAddr(@Param("addr") String addr, @Param("tokenId") Integer tokenId, @Param("walletType") String walletType);

    /**
     * 加减钱包地址可用余额、冻结余额、总额
     *
     * @param address      地址
     * @param tokenId      币种id
     * @param freeAmount   可用余额加减数量
     * @param freezeAmount 冻结余额加减数量
     * @param totalAmount  总额加减数量
     * @return
     */
    Integer updateBalanceByAddrInRowLock(@Param("address") String address, @Param("tokenId") Integer tokenId, @Param("freeAmount") BigDecimal freeAmount,
                                         @Param("freezeAmount") BigDecimal freezeAmount, @Param("totalAmount") BigDecimal totalAmount, @Param("modifyTime") Date modifyTime);

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
    List<BtcWalletDTO> selectAllByUserOpenId(@Param("userOpenId") String userOpenId, @Param("walletType") String walletType);

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