package com.blockchain.server.cmc.mapper;

import com.blockchain.server.cmc.dto.BtcWalletTransferDTO;
import com.blockchain.server.cmc.entity.BtcWalletTransfer;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * BtcWalletTransferMapper 数据访问类
 *
 * @version 1.0
 * @date 2019-02-16 15:08:16
 */
@Repository
public interface BtcWalletTransferMapper extends Mapper<BtcWalletTransfer> {

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
    List<BtcWalletTransferDTO> selectTransfer(@Param("addr") String addr, @Param("tokenId") Integer tokenId);

}