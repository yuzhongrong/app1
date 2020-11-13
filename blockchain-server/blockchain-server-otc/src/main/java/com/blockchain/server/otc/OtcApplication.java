package com.blockchain.server.otc;

import com.blockchain.server.base.BaseConf;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackageClasses = {BaseConf.class, OtcApplication.class})
public class OtcApplication {
    public static void main(String[] args) {
        SpringApplication.run(OtcApplication.class);
    }
}
