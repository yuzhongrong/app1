package com.blockchain.server.tron.mapper;

import com.blockchain.server.tron.entity.TronBlockNumber;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

/**
 * @author Harvey Luo
 * @date 2019/6/21 15:06
 */
@Repository
public interface TronBlockNumberMapper extends Mapper<TronBlockNumber> {

    /**
     * 查询最大爬取的区块
     * @return
     */
    BigInteger selectCurrentBlockNumber();

    /**
     * 根据区块高度修改区块爬取状态
     * @param blockNumber
     * @param status
     * @param date
     * @return
     */
    Integer updateStatusByBlockNumber(@Param("blockNumber") BigInteger blockNumber, @Param("status") Character status, @Param("date") Date date);

    /**
     * 查询区块高度为等待的区块
     * @param status
     * @param date
     * @return
     */
    List<BigInteger> listBlockNumberOmit(@Param("status") Character status, @Param("date") Date date);
}
