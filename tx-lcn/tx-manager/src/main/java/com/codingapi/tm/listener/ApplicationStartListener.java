package com.codingapi.tm.listener;

import com.codingapi.tm.Constants;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * create by lorne on 2017/8/7
 */
@Component
public class ApplicationStartListener implements ApplicationListener<ApplicationEvent> {


    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        //TODO Spring boot 2.0.0没有EmbeddedServletContainerInitializedEvent 此处写死
        //int serverPort = event.getEmbeddedServletContainer().getPort();
        String ip = getIp();
        Constants.address = ip+":"+4545;
    }



    private String getIp(){
        String host = null;
        try {
            host = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return host;
    }
}
