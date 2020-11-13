package com.blockchain.server.currency.redis;


import org.apache.commons.lang3.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissonConfig {

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private String port;

    @Value("${spring.redis.password}")
    private String password;

    @Value("${spring.redis.database}")
    private int database;

    @Bean
    public RedissonClient getRedisson() {
        //redisson配置类
        Config config = new Config();
        //使用redisson单机模式，并设置相关配置
        SingleServerConfig singleServerConfig = config.useSingleServer();
        singleServerConfig.setAddress("redis://" + host + ":" + port).setDatabase(database);
        if (StringUtils.isNotBlank(password)) {
            singleServerConfig.setPassword(password);
        }
        //创建redisson客户端
        return Redisson.create(config);
    }
}
