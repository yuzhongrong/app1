package com.blockchain.server.user.common.config;

import com.alibaba.fastjson.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import com.alibaba.fastjson.JSONObject;
import com.blockchain.server.user.common.utils.SmsCodeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class SmsUtils {
    private static final Logger LOG = LoggerFactory.getLogger(SmsUtils.class);
    public static void send(String txt,String phone ) {

        LOG.info("phone is:"+phone);
        //短信下发
        String sendUrl = "http://intapi.253.com/msg/send/json";
        Map map = new HashMap();
        map.put("account","I3027764");//API账号
        map.put("password","EwuK5GL63g8d8a");//API密码
        map.put("msg",txt);//短信内容
        map.put("phone",phone);//手机号
        map.put("report","true");//是否需要状态报告
        map.put("extend","DOBTmall");//自定义扩展码
        JSONObject js = (JSONObject) JSONObject.toJSON(map);
        LOG.info("发送短信返回消息:"+sendSmsByPost(sendUrl,js.toString()));
        //查询余额
//        String balanceUrl = "http://intapi.253.com/msg/send/json";
//        Map map1 = new HashMap();
//        map1.put("account","I3027764");
//        map1.put("password","EwuK5GL63g8d8a");
//        JSONObject js1 = (JSONObject) JSONObject.toJSON(map1);
//        System.out.println(sendSmsByPost(balanceUrl,js1.toString()));
    }
    public static String sendSmsByPost(String path, String postContent) {
        URL url = null;
        try {
            url = new URL(path);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setConnectTimeout(10000);
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setRequestProperty("Charset", "UTF-8");
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.connect();
            OutputStream os=httpURLConnection.getOutputStream();
            os.write(postContent.getBytes("UTF-8"));
            os.flush();
            StringBuilder sb = new StringBuilder();
            int httpRspCode = httpURLConnection.getResponseCode();
            if (httpRspCode == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(httpURLConnection.getInputStream(), "utf-8"));
                String line = null;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                br.close();
                return sb.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
