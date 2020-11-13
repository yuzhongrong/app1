package com.blockchain.server.teth.service;

import com.blockchain.server.teth.entity.EthBlockNumber;
import com.blockchain.server.teth.entity.EthToken;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 以太坊区块高度记录表——业务接口
 *
 * @author YH
 * @date 2019年2月16日17:09:19
 */
public interface IEthBlockNumberService {
    /**
     * 插入一个区块
     *
     * @param blockNumber 区块号
     * @param status      状态
     */
    void insert(BigInteger blockNumber, char status);

    /**
     * 插入一个区块的状态
     *
     * @param blockNumber 区块号
     * @param status      状态
     */
    void updateByBlockNumber(BigInteger blockNumber, char status);

    /**
     * 查询区块是否存在
     *
     * @param bigInteger 区块
     * @return true存在，false不存在
     */
    boolean isBlock(BigInteger bigInteger);

    /**
     * 查询区块信息
     *
     * @param blockNumber
     * @return
     */
    EthBlockNumber selectByBlockNumber(BigInteger blockNumber);

    /**
     * 查询最大的区块号
     *
     * @return
     */
    BigInteger selectCurrent();


    /**
     * 查询最新的区块号
     *
     * @return
     */
    BigInteger selectNewest();

    /**
     * 获取查询等待中的区块号
     *
     * @return
     */
    List<EthBlockNumber> selectWaitBlocks();

    /**
     * 插入该区块，所有的平台用户充值记录
     *
     * @param blockNumber 区块号
     */
    void handleBlockTxIn(BigInteger blockNumber, Map<String, EthToken> coinAddrs, Set<String> adds);


    /**
     * 删除区块，防止服务重启后可能会区块冲突
     * */
    void deleteBlock(BigInteger currentBlock);
}
