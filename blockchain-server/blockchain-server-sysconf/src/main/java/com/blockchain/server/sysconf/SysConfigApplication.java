package com.blockchain.server.sysconf;

import com.blockchain.server.base.BaseConf;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author huangxl
 * @create 2018-11-13 10:15
 */
@SpringBootApplication(scanBasePackageClasses = {BaseConf.class, SysConfigApplication.class})
public class SysConfigApplication {
    public static void main(String[] args) {
        SpringApplication.run(SysConfigApplication.class,args);
    }
}
