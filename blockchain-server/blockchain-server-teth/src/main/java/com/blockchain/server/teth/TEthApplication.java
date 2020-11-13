package com.blockchain.server.teth;

import com.blockchain.server.base.BaseConf;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author huangxl
 * @create 2018-11-13 10:15
 */

@SpringBootApplication(scanBasePackageClasses = {BaseConf.class, TEthApplication.class})
public class TEthApplication {
    public static void main(String[] args) {
        SpringApplication.run(TEthApplication.class, args);
    }
}
