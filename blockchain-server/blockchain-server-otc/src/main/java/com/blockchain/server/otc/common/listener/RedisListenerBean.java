package com.blockchain.server.otc.common.listener;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

@Configuration
public class RedisListenerBean {

    /***
     * '__keyevent@0__:expired' //@后面指定使用redis db库
     * redisKey失效触发的监听事件
     */
    @Value("${spring.redis.expired.listener}")
    private String redisExpiredListenerKey;

    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
                                            MessageListenerAdapter listenerAdapter) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        //监听过期的redisKey
        container.addMessageListener(listenerAdapter, new PatternTopic(redisExpiredListenerKey));
        //container可以添加多个messageListener
        return container;
    }

    /***
     * 消息监听器适配器，绑定消息处理，利用反射调用消息处理器的业务方法
     *
     * @param receiver
     * @return
     */
    @Bean
    MessageListenerAdapter listenerAdapter(RedisReceiver receiver) {
        //传入一个消息接收处理，利用反射调用receiveMessage方法
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }

    /***
     * 使用默认的工厂初始化redis操作模板
     * @param connectionFactory
     * @return
     */
    @Bean
    StringRedisTemplate template(RedisConnectionFactory connectionFactory) {
        return new StringRedisTemplate(connectionFactory);
    }

}
