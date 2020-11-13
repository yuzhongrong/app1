package com.codingapi.tx.netty.service;


import com.lorne.core.framework.utils.http.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * create by lorne on 2017/11/17
 */
@Component
public class TxManagerHttpRequestHelper {

    private Logger logger = LoggerFactory.getLogger(TxManagerHttpRequestHelper.class);

    public String httpGet(String url) {
        logger.info("load HttpRequestService:" + url);
        return HttpUtils.get(url);
    }

    public String httpPost(String url, String params) {
        logger.info("load HttpRequestService:" + url + "?" + params);
        return HttpUtils.post(url,params);
    }


}
