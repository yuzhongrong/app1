package com.blockchain.server.tron.service;

import com.alibaba.fastjson.JSONObject;

import java.math.BigInteger;

/**
 * @author Harvey Luo
 * @date 2019/6/10 15:23
 */
public interface TronWalletUtilService {

    /**
     * 查询tron出币
     *
     * @param hashId
     * @return
     */
    JSONObject getTransaction(String hashId);

    /**
     * 根据base58获取hex16地址
     *
     * @param address
     * @return
     */
    JSONObject getHexAccount(String address);

    /**
     * 查询TRC20代币方法
     *
     * @param contractAddress
     * @param timestamp
     * @return
     */
    JSONObject getTRC20Transaction(String contractAddress, Long timestamp);

    /**
     * 生成地址
     *
     * @return
     */
    String createAccountAddress();

    /**
     * 创建账号交易
     * @param owner
     * @param account
     * @return
     */
    String createAccount(String owner, String account);


    /**
     * 签名
     * @param signParam
     * @return
     */
    String getSign(String signParam, String privateKey) throws Exception;

    /**
     * 广播交易
     * @param broadcastParam
     */
    String getBroadcast(String broadcastParam);

    /**
     * 获取最新区块信息
     * @return
     */
    JSONObject getNewBlock();

    /**
     * 获取区块之间信息
     * @param startNum
     * @param endNum
     * @return
     */
    JSONObject getBlockLimitNext(BigInteger startNum, BigInteger endNum);

    /**
     * 通过区块高度查询区块信息
     * @param num
     * @return
     */
    JSONObject getBlockByNum(BigInteger num);
}
