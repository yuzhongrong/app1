package com.blockchain.server.eos.service;

import com.alibaba.fastjson.JSONObject;
import com.blockchain.server.eos.dto.BlockNumberDTO;

import java.math.BigInteger;

/**
 * @author Harvey
 * @date 2019/2/18 13:57
 * @user WIN10
 */
public interface EosWalletUtilService {

    /**
     * 获取最新区块
     * @return
     */
    BigInteger getEosBlock();

    /**
     * 获取区块交易记录
     * @param nowBlock
     * @return
     */
    JSONObject listBlockTransaction(BigInteger nowBlock);

    /**
     * 获取区块高度
     * @return
     */
    BlockNumberDTO getBlockNum();

    /**
     * 获取出币信息
     * @param blockNum
     * @param hashId
     * @return
     */
    JSONObject getTransaction(String blockNum, String hashId);

    /**
     * 获取账户历史交易信息
     * @param accountName
     * @return
     */
    JSONObject getActions(String accountName, String offset);
}
