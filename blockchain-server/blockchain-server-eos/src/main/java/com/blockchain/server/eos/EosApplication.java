package com.blockchain.server.eos;

import com.blockchain.server.base.BaseConf;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author huangxl
 * @create 2018-11-13 10:15
 */
@SpringBootApplication(scanBasePackageClasses = {BaseConf.class, EosApplication.class})
@RestController
public class EosApplication {
    public static void main(String[] args) {
        SpringApplication.run(EosApplication.class,args);
    }

}
