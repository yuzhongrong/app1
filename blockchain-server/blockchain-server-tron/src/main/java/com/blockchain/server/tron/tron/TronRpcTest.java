//package com.blockchain.server.tron.tron;
//
//import com.alibaba.druid.VERSION;
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//import com.blockchain.common.base.util.JsonUtils;
//import com.blockchain.server.tron.common.constant.TronConstant;
//import com.blockchain.server.tron.common.util.HexUtil;
//import com.sun.org.apache.xpath.internal.operations.Bool;
//import org.springframework.http.ResponseEntity;
//
//import java.math.BigDecimal;
//import java.math.BigInteger;
//import java.util.*;
//
///**
// * @author Harvey Luo
// * @date 2019/5/29 10:47
// */
//public class TronRpcTest {
//
//    // private static final String TRON_URI = "http://api.trongrid.io/";
//    private static final String TRON_URI = "https://api.trongrid.io/";
//    private static final String TRON_VERSION = "v1/";
//    private static final String ACCOUNTS = "accounts/";
//    private static final String CONTRACTS = "contracts/";
//    private static final String EVENTS = "/events";
//    private static final String WALLET = "wallet/";
//    private static final String GET_NOW_BLOCK = "getnowblock";
//    private static final String GET_BLOCK_BY_NUM = "getblockbynum";
//    private static final String ADDRESS = "TXvLDE5z2vHPuev4bvdCe52EBp67wfew9a";
//    private static final String BLOCK_ADDRESS = "TMuA6YqfCeX8EhbfYEg5y7S4DqzSJireY9";
//    private static final String CONTRACT_ADDRESS = "TNYNLRkqq956bQc2buvoLbaLgh25RkJMiN";
//    // 正式节点
//    // private static final String SIGN_URL = "https://api.trongrid.io/wallet/gettransactionsign";
//    // private static final String BROADCAST_URL = "https://api.trongrid.io/wallet/broadcasttransaction";
//    // private static final String CREATE_TRANSACTION_URL = "https://api.trongrid.io/wallet/createtransaction";
//    // 测试网点
//    private static final String TRON_URI_SHASTA = "https://api.shasta.trongrid.io/";
//    private static final String CREATE_TRANSACTION_URL = "wallet/createtransaction";
//    private static final String SIGN_URL = "wallet/gettransactionsign";
//    private static final String BROADCAST_URL = "wallet/broadcasttransaction";
//    private static final String GET_TRANSACTION_URL = "wallet/gettransactionbyid";
//    private static final String CREATE_ACCOUNT_ADDRESS = "wallet/generateaddress";
//    private static final String CREATE_ACCOUNT = "wallet/createaccount";
//    private static final String TRIGGER_SMART_CONTRACT = "wallet/triggersmartcontract";
//
////    public static void main(String[] args) {
//        Rpc rpc = new Rpc();
////
////        // String accountUrl = TRON_URI + TRON_VERSION + ACCOUNTS + ADDRESS;
////        // String address = getAddress(accountUrl, rpc);
////        // // 默认二十条
////        // int limit = 20;
////        // String transactionUrl = TRON_URI + TRON_VERSION + ACCOUNTS + address + "/transactions?limit=" + limit + "&only_to=true";
////        // getTransaction(transactionUrl, rpc);
////
////        // 交易
//        String owner = "4121185cefce928c232d95500ca6d24872f440f68e";
//        String privateKey = "c4fafed12ce163764a668dd77a6abba86a8b0b4425faa871e23230b17222fca5";
////
//        String to = "415ea97e565b5f3d4826301402f7e27bf632d0538b";
//        BigInteger amount = new BigInteger("10012000");
//        // transaction(rpc, owner, to, amount, privateKey);
////
////        // 根据交易id查询交易信息
////        String hashId = "7ffdbb8ebf3e9257589db7a2b670093c696579a29b55cd74188399da6636ae35";
////        // getTransactionById(rpc, hashId);
////
////        // 创建账户
////         getCreateAccount(rpc, owner, privateKey);
////
////        // 查询账户16进制地址
////        // getAccountByBase58(rpc, BLOCK_ADDRESS);
////
////        // disposeTRC20(rpc, CONTRACT_ADDRESS);
////
////        // 爬取最新区块
////        // getBlockInformation(rpc);
////
////        // 获取区间区块信息
////        // getBlockByLimitNext(rpc);
////
////        // 代币转账
////        // String tokenId = "1002000";
////        String tokenId = "1000044";
////        String ownerAddress = "4121185cefce928c232d95500ca6d24872f440f68e";
////        String toAddress = "41458a51ed5df7ae2a9ec5e3ff42a570a2823d076f";
////        // transactionAsset(rpc, ownerAddress, toAddress, amount, privateKey, tokenId);
////
////        // 出发TRC20代币转账合约
////        String contractAddress = "4189E707191BD5AF4EE41E394ABC2C21B60E02B04A";
////        triggerSmartContract(rpc, ownerAddress, toAddress, amount, privateKey, contractAddress);
////    }
//
//    /**
//     * 出发TRC20代币转账合约
//     *
//     * @param rpc
//     * @param ownerAddress
//     * @param toAddress
//     * @param amount
//     * @param privateKey
//     * @param contractAddress
//     */
//    private static void triggerSmartContract(Rpc rpc, String ownerAddress, String toAddress, BigInteger amount, String privateKey, String contractAddress) {
//        String url = TRON_URI + TRIGGER_SMART_CONTRACT;
//        while (64 > toAddress.length()) {
//            StringBuilder builder = new StringBuilder();
//            builder.append("0").append(toAddress);
//            toAddress = builder.toString();
//        }
//        String parameter = toAddress + String.format("%064d", amount);
//        Map<String, Object> map = new HashMap<>();
//        map.put("contract_address", contractAddress);
//        map.put("function_selector", "transfer(address, uint256)");
//        map.put("parameter", parameter);
//        map.put("fee_limit", 1000000);
//        map.put("call_value", 100);
//        map.put("owner_address", ownerAddress);
//        ResponseEntity<String> stringResponseEntity = rpc.postResponseString(url, JsonUtils.objectToJson(map));
//        System.out.println(stringResponseEntity.toString());
//        JSONObject data = JSONObject.parseObject(stringResponseEntity.getBody());
//        JSONObject transaction = data.getJSONObject("transaction");
//        transaction.remove("visible");
//        transaction.remove("raw_data_hex");
//        signAndBroadcast(JsonUtils.objectToJson(transaction), privateKey, rpc);
//    }
//
//    /**
//     * 代币转账
//     *
//     * @param rpc
//     * @param ownerAddress
//     * @param toAddress
//     * @param amount
//     * @param privateKey
//     * @param tokenId
//     */
//    private static void transactionAsset(Rpc rpc, String ownerAddress, String toAddress, BigInteger amount, String privateKey, String tokenId) {
//        String url = TRON_URI_SHASTA + WALLET + "transferasset";
//        Map<String, Object> map = new HashMap<>();
//        map.put("owner_address", ownerAddress);
//        map.put("to_address", toAddress);
//        map.put("amount", amount);
//        map.put("asset_name", HexUtil.str2HexStr(tokenId));
//        String param = JsonUtils.objectToJson(map);
//        ResponseEntity<String> stringResponseEntity = rpc.postResponseString(url, param);
//        System.out.println(stringResponseEntity.getBody());
//        JSONObject body = JSONObject.parseObject(stringResponseEntity.getBody());
//        body.remove("visible");
//        signAndBroadcast(body.toString(), privateKey, rpc);
//    }
//
//    /**
//     * 获取两区快之间的区块信息
//     *
//     * @param rpc
//     */
//    private static void getBlockByLimitNext(Rpc rpc) {
//        String url = "https://api.trongrid.io/wallet/getblockbylimitnext";
//        Map<String, String> map = new HashMap<>();
//        map.put("startNum", "10300650");
//        map.put("endNum", "10300655");
//        String param = JsonUtils.objectToJson(map);
//        ResponseEntity<String> stringResponseEntity = rpc.postResponseString(url, param);
//        System.out.println(stringResponseEntity);
//    }
//
//    /**
//     * 爬取区块信息
//     *
//     * @param rpc
//     */
//    private static void getBlockInformation(Rpc rpc) {
//        String url = TRON_URI + WALLET + GET_NOW_BLOCK;//  + "?num=10276293";
//        System.out.println(url);
//        Map<String, String> map = new HashMap<>();
//        map.put("num", "10275214");
//        String param = JsonUtils.objectToJson(map);
//        ResponseEntity<String> stringResponseEntity = rpc.getResponseString(url);
//        String body = stringResponseEntity.getBody();
//        System.out.println(body);
////        analysisBlockInformation(body);
//    }
//
//    /**
//     * 解析区块信息
//     *
//     * @param body
//     */
//    private static void analysisBlockInformation(String body) {
//        JSONObject bodyJson = JSONObject.parseObject(body);
//        Integer blockNumber = bodyJson.getJSONObject("block_header").getJSONObject("raw_data").getInteger("number");
//        System.out.println("blockNumber ==> " + blockNumber);
//        JSONArray transactions = bodyJson.getJSONArray("transactions");
//        // System.out.println(transactions.size());
//        if (transactions.size() <= 0) System.out.println("gggggggggggg");
//        for (int i = 0; i < transactions.size(); i++) {
//            JSONObject data = transactions.getJSONObject(i);
//            String contractRet = data.getJSONArray("ret").getJSONObject(0).getString("contractRet");
//            System.out.print("contractRet ==> " + contractRet);
//            if (!"SUCCESS".equalsIgnoreCase(contractRet)) System.out.println(" ：该记录有问题");
//            String txID = data.getString("txID");
//            System.out.println("，txID ==> " + txID);
//            JSONObject contract = data.getJSONObject("raw_data").getJSONArray("contract").getJSONObject(0);
//            String type = contract.getString("type");
//            JSONObject value = contract.getJSONObject("parameter").getJSONObject("value");
//            if (type.equalsIgnoreCase("TransferContract")) {
//                String amount = value.getString("amount");
//                String owner = value.getString("owner_address");
//                String to = value.getString("to_address");
//                System.out.print("主链币：");
//                System.out.print(" amount ==> " + amount);
//                System.out.print("，owner ==> " + owner);
//                System.out.println("，to ==> " + to);
//            }
//            if ("TransferAssetContract".equalsIgnoreCase(type)) {
//                String amount = value.getString("amount");
//                String assetName = value.getString("asset_name");
//                String owner = value.getString("owner_address");
//                String to = value.getString("to_address");
//                System.out.print("TRC10代币：");
//                System.out.print(" assetName ==> " + assetName);
//                System.out.print("，amount ==> " + amount);
//                System.out.print("，owner ==> " + owner);
//                System.out.println("，to ==> " + to);
//            }
//            if ("TriggerSmartContract".equalsIgnoreCase(type)) {
//                String TRC20Data = value.getString("data");
//                String owner = value.getString("owner_address");
//                String contractAddress = value.getString("contract_address");
//                String callValue = value.getString("call_value");
//                System.out.print("TRC20代币：");
//                System.out.print(" TRC20Data ==> " + TRC20Data);
//                System.out.print("，owner ==> " + owner);
//                System.out.print("，callValue ==> " + callValue);
//                System.out.println("，contractAddress ==> " + contractAddress);
//            }
//        }
//    }
//
//    /**
//     * 通过事件爬取TRC20代币充值
//     *
//     * @param rpc
//     * @param contractAddress
//     */
//    private static void disposeTRC20(Rpc rpc, String contractAddress) {
//        String url = TRON_URI + TRON_VERSION + CONTRACTS + contractAddress + EVENTS + TronConstant.RPCConstant.RPC_EVENT_NAME + TronConstant.RPCConstant.RPC_MIN_BLOCK_TIMESTAMP + "1561002060000" + TronConstant.RPCConstant.RPC_MAX_BLOCK_TIMESTAMP + new Date().getTime() + TronConstant.RPCConstant.RPC_LIMIT + TronConstant.RPCConstant.OFFSET;
//        // System.out.println("url ==> " + url);
//        ResponseEntity<JSONObject> responseEntity = rpc.get(url);
//        JSONObject body = responseEntity.getBody();
//        // System.out.println("body ==> " + body);
//        analysisTRC20Body(body);
//    }
//
//    /**
//     * 解析获取TRC20代币充值数据
//     *
//     * @param body
//     */
//    private static void analysisTRC20Body(JSONObject body) {
//        Boolean success = body.getBoolean("success");
//        if (!success) System.out.println("获取充值记录失败，请稍后重试");
//        Integer pageSize = body.getJSONObject("meta").getInteger("page_size");
//        System.out.println("pageSize ==> " + pageSize);
//        JSONArray dataArray = body.getJSONArray("data");
//        if (dataArray.size() == 0) {
//            System.out.println("无充值记录");
//            return;
//        }
//        for (int i = 0; i < dataArray.size(); i++) {
//            JSONObject data = dataArray.getJSONObject(i);
//            String hashId = data.getString("transaction_id");
//            JSONObject result = data.getJSONObject("result");
//            Long timestamp = data.getLong("block_timestamp");
//            String from = result.getString("_from");
//            String to = result.getString("_to");
//            BigDecimal value = result.getBigDecimal("value");
//            from = 41 + from.substring(2);
//            to = 41 + to.substring(2);
//            BigDecimal amount = value.divide(BigDecimal.TEN.pow(6));
//            System.out.println("hashId ==> " + hashId);
//            System.out.println("timestamp ==> " + timestamp);
//            System.out.println("from ==> " + from);
//            System.out.println("to ==> " + to);
//            System.out.println("value ==> " + value);
//            System.out.println("amount ==> " + amount);
//        }
//    }
//
//    private static void getAccountByBase58(Rpc rpc, String blockAddress) {
//        String url = TRON_URI + TRON_VERSION + ACCOUNTS + blockAddress;
//        ResponseEntity<JSONObject> responseEntity = rpc.get(url);
//        JSONObject body = responseEntity.getBody();
//        Boolean success = body.getBoolean("success");
//        System.out.println("success ==> " + success);
//        String hexAddress = body.getJSONArray("data").getJSONObject(0).getString("address");
//        System.out.println("blockAddress == > " + blockAddress);
//        System.out.println("hexAddress == > " + hexAddress);
//    }
//
//    /**
//     * 创建账号
//     *
//     * @param rpc
//     */
//    private static void getCreateAccount(Rpc rpc, String owner, String privateKey) {
//        String accountAddress = createAccountAddress(TRON_URI_SHASTA + CREATE_ACCOUNT_ADDRESS, rpc);
//        JSONObject accountAddressJson = JSONObject.parseObject(accountAddress);
//        String hexAddress = accountAddressJson.getObject("hexAddress", String.class);
//        String accountTransaction = createAccount(TRON_URI_SHASTA + CREATE_ACCOUNT, rpc, owner, hexAddress);
//        signAndBroadcast(accountTransaction, privateKey, rpc);
//    }
//
//    /**
//     * 创建账户
//     *
//     * @param url
//     * @param rpc
//     * @param owner
//     * @param hexAddress
//     * @return
//     */
//    private static String createAccount(String url, Rpc rpc, String owner, String hexAddress) {
//        Map<String, String> map = new HashMap<>();
//        map.put("owner_address", owner);
//        map.put("account_address", hexAddress);
//        String param = JsonUtils.objectToJson(map);
//        System.out.println("创建账户参数 ==> " + param);
//        ResponseEntity<String> stringResponseEntity = rpc.postResponseString(url, param);
//        String body = stringResponseEntity.getBody();
//        System.out.println("创建账户返回 ==> " + body);
//        return body;
//    }
//
//    /**
//     * 生成地址
//     *
//     * @param url
//     * @param rpc
//     * @return
//     */
//    private static String createAccountAddress(String url, Rpc rpc) {
//        ResponseEntity<String> stringResponseEntity = rpc.postResponseString(url, null);
//        System.out.println("生成地址返回 ==> " + stringResponseEntity.getBody());
//        return stringResponseEntity.getBody();
//    }
//
//    /**
//     * 根据交易id查询交易信息
//     *
//     * @param rpc
//     */
//    private static void getTransactionById(Rpc rpc, String hashId) {
//        Map<String, String> map = new HashMap<>();
//        map.put("value", hashId);
//        String param = JsonUtils.objectToJson(map);
//        ResponseEntity<String> responseString = rpc.postResponseString(TRON_URI_SHASTA + GET_TRANSACTION_URL, param);
//        String body = responseString.getBody();
//        System.out.println("查询交易返回 ==> " + body);
//    }
//
//    private static void signAndBroadcast(String transaction, String privateKey, Rpc rpc) {
//        JSONObject transactionJson = JSONObject.parseObject(transaction);
//        transactionJson.remove("raw_data_hex");
//        Map<String, Object> map = new HashMap<>();
//        map.put("transaction", transactionJson);
//        map.put("privateKey", privateKey);
//        String SignParam = JsonUtils.objectToJson(map);
//        // 签名
//        String signBody = getSign(TRON_URI_SHASTA + SIGN_URL, rpc, SignParam);
//        JSONObject jsonObject = JSONObject.parseObject(signBody);
//        jsonObject.remove("raw_data_hex");
//        String broadcastParam = JsonUtils.objectToJson(jsonObject);
//        // 广播
//        broadcastTransaction(TRON_URI_SHASTA + BROADCAST_URL, rpc, broadcastParam);
//    }
//
//    /**
//     * 交易
//     *
//     * @param rpc
//     */
//    private static void transaction(Rpc rpc, String owner, String to, BigInteger amount, String privateKey) {
//        // 创建交易
//        String transaction = createTransaction(TRON_URI_SHASTA + CREATE_TRANSACTION_URL, rpc, owner, to, amount);
//        signAndBroadcast(transaction, privateKey, rpc);
//    }
//
//    /**
//     * 创建交易
//     *
//     * @param url
//     * @param rpc
//     * @return
//     */
//    private static String createTransaction(String url, Rpc rpc, String owner, String to, BigInteger amount) {
//        Map<String, Object> map = new HashMap<>();
//        map.put("to_address", to);
//        map.put("owner_address", owner);
//        map.put("amount", amount);
//        String param = JsonUtils.objectToJson(map);
//        System.out.println("创建交易参数 ==> " + param);
//        ResponseEntity<String> post = rpc.postResponseString(url, param);
//        System.out.println("创建交易返回值 ==> " + post);
//        return post.getBody();
//    }
//
//    /**
//     * 广播交易
//     *
//     * @param url
//     * @param rpc
//     * @param param
//     */
//    private static void broadcastTransaction(String url, Rpc rpc, String param) {
//        System.out.println("广播交易参数 ==> " + param);
//        ResponseEntity<String> responseEntity = rpc.postResponseString(url, param);
//        System.out.println("广播交易返回 ==> " + responseEntity);
//        Boolean result = JSONObject.parseObject(responseEntity.getBody()).getBoolean("result");
//
//        if (result) {
//            System.out.println("是的呢");
//        } else {
//            System.out.println("不是的呢");
//        }
//        System.out.println("result ==> " + result);
//    }
//
//    /**
//     * 签名
//     *
//     * @param url
//     * @param rpc
//     * @return
//     */
//    private static String getSign(String url, Rpc rpc, String param) {
//        /*System.out.println("url ==> " + url);
//        Map<String, Object> map = new HashMap<>();
//        Map<String, Object> transaction = new HashMap<>();
//        Map<String, Object> rawData = new HashMap<>();
//        Object[] contractArray = new Object[1];
//        Map<String, Object> contract = new HashMap<>();
//        Map<String, Object> parameter = new HashMap<>();
//        Map<String, Object> value = new HashMap<>();
//        value.put("amount", 1000);
//        value.put("owner_address", "41d1e7a6bc354106cb410e65ff8b181c600ff14292");
//        value.put("to_address", "41e9d79cc47518930bc322d9bf7cddd260a0260a8d");
//        parameter.put("value", value);
//        parameter.put("type_url", "type.googleapis.com/protocol.TransferContract");
//        contract.put("parameter", parameter);
//        contract.put("type", "TransferContract");
//        contractArray[0] = contract;
//        rawData.put("contract", contractArray);
//        rawData.put("ref_block_bytes", "3b7a");
//        rawData.put("ref_block_hash", "61b27c3f39abb92e");
//        rawData.put("expiration", 1559716602000L);
//        rawData.put("timestamp", 1559716544068L);
//        transaction.put("txID", "c8495ddfc07054d04fa5fe1d010349c59fb9e4d6f22041b7d098768d68d73c7d");
//        transaction.put("raw_data", rawData);
//        map.put("transaction", transaction);
//        map.put("privateKey", "56b444d019eb8d33b418df3b1d66bf3ed11c5323db1528252d44707221c2c4ee");
//        String param = JsonUtils.objectToJson(map);*/
//        System.out.println("签名参数 ==> " + param);
//        ResponseEntity<String> responseEntity = rpc.postResponseString(url, param);
//        String body = responseEntity.getBody();
//        System.out.println("签名返回 ==> " + body);
//        return body;
//    }
//
//    /**
//     * 获取账号信息
//     *
//     * @param url
//     * @param rpc
//     * @return
//     */
//    private static String getAddress(String url, Rpc rpc) {
//        System.out.println("url ==> " + url);
//        ResponseEntity<JSONObject> responseEntity = rpc.get(url);
//        JSONObject body = responseEntity.getBody();
//        return analysisAddress(body);
//    }
//
//    /**
//     * 解析账户信息
//     *
//     * @param body
//     * @return
//     */
//    private static String analysisAddress(JSONObject body) {
//        Boolean success = body.getObject("success", Boolean.class);
//        System.out.println("success ==> " + success);
//        JSONObject data = body.getJSONArray("data").getJSONObject(0);
//        String address = data.getObject("address", String.class);
//        String balance = data.getObject("balance", String.class);
//        Long createTime = data.getObject("create_time", Long.class);
//        System.out.println("address ==> " + address);
//        System.out.println("balance ==> " + balance);
//        System.out.println("createTime ==> " + createTime);
//        System.out.println("***************************************************");
//        return address;
//    }
//
//    /**
//     * 获取账户信息资料
//     */
//    private static void getTransaction(String url, Rpc rpc) {
//
//        System.out.println("url: " + url);
//        ResponseEntity<JSONObject> responseEntity = rpc.get(url);
//        JSONObject body = responseEntity.getBody();
//        analysisTransaction(body);
//    }
//
//    /**
//     * 解析交易信息
//     *
//     * @param body
//     */
//    private static void analysisTransaction(JSONObject body) {
//        Boolean success = body.getObject("success", Boolean.class);
//        System.out.println(success);
//        JSONArray data = body.getJSONArray("data");
//        if (data.size() > 0) {
//            for (int i = 0; i < data.size(); i++) {
//                JSONObject job = data.getJSONObject(i);
//                String txID = job.getObject("txID", String.class);
//                Date blockTimestamp = job.getObject("block_timestamp", Date.class);
//                JSONObject rawData = job.getObject("raw_data", JSONObject.class);
//                JSONArray contractArray = rawData.getJSONArray("contract");
//                JSONObject contract = contractArray.getJSONObject(0);
//                JSONObject parameter = contract.getJSONObject("parameter");
//                JSONObject value = parameter.getJSONObject("value");
//                String type = contract.getObject("type", String.class);
//                String amount = value.getObject("amount", String.class);
//                String assetName = value.getObject("asset_name", String.class);
//                String to = value.getObject("to_address", String.class);
//                String owner = value.getObject("owner_address", String.class);
//                Date timestamp = rawData.getObject("timestamp", Date.class);
//
//                System.out.println("txID ==> " + txID);
//                System.out.println("blockTimestamp ==> " + blockTimestamp.getTime());
//                System.out.println("amount ==> " + amount);
//                System.out.println("assetName ==> " + assetName);
//                System.out.println("to ==> " + to);
//                System.out.println("owner ==> " + owner);
//                System.out.println("type ==> " + type);
//                System.out.println("timestamp ==> " + timestamp.getTime());
//                System.out.println("=========================================================");
//            }
//        }
//    }
//
//}
