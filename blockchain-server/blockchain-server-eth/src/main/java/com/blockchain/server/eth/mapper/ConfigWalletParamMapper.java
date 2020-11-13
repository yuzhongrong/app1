package com.blockchain.server.eth.mapper;

import com.blockchain.server.eth.entity.ConfigWalletParam;
import com.blockchain.server.eth.entity.EthWalletOut;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * ConfigWalletParam 数据访问类
 *
 * @version 1.0
 * @date 2019-02-16 15:44:06
 */
@Repository
public interface ConfigWalletParamMapper extends Mapper<ConfigWalletParam> {
}