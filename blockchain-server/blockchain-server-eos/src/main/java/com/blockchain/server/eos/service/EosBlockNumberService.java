package com.blockchain.server.eos.service;

import com.blockchain.server.eos.dto.BlockNumberDTO;
import com.blockchain.server.eos.entity.BlockNumber;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.List;

/**
 * @author Harvey
 * @date 2019/2/16 17:45
 * @user WIN10
 */
public interface EosBlockNumberService {

    /**
     * 查询最大区块
     * @return
     */
    BigInteger selectCurrentBlockNum();

    /**
     * 添加区块交易信息
     * @param nowBlock
     * @param listTokenAddr
     */
    void handleBlockTransactions(BigInteger nowBlock, HashSet<String> listTokenAddr);

    /**
     * 添加
     * @param blockNumber
     * @return
     */
    int insertBlockNumber(BlockNumber blockNumber);

    /**
     * 插入区块处理状态
     * @param nowBlock
     * @param status
     * @return
     */
    int insertBlockNumberByStatus(BigInteger nowBlock, Character status);

    /**
     * 根据区块状态获取区块
     * @param eosBlockNumberWait
     * @return
     */
    List<BigInteger> listBlockNumberByStatus(Character eosBlockNumberWait);

    /**
     * 更新处理区块状态
     * @param blockNum
     * @param status
     * @return
     */
    int updateBlockNumberByStatus(BigInteger blockNum, Character status);
}
