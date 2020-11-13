package com.blockchain.server.teth.mapper;

import com.blockchain.server.teth.entity.EthBlockNumber;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.math.BigInteger;

/**
 * EthBlockNumberMapper 数据访问类
 *
 * @version 1.0
 * @date 2019-02-16 15:44:06
 */
@Repository
public interface EthBlockNumberMapper extends Mapper<EthBlockNumber> {
    /**
     * 查询最大的区块号
     *
     * @return
     */
    BigInteger selectMaxBlockNumber();

    /**
     * 查询最小的区块号
     *
     * @return
     */
    BigInteger selectMinBlockNumber();
}