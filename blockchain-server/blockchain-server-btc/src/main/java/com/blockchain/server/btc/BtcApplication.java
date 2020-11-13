package com.blockchain.server.btc;

import com.blockchain.server.base.BaseConf;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@ServletComponentScan
@SpringBootApplication(scanBasePackageClasses = {BaseConf.class, BtcApplication.class})
public class BtcApplication {
    public static void main(String[] args) {
//        initSystemConfig();
        SpringApplication.run(BtcApplication.class, args);
    }

    /**
     * 初始化系统配置
     */
    private static void initSystemConfig() {
        System.setProperty("log.root", "ALL,CONSOLE,info,error,DEBUG");
        System.setProperty("service.id", "dapp-btc-server");
    }

//    @Bean
//    public Docket createRestApi() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .apiInfo(apiInfo())
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("com.blockchain.server.btc.controller"))
//                .paths(PathSelectors.any())
//                .build();
//    }
//
//    private ApiInfo apiInfo() {
//        return new ApiInfoBuilder()
//                .title("比特币托管账户系统（dapp-eth-server）RESTful APIs")
//                .description("")
//                .termsOfServiceUrl("")
//                .version("1.0")
//                .build();
//    }
}
