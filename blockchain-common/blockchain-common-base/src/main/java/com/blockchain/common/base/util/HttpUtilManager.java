package com.blockchain.common.base.util;

import org.apache.commons.io.IOUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class HttpUtilManager {

    private static HttpClient client;
    private static long startTime = System.currentTimeMillis();
    public static PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
    private static ConnectionKeepAliveStrategy keepAliveStrat = new DefaultConnectionKeepAliveStrategy() {
        public long getKeepAliveDuration(
                HttpResponse response,
                HttpContext context) {
            long keepAlive = super.getKeepAliveDuration(response, context);

            if (keepAlive == -1) {
                keepAlive = 5000;
            }
            return keepAlive;
        }

    };

    private HttpUtilManager() {
        client = HttpClients.custom().setConnectionManager(cm).setKeepAliveStrategy(keepAliveStrat).build();
    }

    public static void IdleConnectionMonitor() {

        if (System.currentTimeMillis() - startTime > 30000) {
            startTime = System.currentTimeMillis();
            cm.closeExpiredConnections();
            cm.closeIdleConnections(30, TimeUnit.SECONDS);
        }
    }

    private static RequestConfig requestConfig = RequestConfig.custom()
            .setSocketTimeout(20000)
            .setConnectTimeout(20000)
            .setConnectionRequestTimeout(20000)
            .build();


    public static HttpUtilManager getInstance() {
        return new HttpUtilManager();
    }

    public HttpClient getHttpClient() {
        return client;
    }

    private HttpPost httpPostMethod(String url) {
        return new HttpPost(url);
    }

    private HttpRequestBase httpGetMethod(String url) {
        return new HttpGet(url);
    }

    public String requestHttpGet(String url){
       return this.requestHttpGet(url,null);
    }

    public String requestHttpGet(String url, String param){
        InputStream is = null;
        String responseData = "";
        try {
            IdleConnectionMonitor();
            if (param != null && !param.equals("")) {
                if (url.endsWith("?")) {
                    url = url + param;
                } else {
                    url = url + "?" + param;
                }
            }
            HttpRequestBase method = this.httpGetMethod(url);
            method.setConfig(requestConfig);
            HttpResponse response = client.execute(method);
            HttpEntity entity = response.getEntity();
            if (entity == null) {
                return "";
            }
            is = entity.getContent();
            responseData = IOUtils.toString(is, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return responseData;
    }

    public String requestHttpPost(String url, Map<String, String> params) throws IOException {
        InputStream is = null;
        String responseData = "";
        IdleConnectionMonitor();

        HttpPost method = this.httpPostMethod(url);
        List<NameValuePair> valuePairs = this.convertMap2PostParams(params);

        UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(valuePairs, Consts.UTF_8);
        method.setEntity(urlEncodedFormEntity);
        method.setConfig(requestConfig);
        HttpResponse response = client.execute(method);
        HttpEntity entity = response.getEntity();
        if (entity == null) {
            return "";
        }
        is = entity.getContent();
        responseData = IOUtils.toString(is, "UTF-8");
        return responseData;

    }

    private List<NameValuePair> convertMap2PostParams(Map<String, String> params) {
        List<String> keys = new ArrayList<String>(params.keySet());
        if (keys.isEmpty()) {
            return null;
        }
        int keySize = keys.size();
        List<NameValuePair> data = new LinkedList<NameValuePair>();
        for (int i = 0; i < keySize; i++) {
            String key = keys.get(i);
            String value = params.get(key);
            data.add(new BasicNameValuePair(key, value));
        }
        return data;
    }

}
