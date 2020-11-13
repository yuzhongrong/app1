package com.blockchain.server.tron.mapper;

import com.blockchain.server.tron.dto.RefundOutDTO;
import com.blockchain.server.tron.dto.TronWalletTransferDto;
import com.blockchain.server.tron.entity.TronWalletTransfer;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author Harvey
 * @date 2019/2/18 11:13
 * @user WIN10
 */
@Repository
public interface TronWalletTransferMapper extends Mapper<TronWalletTransfer> {

    /**
     * 查询钱包交易记录
     * @param walletHexAddr
     * @param tokenAddr
     * @return
     */
    List<TronWalletTransferDto> listWalletTransfer(@Param("walletHexAddr") String walletHexAddr, @Param("tokenAddr") String tokenAddr);

    /**
     * 根据hash查询是否存在数据
     * @param hash
     * @param transferType
     * @return
     */
    Integer countByHashAndTransferType(@Param("hash") String hash, @Param("transferType") String transferType);

    /**
     * 查询记录是否存在
     *
     * @param hash 预支付ID(存入HASH字段)
     * @param hash 预支付ID(存入HASH字段)
     * @return
     */
    Integer findByHashAndTypeTheLime(@Param("hash") String hash, @Param("type") String type);

    /**
     * 查询记录
     *
     * @param fromAddr 支付地址
     * @param toAddr   收款地址
     * @param type     类型
     * @return
     */
    List<RefundOutDTO> selectByAddrAndType(@Param("fromAddr") String fromAddr,
                                           @Param("toAddr") String toAddr,
                                           @Param("type") String type);
}
