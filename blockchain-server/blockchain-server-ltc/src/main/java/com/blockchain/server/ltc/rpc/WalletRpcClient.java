package com.blockchain.server.ltc.rpc;

import com.googlecode.jsonrpc4j.JsonRpcHttpClient;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * @author hugq
 * @date 2019/2/16 15:54
 */
@Component
public class WalletRpcClient {

    private Logger LOG = LoggerFactory.getLogger(getClass());

    @Value("${ltc.rpc.user}")
    private String RPC_USER; //验证用户名
    @Value("${ltc.rpc.password}")
    private String RPC_PASSWORD; //验证密码
    @Value("${ltc.rpc.url}")
    private String RPC_URL; //验证地址


    /**
     * LTC莱特币RPC身份认证
     */
    JsonRpcHttpClient getClient() {
        JsonRpcHttpClient client = null;
        try {
            String cred = new Base64().encodeToString((RPC_USER + ":" + RPC_PASSWORD).getBytes("UTF-8"));
            Map<String, String> headers = new HashMap<String, String>(1);
            headers.put("Authorization", "Basic " + cred);
            client = new JsonRpcHttpClient(new URL(RPC_URL), headers);
        } catch (Exception e) {
            LOG.info("=== getClient:{} ltc client !===", e.getMessage(), e);
        }
        return client;
    }

}
