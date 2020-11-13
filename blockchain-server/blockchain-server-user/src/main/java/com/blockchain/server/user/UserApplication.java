package com.blockchain.server.user;

import com.blockchain.server.base.BaseConf;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author xusm
 * @data 2019/2/21 14:06
 */
@SpringBootApplication(scanBasePackageClasses = {BaseConf.class, UserApplication.class})
@EnableConfigurationProperties
@EnableAsync
public class UserApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class,args);
    }
}
