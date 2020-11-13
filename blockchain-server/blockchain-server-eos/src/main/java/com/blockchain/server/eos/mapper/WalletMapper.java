package com.blockchain.server.eos.mapper;

import com.blockchain.common.base.dto.WalletBalanceBatchDTO;
import com.blockchain.common.base.dto.WalletBalanceDTO;
import com.blockchain.server.eos.dto.WalletDTO;
import com.blockchain.server.eos.entity.Wallet;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author Harvey
 * @date 2019/2/18 16:50
 * @user WIN10
 */
@Repository
public interface WalletMapper extends Mapper<Wallet> {

    /**
     * 用户充值修改钱包金额
     *
     * @param balance
     * @param id
     */
    int updateWalletBalanceByIdInRowLock(@Param("balance") BigDecimal balance, @Param("id") Integer id, @Param("modifyTime") Date modifyTime);

    /**
     * 根据用户id查询用户钱包
     *
     * @param walletType
     * @param userOpenId
     * @return
     */
    List<WalletDTO> listWalletByWalletType(@Param("walletType") String walletType, @Param("userOpenId") String userOpenId);

    /**
     * 通过用户id和币种名称和钱包标识查找用户
     *
     * @param userOpenId
     * @param tokenName
     * @param walletType
     * @return
     */
    WalletDTO selectWallet(@Param("userOpenId") String userOpenId, @Param("tokenName") String tokenName, @Param("walletType") String walletType);

    /**
     * 修改用户钱包
     *
     * @param balance
     * @param freeBalance
     * @param freezeBalance
     * @param userOpenId
     * @param tokenName
     * @param walletType
     * @return
     */
    Integer updateWalletAllBalanceInRowLock(@Param("balance") BigDecimal balance,
                                            @Param("freeBalance") BigDecimal freeBalance,
                                            @Param("freezeBalance") BigDecimal freezeBalance,
                                            @Param("userOpenId") String userOpenId,
                                            @Param("tokenName") String tokenName,
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
    WalletDTO selectWalletByTokenSymbol(@Param("userId") String userId, @Param("tokenSymbol") String tokenSymbol, @Param("walletType") String walletType);

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
