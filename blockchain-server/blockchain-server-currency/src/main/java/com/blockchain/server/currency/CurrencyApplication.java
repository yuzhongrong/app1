package com.blockchain.server.currency;

import com.blockchain.server.base.BaseConf;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * \* <p>Desciption:</p>
 * \* CreateTime: 2019/2/20 17:35
 * \* User: XianChaoWei
 * \* Version: V1.0
 * \
 */
@SpringBootApplication(scanBasePackageClasses = {BaseConf.class, CurrencyApplication.class})
public class CurrencyApplication {
    public static void main(String[] args) {
        SpringApplication.run(CurrencyApplication.class,args);
    }
}
