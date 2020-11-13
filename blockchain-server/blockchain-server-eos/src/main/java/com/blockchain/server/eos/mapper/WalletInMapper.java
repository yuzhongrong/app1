package com.blockchain.server.eos.mapper;

import com.blockchain.server.eos.dto.WalletInDTO;
import com.blockchain.server.eos.entity.WalletIn;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.math.BigInteger;
import java.util.List;

/**
 * @author Harvey
 * @date 2019/2/18 16:50
 * @user WIN10
 */
@Repository
public interface WalletInMapper extends Mapper<WalletIn> {


    // WalletInDTO selectWalletInAccount(String tokenName, Integer eosWalletInUsable);

    /**
     * 查询对应的代币名称充值账户
     * @param tokenName
     * @param status
     * @return
     */
    WalletInDTO selectWalletInAccount(@Param("tokenName") String tokenName, @Param("status") Integer status);

    List<WalletInDTO> listWalletInByAccountName(@Param("to") String to);

    /**
     * 修改区块字段
     * @param accountName
     * @param blockNum
     * @return
     */
    Integer updateWalletInBlockNumber(@Param("accountName") String accountName, @Param("blockNum") BigInteger blockNum);

}
