package com.codingapi.tx.springcloud.listener;

import com.codingapi.tx.listener.service.InitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;


@Component
public class ServerListener implements ApplicationListener<ApplicationEvent> {

    private Logger logger = LoggerFactory.getLogger(ServerListener.class);

    private int serverPort;
    @Value("${server.port}")
    private String port;

    @Autowired
    private InitService initService;

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        //logger.info("onApplicationEvent -> onApplicationEvent. ");
        this.serverPort = Integer.parseInt(port);//event.getEmbeddedServletContainer().getPort();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                // 若连接不上txmanager start()方法将阻塞
                initService.start();
            }
        });
        thread.setName("TxInit-thread");
        thread.start();
    }

    public int getPort() {
        return this.serverPort;
    }
    
    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }
}
