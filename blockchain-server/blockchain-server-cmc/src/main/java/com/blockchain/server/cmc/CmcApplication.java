package com.blockchain.server.cmc;

import com.blockchain.server.base.BaseConf;
import com.blockchain.server.cmc.entity.BtcApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication(scanBasePackageClasses = {BaseConf.class, CmcApplication.class})
public class CmcApplication {
    public static void main(String[] args) {
//        initSystemConfig();
        SpringApplication.run(CmcApplication.class, args);
    }

    /**
     * 初始化系统配置
     */
    private static void initSystemConfig() {
        System.setProperty("log.root", "ALL,CONSOLE,info,error,DEBUG");
        System.setProperty("service.id", "dapp-cmc-server");
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
