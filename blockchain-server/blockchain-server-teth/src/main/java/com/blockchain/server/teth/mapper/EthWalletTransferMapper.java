package com.blockchain.server.teth.mapper;

import com.blockchain.server.teth.entity.EthWalletTransfer;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * EthWalletTransferMapper 数据访问类
 *
 * @version 1.0
 * @date 2019-02-16 15:44:06
 */
@Repository
public interface EthWalletTransferMapper extends Mapper<EthWalletTransfer> {

    /**
     * 查询钱包记录信息
     *
     * @param addr
     * @param tokenAddr
     * @return
     */
    List<EthWalletTransfer> selectTransfer(@Param("addr") String addr,
                                           @Param("tokenAddr") String tokenAddr);

}