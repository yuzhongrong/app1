package com.blockchain.server.currency.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * Created by xiancw on 2017/5/4.
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    /**
     * 配置消息代理
     *
     * @param registry
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        //设置客户端订阅号前缀，有两个：/topic(一对多)、/queue(一对一)
        registry.enableSimpleBroker("/topic", "/queue");
        // 设置Controller的@MessageMapping前缀，也就是客户端要向服务端发送消息,必须要有该前缀，这个前缀是任意字符串
        registry.setApplicationDestinationPrefixes("/app");
        //webSocket默认是使用"."来分割路径,spring是使用"/"
        registry.setPathMatcher(new AntPathMatcher("/"));
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        //设置端点，客户端使用"appApplicationUrl/websocket"进行连接
        //setAllowedOrigins的字符串如果不是"*",必须是"HTTP:"或者是"HTTPS:"开头
        registry.addEndpoint("/websocket").setAllowedOrigins("*").withSockJS();
    }

}
