package com.blockchain.server.currency.websocket;

import org.springframework.messaging.simp.SimpMessagingTemplate;

/**
 * Created by HXL on 2017/5/8.
 */
public class WebSocketSendMsgUtils {
    private static final String BROKER = "/topic";

    /**
     * 行情更新消息
     *
     * @param template
     */
    public static void sendMarketMsg(SimpMessagingTemplate template, Object json) {
        template.convertAndSend(BROKER + "/market", json);
    }

    /**
     * 行情更新消息
     *
     * @param template
     */
    public static void sendHistoryMsg(SimpMessagingTemplate template,String currencyPair, Object json) {
        template.convertAndSend(BROKER + "/history/" + currencyPair, json);
    }

    /**
     * K线行情更新消息
     *
     * @param template
     */
    public static void sendMarketKMsg(SimpMessagingTemplate template,String currencyPair,String ktype, Object json) {
        template.convertAndSend(BROKER + "/marketk/" + currencyPair + "/" + ktype, json);
    }
}
