package com.blockchain.server.otc.common.listener;

import com.blockchain.server.otc.redis.OrderCache;
import com.blockchain.server.otc.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RedisReceiver {

    @Autowired
    private OrderService orderService;

    /**
     * redisKey过期后，会通过反射调用此方法
     *
     * @param message
     */
    public void receiveMessage(String message) {
        //订单前缀key
        String newOrderKey = OrderCache.getNewOrderPre();
        //判断Key的前缀，如果是订单key的前缀进行撤单
        if (message.startsWith(newOrderKey)) {
            //获取订单id
            String orderId = message.substring(newOrderKey.length());
            //撤销订单
            orderService.autoCancelOrder(orderId);
        }
    }

}
