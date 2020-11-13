package com.blockchain.server.eth.mapper;

import com.blockchain.server.eth.entity.EthWalletOut;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * EthWalletOutMapper 数据访问类
 * @date 2019-02-16 15:44:06
 * @version 1.0
 */
@Repository
public interface EthWalletOutMapper extends Mapper<EthWalletOut> {
}