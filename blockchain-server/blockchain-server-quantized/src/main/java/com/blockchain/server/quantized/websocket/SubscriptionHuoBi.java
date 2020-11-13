package com.blockchain.server.quantized.websocket;

import com.blockchain.server.quantized.service.OrderService;
import com.blockchain.server.quantized.service.SubscriptionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @author: Liusd
 * @create: 2019-04-19 17:52
 * <p>
 * 订阅火币
 **/
@Component
public class SubscriptionHuoBi implements ApplicationRunner {

    private static final Logger LOG = LoggerFactory.getLogger(SubscriptionHuoBi.class);

    @Autowired
    private SubscriptionService subscriptionService;


    @Override
    public void run(ApplicationArguments args) {
        //延迟5s执行方法。
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                    //订阅交易对
                    System.out.println("订阅开始!");
                    subscriptionService.subscribeAll();
                    //订阅历史订单数据，防止服务器停止的时候火币订单发生了变化
                    subscriptionService.initOrder();
                    System.out.println("订阅结束!");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    LOG.error("订阅发生异常---->>>",e);
                }



                //订阅行情
//              subQuote(option);
            }
        });
        t.start();
    }

}
