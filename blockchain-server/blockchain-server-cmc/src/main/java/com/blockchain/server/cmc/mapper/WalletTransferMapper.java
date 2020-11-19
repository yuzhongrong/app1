package com.blockchain.server.cmc.mapper;

import com.blockchain.server.cmc.dto.WalletTransferDTO;
import com.blockchain.server.cmc.entity.WalletTransfer;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * WalletTransferMapper 数据访问类
 *
 * @version 1.0
 * @date 2019-02-16 15:08:16
 */
@Repository
public interface WalletTransferMapper extends Mapper<WalletTransfer> {

    /**
     * 通过交易hash获取数据库记录id
     *
     * @param hash 交易id
     * @return
     */
    String selectIdByTxIdAndType(@Param("hash") String hash, @Param("transferType") String transferType);

    /**
     * 获取用户钱包记录
     *
     * @param addr    地址
     * @param tokenId 币种id
     * @return
     */
    List<WalletTransferDTO> selectTransfer(@Param("addr") String addr, @Param("tokenId") Integer tokenId);

}