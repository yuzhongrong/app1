package com.blockchain.server.teth.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * YH
 * 2019年2月16日16:57:21
 */
@Data
@Configuration
@Component
@ConfigurationProperties(prefix = "ethconfig")
public class EthConfig {

    String emptyAddress;

    String RPC_URL;

    String os;

    String urlType;

    String url;

    String path;

}
