package com.blockchain.server.base.conf;

import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

/**
 * @author huangxl
 * @create 2018-11-13 13:48
 */
@Configuration
@EnableEurekaClient //注册客户端
@EnableFeignClients(basePackages = "com.blockchain.server.*.feign")
@EnableCircuitBreaker //熔断器
@EnableDiscoveryClient //服务发现
public class SpringCloudConf {
}
