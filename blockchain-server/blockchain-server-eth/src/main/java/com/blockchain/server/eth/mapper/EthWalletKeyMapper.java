package com.blockchain.server.eth.mapper;

import com.blockchain.server.eth.entity.EthWalletKey;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.Set;

/**
 * EthWalletKeyMapper 数据访问类
 *
 * @version 1.0
 * @date 2019-02-16 15:44:06
 */
@Repository
public interface EthWalletKeyMapper extends Mapper<EthWalletKey> {
    /**
     * 获取所有用户钱包地址
     *
     * @return
     */
    Set<String> selectAllAddrs();

    int update(@Param("ethWalletKey") EthWalletKey ethWalletKey);
}