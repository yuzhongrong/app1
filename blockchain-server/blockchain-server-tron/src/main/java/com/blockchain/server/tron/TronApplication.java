package com.blockchain.server.tron;

import com.blockchain.server.base.BaseConf;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author huangxl
 * @create 2018-11-13 10:15
 */
@SpringBootApplication(scanBasePackageClasses = {BaseConf.class, TronApplication.class})
@RestController
public class TronApplication {
    public static void main(String[] args) {
        SpringApplication.run(TronApplication.class,args);
    }

}
