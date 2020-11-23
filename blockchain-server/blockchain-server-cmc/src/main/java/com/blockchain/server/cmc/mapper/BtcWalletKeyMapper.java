package com.blockchain.server.cmc.mapper;

import com.blockchain.server.cmc.entity.BtcWalletKey;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * BtcWalletKeyMapper 数据访问类
 * @date 2019-02-16 15:08:16
 * @version 1.0
 */
@Repository
public interface BtcWalletKeyMapper extends Mapper<BtcWalletKey> {
}