package com.blockchain.server.tron.tron;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * @author Harvey Luo
 * @date 2019/5/29 10:41
 */
public class Rpc {

    private static final RestTemplate restTemplate = new RestTemplate(getClientHttpRequestFactory());


    SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    public Rpc() {
        dateFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));
    }


    /**
     * get请求，返回JSONObject
     * @param url
     * @return
     */
    public ResponseEntity<JSONObject> get(String url) {
        ResponseEntity<JSONObject> responseEntity = null;
        try {
            responseEntity = restTemplate.getForEntity(url, JSONObject.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseEntity;
    }

    /**
     * get请求，返回String
     * @param url
     * @return
     */
    public ResponseEntity<String> getResponseString(String url) {
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.getForEntity(url, String.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseEntity;
    }

    /**
     * post请求
     *
     * @param url
     * @param parameterJson
     * @return
     */
    public ResponseEntity<String> postResponseString(String url, String parameterJson) {
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(url, parameterJson, String.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
