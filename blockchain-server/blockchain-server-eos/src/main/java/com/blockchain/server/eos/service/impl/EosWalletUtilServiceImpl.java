package com.blockchain.server.eos.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.blockchain.server.eos.dto.BlockNumberDTO;
import com.blockchain.server.eos.eos4j.Rpc;
import com.blockchain.server.eos.eos4j.api.vo.ChainInfo;
import com.blockchain.server.eos.service.EosBlockNumberService;
import com.blockchain.server.eos.service.EosWalletUtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

/**
 * @author Harvey
 * @date 2019/2/18 13:57
 * @user WIN10
 */
@Service
public class EosWalletUtilServiceImpl implements EosWalletUtilService {

    @Autowired
    private EosBlockNumberService eosBlockNumberService;


    private Rpc eosRpc = null;

    @Value("${rpc_url}")
    private String RPC_URL;
    @Value("${get_block_url}")
    private String GET_BLOCK_URL;
    @Value("${get_transaction}")
    private String GET_TRANSACTION_URL;
    @Value("${get_actions}")
    private String GET_ACTIONS;


    @Override
    public BigInteger getEosBlock() {
        eosRpc = new Rpc(RPC_URL);
        ChainInfo chainInfo = eosRpc.getChainInfo();
        String headBlockNum = chainInfo.getHeadBlockNum();
        return new BigInteger(headBlockNum);
    }

    /**
     * 获取区块交易信息
     *
     * @param nowBlock
     * @return
     */
    @Override
    public JSONObject listBlockTransaction(BigInteger nowBlock) {
        eosRpc = new Rpc(RPC_URL);
        String url = RPC_URL + GET_BLOCK_URL;
        ResponseEntity<JSONObject> body = eosRpc.get_block(url, nowBlock);
        if (body != null) return body.getBody();
        return null;
    }

    @Override
    public BlockNumberDTO getBlockNum() {

        BigInteger newstBlock = this.getEosBlock();  //最新区块
        // 从redis获取区块信息
        // 当前区块高度
        BigInteger current = eosBlockNumberService.selectCurrentBlockNum();
        if (current == null) current = newstBlock;
        // 判断当前区块是否大于最新区块
        if (current.compareTo(newstBlock) > 0) newstBlock = this.getEosBlock();  //最新区块
        BlockNumberDTO blockNumberDTO = new BlockNumberDTO();
        blockNumberDTO.setCurrentBlock(current);
        blockNumberDTO.setNewBlock(newstBlock);
        return blockNumberDTO;
    }

    /**
     * 获取出币信息
     *
     * @param blockNum
     * @param hashId
     * @return
     */
    @Override
    public JSONObject getTransaction(String blockNum, String hashId) {
        eosRpc = new Rpc(RPC_URL);
        String url = RPC_URL + GET_TRANSACTION_URL;
        String body = eosRpc.getTransaction(url, blockNum, hashId);
        if (body != null) return JSONObject.parseObject(body);
        return null;
    }

    /**
     * 获取账户历史交易信息
     *
     * @param accountName
     * @return
     */
    @Override
    public JSONObject getActions(String accountName, String offset) {
        eosRpc = new Rpc(RPC_URL);
        String url = RPC_URL + GET_ACTIONS;
        ResponseEntity<String> body = eosRpc.getActionsTest(url, accountName, offset);
        if (body != null) return JSONObject.parseObject(body.getBody());
        return null;
    }
}
