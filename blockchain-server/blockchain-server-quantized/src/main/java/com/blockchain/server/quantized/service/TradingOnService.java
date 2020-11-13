package com.blockchain.server.quantized.service;

import com.blockchain.server.quantized.entity.TradingOn;

import java.util.List;

/**
 * @author: Liusd
 * @create: 2019-04-18 16:28
 **/
public interface TradingOnService {

    List<TradingOn> list(String state);

    TradingOn selectOne(String coinName, String unitName);

    TradingOn selectByKey(String id);

    Boolean checkTradingOnIsOpen(String coinName, String unitName);
}
