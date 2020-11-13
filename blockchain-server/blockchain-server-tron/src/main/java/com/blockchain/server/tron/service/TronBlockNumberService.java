package com.blockchain.server.tron.service;

import com.alibaba.fastjson.JSONObject;
import com.blockchain.server.tron.dto.TronTokenDTO;
import com.blockchain.server.tron.entity.TronToken;

import java.math.BigInteger;
import java.util.List;
import java.util.Set;

/**
 * @author Harvey Luo
 * @date 2019/6/21 15:02
 */
public interface TronBlockNumberService {

    /**
     * 获取最新区块
     */
    JSONObject getBlockLimitNext();

    /**
     * 保存数据
     * @param blockNumber
     * @param blockNumberType
     * @return
     */
    Integer insert(BigInteger blockNumber, Character blockNumberType);

    /**
     * 处理爬取区间区块信息
     * @param blockInformation
     * @param listToken
     */
    void handleBlockTransactions(JSONObject blockInformation, List<TronToken> listToken, Set<String> addrs);

    /**
     * 保存区块数据
     * @param blockNumber
     * @param newBlockNumber
     * @param status
     * @return
     */
    Integer insertBetweenBlockNumber(BigInteger blockNumber, BigInteger newBlockNumber, Character status);

    /**
     * 根据区块高度修改区块爬取状态
     * @param blockNumber
     * @param blockNumberType
     * @return
     */
    Integer updateStatusByBlockNumber(BigInteger blockNumber, Character blockNumberType);

    /**
     * 查询区块高度为等待的区块
     * @param status
     * @return
     */
    List<BigInteger> listBlockNumberOmit(Character status);

    /**
     * 处理遗漏区块信息
     * @param blockNumbers
     * @param listToken
     */
    void handleBlockTransactionsByOmit(List<BigInteger> blockNumbers, List<TronToken> listToken, Set<String> addrs);

    /**
     * TRC20代币充值处理方法
     * @param tronTokenDTO
     */
    void handleBlockTransactionsByTRC20(TronTokenDTO tronTokenDTO, Set<String> addrs);
}
