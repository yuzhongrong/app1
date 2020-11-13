package com.blockchain.server.tron.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.blockchain.common.base.util.JsonUtils;
import com.blockchain.server.tron.common.constant.TronConstant;
import com.blockchain.server.tron.service.TronWalletUtilService;
import com.blockchain.server.tron.tron.Rpc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.tron.core.capsule.TransactionCapsule;
import org.tron.core.services.http.JsonFormat;
import org.tron.core.services.http.Util;
import org.tron.protos.Protocol;

import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Harvey Luo
 * @date 2019/6/10 15:23
 */
@Service
public class TronWalletUtilServiceImpl implements TronWalletUtilService {

    private Rpc tronRpc = new Rpc();

    // 主网地址
    @Value("${rpc_url}")
    private String RPC_URL;
    // 版本
    @Value("${version}")
    private String VERSION;
    // 账号
    @Value("${accounts}")
    private String ACCOUNTS;
    // 合同
    @Value("${contracts}")
    private String CONTRACTS;
    // 事件
    @Value("${events}")
    private String EVENTS;
    // 交易后缀
    @Value("${transactions}")
    private String TRANSACTIONS;
    // 钱包
    @Value("${wallet}")
    private String WALLET;
    // 签名后缀
    @Value("${sign}")
    private String SIGN;
    // 广播交易后缀
    @Value("${broadcast}")
    private String BROADCAST;
    // 查询交易后缀
    @Value("${get_transaction}")
    private String GET_TRANSACTION;
    // 创建地址后缀
    @Value("${create_account_address}")
    private String CREATE_ACCOUNT_ADDRESS;
    // 创建账号后缀
    @Value("${create_account}")
    private String CREATE_ACCOUNT;
    // 获取最新区块后缀
    @Value("${get_now_block}")
    private String GET_NOW_BLOCK;
    // 根据区块高度查询区块信息后缀
    @Value("${get_block_by_num}")
    private String GET_BLOCK_BY_NUM;
    // 查询两区快之间信息
    @Value("${get_block_by_limit_next}")
    private String GET_BLOCK_BY_LIMIT_NEXT;

    /**
     * 查询tron出币
     *
     * @param hashId
     * @return
     */
    @Override
    public JSONObject getTransaction(String hashId) {
        String url = RPC_URL + WALLET + GET_TRANSACTION;
        Map<String, String> map = new HashMap<>();
        map.put("value", hashId);
        String param = JsonUtils.objectToJson(map);
        ResponseEntity<String> stringResponseEntity = tronRpc.postResponseString(url, param);
        try {
            if (stringResponseEntity != null) return JSONObject.parseObject(stringResponseEntity.getBody());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    /**
     * 根据base58获取hex16地址
     *
     * @param address
     * @return
     */
    @Override
    public JSONObject getHexAccount(String address) {
        String url = RPC_URL + VERSION + ACCOUNTS + address;
        ResponseEntity<JSONObject> responseEntity = tronRpc.get(url);
        if (responseEntity != null) return responseEntity.getBody();
        return null;
    }

    /**
     * 解析TRC20合约地址信息
     *
     * @param contractAddress
     * @param timestamp
     * @return
     */
    public JSONObject getTRC20Transaction(String contractAddress, Long timestamp) {
        String url = RPC_URL + VERSION + CONTRACTS + contractAddress + EVENTS + TronConstant.RPCConstant.RPC_EVENT_NAME + TronConstant.RPCConstant.RPC_MIN_BLOCK_TIMESTAMP + timestamp + TronConstant.RPCConstant.RPC_MAX_BLOCK_TIMESTAMP + new Date().getTime() + TronConstant.RPCConstant.RPC_ORDER_BY_DESC;
        ResponseEntity<JSONObject> responseEntity = tronRpc.get(url);
        if (responseEntity == null) return null;
        return responseEntity.getBody();
    }

    /**
     * 生成地址
     *
     * @return
     */
    public String createAccountAddress() {
        String url = RPC_URL + WALLET + CREATE_ACCOUNT_ADDRESS;
        ResponseEntity<String> stringResponseEntity = tronRpc.postResponseString(url, null);
        return stringResponseEntity.getBody();
    }

    /**
     * 创建账号交易
     *
     * @param owner
     * @param account
     * @return
     */
    @Override
    public String createAccount(String owner, String account) {
        String url = RPC_URL + WALLET + CREATE_ACCOUNT;
        Map<String, String> map = new HashMap<>();
        map.put("owner_address", owner);
        map.put("account_address", account);
        String param = JsonUtils.objectToJson(map);
        ResponseEntity<String> stringResponseEntity = tronRpc.postResponseString(url, param);
        String body = stringResponseEntity.getBody();
        return body;
    }

    /**
     * 签名
     *
     * @param signParam
     * @return
     */
    @Override
    public String getSign(String signParam, String privateKey) throws Exception {
//        String url = RPC_URL + WALLET + SIGN;
//        ResponseEntity<String> responseEntity = tronRpc.postResponseString(url, signParam);
//        String body = responseEntity.getBody();
        return signTransaction(signParam, privateKey);
    }

    /**
     * 广播交易
     *
     * @param broadcastParam
     * @return
     */
    @Override
    public String getBroadcast(String broadcastParam) {
        String url = RPC_URL + WALLET + BROADCAST;
        ResponseEntity<String> responseEntity = tronRpc.postResponseString(url, broadcastParam);
        String body = responseEntity.getBody();
        return body;
    }

    /**
     * 获取最新区块信息
     *
     * @return
     */
    @Override
    public JSONObject getNewBlock() {
        String url = RPC_URL + WALLET + GET_NOW_BLOCK;
        ResponseEntity<String> responseEntity = tronRpc.getResponseString(url);
        if (responseEntity == null) return null;
        return JSONObject.parseObject(responseEntity.getBody());
    }

    /**
     * 获取区块之间信息
     *
     * @param startNum
     * @param endNum
     * @return
     */
    @Override
    public JSONObject getBlockLimitNext(BigInteger startNum, BigInteger endNum) {
        String url = RPC_URL + WALLET + GET_BLOCK_BY_LIMIT_NEXT;
        Map<String, BigInteger> map = new HashMap<>();
        map.put("startNum", startNum);
        map.put("endNum", endNum);
        String param = JsonUtils.objectToJson(map);
        return getPost(url, param);
    }

    /**
     * 离线签名
     * @param trans
     * @param privateKey
     * @return
     * @throws Exception
     */
    private String signTransaction(String trans, String privateKey) throws Exception {
        String transactionStr = "{\"transaction\":" + trans + ",\"privateKey\":\"" + privateKey + "\"}";
        com.alibaba.fastjson.JSONObject input = com.alibaba.fastjson.JSONObject.parseObject(transactionStr);
        String strTransaction = input.getJSONObject("transaction").toJSONString();
        Protocol.Transaction transaction = Util.packTransaction(strTransaction);
        com.alibaba.fastjson.JSONObject jsonTransaction = com.alibaba.fastjson.JSONObject.parseObject(JsonFormat.printToString(transaction));
        input.put("transaction", jsonTransaction);
        Protocol.TransactionSign.Builder build = Protocol.TransactionSign.newBuilder();
        JsonFormat.merge(input.toJSONString(), build);
        TransactionCapsule reply = getTransactionSign(build.build());
        return Util.printTransaction(reply.getInstance());
    }

    private TransactionCapsule getTransactionSign(Protocol.TransactionSign transactionSign) {
        byte[] privateKey = transactionSign.getPrivateKey().toByteArray();
        TransactionCapsule trx = new TransactionCapsule(transactionSign.getTransaction());
        trx.sign(privateKey);
        return trx;
    }

    /**
     * 通过区块高度查询区块信息
     * @param num
     * @return
     */
    @Override
    public JSONObject getBlockByNum(BigInteger num) {
        String url = RPC_URL + WALLET + GET_BLOCK_BY_NUM;
        Map<String, BigInteger> map = new HashMap<>();
        map.put("num", num);
        String param = JsonUtils.objectToJson(map);
        return getPost(url, param);
    }

    /**
     * post共用方法
     * @param url
     * @param param
     * @return
     */
    private JSONObject getPost(String url, String param) {
        ResponseEntity<String> stringResponseEntity = tronRpc.postResponseString(url, param);
        if (stringResponseEntity == null) return null;
        return JSONObject.parseObject(stringResponseEntity.getBody());
    }

}
