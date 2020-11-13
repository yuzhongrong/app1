package com.blockchain.server.quantized.service;

import com.blockchain.server.quantized.entity.TradingOn;

/**
 * @author: Liusd
 * @create: 2019-05-07 10:22
 **/
public interface SubscriptionService {
    void initOrder();

    void subscribeAll();

    void subscribeTradingOn(TradingOn symbol);

    void unSubscribe(TradingOn trading);

    void push(TradingOn symbol);
}
