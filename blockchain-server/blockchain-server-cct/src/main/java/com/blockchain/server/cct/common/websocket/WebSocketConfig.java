package com.blockchain.server.cct.common.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        //注册STMOP端点，前端通过 域名：端口/websocket
        //setAllowedOrigins的字符串如果不是"*",必须是"HTTP:"或者是"HTTPS:"开头，目前尝试如果不加的话会出现跨域问题
        //withSockJS用于兼容SockJS
        registry.addEndpoint("/cct-websocket").setAllowedOrigins("*").withSockJS();
    }
}
