package com.blockchain.server.eth.mapper;

import com.blockchain.server.eth.entity.EthToken;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.Set;

/**
 * EthTokenMapper 数据访问类
 * @date 2019-02-16 15:44:06
 * @version 1.0
 */
@Repository
public interface EthTokenMapper extends Mapper<EthToken> {
    /**
     * 获取所有的币种地址
     *
     * @return
     */
    Set<String> selectAllTokenAddr();
}