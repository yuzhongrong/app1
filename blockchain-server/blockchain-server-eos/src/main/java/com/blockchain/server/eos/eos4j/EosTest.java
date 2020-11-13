package com.blockchain.server.eos.eos4j;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.blockchain.server.eos.eos4j.api.exception.ApiException;
import com.blockchain.server.eos.eos4j.api.vo.Block;
import com.blockchain.server.eos.eos4j.api.vo.ChainInfo;
import com.blockchain.server.eos.eos4j.api.vo.SignParam;
import com.blockchain.server.eos.eos4j.api.vo.account.Account;
import com.blockchain.server.eos.eos4j.api.vo.transaction.Processed;
import com.blockchain.server.eos.eos4j.api.vo.transaction.Transaction;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;


/**
 * @author Harvey
 * @date 2019/2/14 16:38
 * @user WIN10
 */
public class EosTest {

    private static Rpc rpc = null; // new Rpc("https://api-kylin.eosasia.one");
    private static final RestTemplate restTemplate = new RestTemplate(getClientHttpRequestFactory());

    private static final String EOS_RPC_URI = null; // "http://api-kylin.eosasia.one/";
    private static final String GET_TRANSACTION_SUFFIX_URI = "v1/history/get_transaction";
    private static RedisTemplate redistemplate;

    // public static void main(String[] args) {


        // 创建密钥对
        /*for (int i = 0; i < 2; i++) {
            String pk = Ecc.seedPrivate(UUID.randomUUID().toString());
            String pu = Ecc.privateToPublic(pk);
            System.out.println(pu + " : " + pk);
            // EOS5i7ScCUx85khc3gBWKjBcdBRQdimkPJ3BLtVpArTML71ZEoSoR : 5KbbsfKUd4YshNqkE67AMyvQjEAmiomN66rfcyabx2v4U75yB6p
            // EOS4wLzc4Gfr39nNJfVCT2eLqv1xhxEWwsEj6mVAEiY4o4YcEx2o7 : 5JVDVECcg4WQQF4GYYMqWofwyBLXAWfjpnP22Hw72TnTA6wrC6w
        }*/

        // 创建者
        String creator = "aaasssxxxccc";
        // 创建者密钥
        String privateKeys = "5KBR1zJT1VYxcHDAB1MgDqmXufvFYJDmWtwZGVMUDGottR5k5gJ";
        // 创建的账户
        String newAccount = "55vbdcvfg553";
        String ownerPublic = "EOS5i7ScCUx85khc3gBWKjBcdBRQdimkPJ3BLtVpArTML71ZEoSoR";
        String activePublic = "EOS4wLzc4Gfr39nNJfVCT2eLqv1xhxEWwsEj6mVAEiY4o4YcEx2o7";
        String stakeNetQuantity = new BigDecimal(1.0100).setScale(4, BigDecimal.ROUND_HALF_UP) + " EOS";
        String stakeCpuQuantity = new BigDecimal(10.0100).setScale(4, BigDecimal.ROUND_HALF_UP) + " EOS";


        // 不抵押创建账户 需要提供两个公钥，
        // testOfflineCreate(creator, privateKeys, ownerPublic, activePublic, newAccount);
        // 押创建账户 需要提供两个公钥，
        // testOfflineCreatePledge(creator, privateKeys, ownerPublic, activePublic, newAccount);

        // 提现
        // eosWithdrawDeposit(privateKeys, creator, newAccount, stakeCpuQuantity, "aaasssxxxccc transfer 55vbdcvfg553");

        // 查询账户
        // Account account = getAccount(creator);
        // System.out.println(account);

        // ChainInfo chainInfo = getChainInfo();
        // System.out.println("-----------------------------------------------------------------");
        // getBlock(chainInfo.getHeadBlockNum());

        // postVisitEos();

        // 获取区块交易信息
        // blockPay();

        // eosWithdrawDeposit(privateKeys, creator, newAccount, stakeCpuQuantity, "aaasssxxxccc transfer 55vbdcvfg553");
    // }

    private static void eosWithdrawDeposit(String privateKeys, String creator, String newAccount, String stakeCpuQuantity, String memo) {
        /**
         * 业务参数判断
         */
        GetTransactionParamentDto transfer = testOfflineTransfer(privateKeys, creator, newAccount, stakeCpuQuantity, memo);
//        GetTransactionParamentDto transfer = new GetTransactionParamentDto();
//        transfer.setId("4f70d57f10f305db9b2378d73734bb51b96ca858172fe089d40aa9be1fcdeac9");
//        transfer.setBlock_num_hint(BigInteger.valueOf(34808748));
        //String json = JsonUtils.objectToJson(transfer);
        System.out.println("---------------------------------------");
        getTransaction(transfer);
    }


    // 获取区块高度
    public static BigInteger get_info() {
        String url = EOS_RPC_URI + "/v1/chain/get_info";
        ResponseEntity<JSONObject> body = postVisitEos(url, null);
        Map<String, Object> innerMap = body.getBody().getInnerMap();
        System.out.println(body);
        // System.out.println(blockNumber);
        return (BigInteger) innerMap.get("head_block_num");

    }

    // 获取区块交易记录
    public static void blockPay() {
        // 获取区块信息
        String url = EOS_RPC_URI + "v1/chain/get_block";
        long blcokNum = 34681329;

        ResponseEntity<JSONObject> body = get_block(blcokNum);

        // System.out.println("boby: " + body);
        JSONObject bodyBody = body.getBody();
        JSONArray transactions = bodyBody.getJSONArray("transactions");

        if (transactions.size() > 0) {
            for (int i = 0; i < transactions.size(); i++) {
                // 遍历 jsonarray 数组，把每一个对象转成 json 对象
                JSONObject job = transactions.getJSONObject(i);

                JSONObject trx = null;
                try {
                    trx = job.getJSONObject("trx");
                } catch (Exception e) {
                    continue;
                }
                JSONObject transaction = trx.getJSONObject("transaction");
                JSONArray actions = transaction.getJSONArray("actions");
                JSONObject action = actions.getJSONObject(0);
                // System.out.println("action: " + action);
                JSONObject data = action.getJSONObject("data");
                // System.out.println("data: " + data);

                if (null != data && !"".equals(data)) {
                    if (data.containsKey("to")) {
                        // 解析区块信息，获取充值记录

                        // 交易地址：account: eosio.token
                        System.out.println("account: " + action.get("account"));
                        // 交易类型：transfer type: transfer
                        System.out.println("transfer type: " + action.get("name"));
                        // 交易方：from: aaasssxxxccc
                        System.out.println("from: " + data.get("from"));
                        // 接收方：to: 55vbdcvfg553
                        System.out.println("to: " + data.get("to"));
                        // 交易数额：quantity: 1.0100 EOS
                        System.out.println("quantity: " + data.get("quantity"));
                        // 交易备注：memo: aaasssxxxccc transfer 55vbdcvfg553
                        System.out.println("memo: " + data.get("memo"));
                        // 交易哈希值：hex_data: 8010eabd638c8d31304a616ba3747629742700000000000004454f530000000022616161737373787878636363207472616e7366657220353576626463766667353533
                        System.out.println("hex_data: " + action.get("hex_data"));
                    }
                }
            }
        }
    }

    // 获取区块信息
    public static ResponseEntity<JSONObject> get_block(long blockNumber) {
        String url = EOS_RPC_URI + "v1/chain/get_block";
        Map<String, Long> map = new HashMap<>();
        map.put("block_num_or_id", blockNumber);

        String para = JsonUtils.objectToJson(map);
        ResponseEntity<JSONObject> body = postVisitEos(url, para);
        // System.out.println(body);
        return body;
    }

    /**
     * 提现 离线签名
     *
     * @param privateKeys
     * @param from
     * @param to          用户指定账户提现
     * @param quantity
     * @param memo
     * @return
     */
    public static GetTransactionParamentDto testOfflineTransfer(String privateKeys, String from, String to, String quantity, String memo) {
        System.out.println("================= 开始提现 =================");
//		Rpc rpc = new Rpc("http://47.106.221.171:8888");
        // Rpc rpc = new Rpc("https://api-kylin.eosasia.one");
        // 获取离线签名参数
        SignParam params = rpc.getOfflineSignParams(60l);
        // 离线签名
        OfflineSign sign = new OfflineSign();
        // 交易信息
        String content = "";
        try {
            // 转账需要active的私钥

            content = sign.transfer(params, privateKeys, "eosio.token",
                    from, to, quantity, memo);
            System.out.println(content);
        } catch (Exception e) {
            e.printStackTrace();
        }
        GetTransactionParamentDto getTransactionParamentDto = new GetTransactionParamentDto();
        // 广播交易
        try {
            Transaction tx = rpc.pushTransaction(content);
            Processed processed = tx.getProcessed();
            String transactionId = tx.getTransactionId();
            getTransactionParamentDto.setId(transactionId);
            getTransactionParamentDto.setBlock_num_hint(new BigInteger(processed.getBlockNum()));
            System.out.println("id: " + getTransactionParamentDto.getId());
            System.out.println("blockNum: " + getTransactionParamentDto.getBlock_num_hint());
            System.out.println("================= 提现成功 =================");
            return getTransactionParamentDto;
        } catch (ApiException ex) {
            System.out.println(ex.getError().getCode());
            System.out.println("================= 提现失败 =================");
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("================= 提现失败 =================");
            return null;
        }

    }

    /**
     * 出币查询
     *
     * @param transactionPara
     */
    public static void getTransaction(GetTransactionParamentDto transactionPara) {
        System.out.println("================= 开始出币 =================");
        if (null == transactionPara) {
            // 参数异常
            System.out.println("参数异常错误");
        }
        JSONObject result = getTransactionPost(transactionPara);
        JSONObject trx = result.getJSONObject("trx");
        JSONObject trx1 = trx.getJSONObject("trx");
        JSONArray actions = trx1.getJSONArray("actions");
        JSONObject action = actions.getJSONObject(0);
        JSONObject data = action.getJSONObject("data");

        // token_name：account: eosio.token
        String account = action.getObject("account", String.class);
        System.out.println("account: " + account);
        // 转账类型：transfer: transfer
        String name = action.getObject("name", String.class);
        System.out.println("transfer: " + name);
        // 支付人地址：
        String from = data.getObject("from", String.class);
        System.out.println("from: " + from);
        // 收款人地址：
        String to = data.getObject("to", String.class);
        System.out.println("to: " + to);
        // 数额：
        String quantity = data.getObject("quantity", String.class);
        System.out.println("quantity: " + quantity);
        // 备注：
        String memo = data.getObject("memo", String.class);
        System.out.println("memo: " + memo);
        // 交易哈希值：
        String hexData = action.getObject("hex_data", String.class);
        System.out.println("hax_data: " + hexData);
        // 区块高度
        BigInteger blockNum = result.getObject("block_num", BigInteger.class);
        System.out.println("block_num: " + blockNum);

        System.out.println("================= 出币成功 =================");
    }

    public static JSONObject getTransactionPost(GetTransactionParamentDto para) {
        String url = EOS_RPC_URI + GET_TRANSACTION_SUFFIX_URI;
        String jsonPara = JsonUtils.objectToJson(para);
        ResponseEntity<JSONObject> jsonObjectResponseEntity = postVisitEos(url, jsonPara);
        JSONObject body = jsonObjectResponseEntity.getBody();
        return body;
    }

    /**
     * 获得账户信息
     *
     * @param account 账户名称
     * @return
     */
    public static Account getAccount(String account) {
        return rpc.getAccount(account);
    }

    /**
     * 获得链信息
     *
     * @return
     */
    public static ChainInfo getChainInfo() {
        ChainInfo chainInfo = rpc.getChainInfo();
        System.out.println("chainInfo: " + chainInfo);
        return chainInfo;
    }

    /**
     * 获得区块信息
     *
     * @param blockNumberOrId 区块ID或者高度
     * @return
     */
    public static Block getBlock(String blockNumberOrId) {
        Block block = rpc.getBlock(blockNumberOrId);
        System.out.println("block: " + block);
        return block;
    }


    // 创建账户
    public static void testOfflineCreate(String creator, String privateKeys, String owner, String active, String newAccount) {
        // Rpc rpc = new Rpc("https://api-kylin.eosasia.one");
        // 获取离线签名参数
        SignParam params = rpc.getOfflineSignParams(60l);
        // 离线签名
        OfflineSign sign = new OfflineSign();
        // 交易信息
        String content = "";
        try {
            content = sign.createAccount(params, privateKeys, creator, newAccount, owner, active, 8000l);
            System.out.println("content: " + content);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 广播交易
        try {
            Transaction tx = rpc.pushTransaction(content);
            System.out.println(tx.toString());
        } catch (ApiException ex) {
            System.out.println("error: " + ex);
            System.out.println(ex.getError().getCode());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 创建账户并且抵押
    public static void testOfflineCreatePledge(String creator, String privateKeys, String owner, String active, String newAccount) {
        // Rpc rpc = new Rpc("https://api-kylin.eosasia.one");
        // 获取离线签名参数
        SignParam params = rpc.getOfflineSignParams(60l);
        // 离线签名
        OfflineSign sign = new OfflineSign();
        // 交易信息
        String content = "";
        try {
            Transaction t2 = rpc.createAccount(privateKeys, creator, newAccount, owner, active, 8192l, "1.0100 EOS", "1.0100 EOS", 0l);
            System.out.println("创建成功 = " + t2.getTransactionId() + " \n ");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        // 广播交易
        /*try {
            Transaction tx = rpc.pushTransaction(content);
            System.out.println(tx.toString());
        } catch (ApiException ex) {
            System.out.println("error: " + ex);
            System.out.println(ex.getError().getCode());
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }


    /**
     * eos  get请求
     *
     * @param url
     * @return
     */
    public static ResponseEntity<JSONObject> getVisitEos(String url) {
        ResponseEntity<JSONObject> responseEntity = restTemplate.getForEntity(url, JSONObject.class);
        // Map<String, Object> body = responseEntity.getBody().getInnerMap();
        return responseEntity;
    }

    /**
     * eos post请求
     *
     * @param url
     * @param parameterJson
     * @return
     */
    public static ResponseEntity<JSONObject> postVisitEos(String url, String parameterJson) {
        ResponseEntity<JSONObject> responseEntity = restTemplate.postForEntity(url, parameterJson, JSONObject.class);
        // System.out.println("responseEntity: " + responseEntity);
        // System.out.println("-----------------");

        // Map<String, Object> body = responseEntity.getBody().getInnerMap();
        // System.out.println(responseEntity);
        return responseEntity;
    }

    /**
     * 配置HttpClient超时时间
     *
     * @return
     */
    private static ClientHttpRequestFactory getClientHttpRequestFactory() {
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(10000)
                .setConnectTimeout(10000).build();
        CloseableHttpClient client = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).build();
        return new HttpComponentsClientHttpRequestFactory(client);
    }

}
