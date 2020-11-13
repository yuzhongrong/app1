package com.blockchain.server.eos.mapper;

import com.blockchain.server.eos.dto.BlockNumberDTO;
import com.blockchain.server.eos.entity.BlockNumber;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.math.BigInteger;
import java.util.List;

/**
 * BlockNumberMapper 数据访问类
 * @date 2018-11-05 15:10:47
 * @version 1.0
 */
@Repository
public interface BlockNumberMapper extends Mapper<BlockNumber> {

    /**
     * 查询最旧区块
     * @return
     */
    BigInteger selectMinBlockNum();
    /**
     * 查询最大的区块号
     * @return
     */
    BigInteger selectMaxBlockNum();

    /**
     * 查询是否存在区块
     * @param blockNum
     * @return
     */
    BlockNumberDTO selectBlockNumIsExist(@Param("blockNum") BigInteger blockNum);

    /**
     * 查找当前区块状态
     * @param blockNum
     * @return
     */
    Character selectBlockNumStatusByBlockNum(@Param("blockNum") BigInteger blockNum);

    /**
     * 根据区块状态获取区块
     * @param status
     * @return
     */
    List<BigInteger> listBlockNumberByStatus(@Param("status") Character status);

    /**
     * 更新处理区块状态
     * @param blockNum
     * @param status
     * @return
     */
    int updateBlockNumberByStatus(@Param("blockNum") BigInteger blockNum, @Param("status") Character status);

    /**
     *
     * @param blockNumber
     * @return
     */
    int insertSelectiveIgnore(BlockNumber blockNumber);
}