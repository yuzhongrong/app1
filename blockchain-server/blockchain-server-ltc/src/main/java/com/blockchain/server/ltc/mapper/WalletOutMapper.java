package com.blockchain.server.ltc.mapper;

import com.blockchain.server.ltc.entity.WalletOut;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * WalletOutMapper 数据访问类
 * @date 2019-02-16 15:08:16
 * @version 1.0
 */
@Repository
public interface WalletOutMapper extends Mapper<WalletOut> {
}