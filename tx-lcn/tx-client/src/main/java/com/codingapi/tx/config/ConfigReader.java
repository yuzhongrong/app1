package com.codingapi.tx.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * create by lorne on 2017/11/13
 */
@Component
public class ConfigReader {


    private Logger logger = LoggerFactory.getLogger(ConfigReader.class);

    @Autowired
    private ApplicationContext spring;

    @Value("${tm.manager.url}")
    private String managerUrl;


    public String getTxUrl() {
        logger.debug("load tx-manager:" + managerUrl);
        return managerUrl;
    }


}
