package com.blockchain.server.eth;

import com.blockchain.server.base.BaseConf;
import com.blockchain.server.eth.common.config.EthConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @author huangxl
 * @create 2018-11-13 10:15
 */

@SpringBootApplication(scanBasePackageClasses = {BaseConf.class, EthApplication.class})
public class EthApplication {
    public static void main(String[] args) {
        SpringApplication.run(EthApplication.class, args);
    }
}
