package com.blockchain.server.base.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tk.mybatis.spring.mapper.MapperScannerConfigurer;

@Configuration
public class TkMybatisConf {
    private static final String MAPPER_PACKAGE = "com.blockchain.server.*.mapper";

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer(){
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setBasePackage(MAPPER_PACKAGE);
        return mapperScannerConfigurer;
    }
}
