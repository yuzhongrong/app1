package com.blockchain.server.eos.mapper;

import com.blockchain.server.eos.dto.WalletTransferDTO;
import com.blockchain.server.eos.entity.WalletTransfer;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.HashSet;
import java.util.List;

/**
 * @author Harvey
 * @date 2019/2/18 11:13
 * @user WIN10
 */
@Repository
public interface WalletTransferMapper extends Mapper<WalletTransfer> {

    /**
     * 查询钱包交易记录
     * @param walletId
     * @param tokenName
     * @return
     */
    List<WalletTransferDTO> listWalletTransfer(@Param("walletId") Integer walletId, @Param("tokenName") String tokenName);

    /**
     * 根据hash查询是否存在数据
     * @param hash
     * @param transferType
     * @return
     */
    Integer countByHashAndTransferType(@Param("hash") String hash, @Param("transferType") String transferType);
}
