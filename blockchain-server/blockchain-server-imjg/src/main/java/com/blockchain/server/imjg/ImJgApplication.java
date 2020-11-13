package com.blockchain.server.imjg;

import com.blockchain.server.base.BaseConf;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackageClasses = {BaseConf.class, ImJgApplication.class})
public class ImJgApplication {

    public static void main(String[] args) {
        System.out.println("==========================1.ImJg--Main==============================");
        SpringApplication.run(ImJgApplication.class, args);
    }

}
