package com.blockchain.server.imjg.common.util;

import org.apache.tomcat.util.codec.binary.Base64;


public class JiGuangIMUtils {


    public static String getAuthString(String appkey,String secret){
        return Base64.encodeBase64String((appkey+":"+secret).getBytes());
    }

    public static String doRegister(String url,String json,String appkey,String secret){
        return HttpUtils.doJiGuangPost(url,json,JiGuangIMUtils.getAuthString(appkey,secret));
    }


}
