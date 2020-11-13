package com.blockchain.server.databot;

import com.blockchain.server.base.BaseConf;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackageClasses = {BaseConf.class, BotApplication.class})
public class BotApplication {
    public static void main(String[] args) {
        SpringApplication.run(BotApplication.class);
    }
}
