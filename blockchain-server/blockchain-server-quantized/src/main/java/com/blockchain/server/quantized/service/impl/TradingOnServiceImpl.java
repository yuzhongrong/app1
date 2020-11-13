package com.blockchain.server.quantized.service.impl;

import com.blockchain.server.quantized.common.constant.QuantizedOrderInfoConstant;
import com.blockchain.server.quantized.common.constant.TradingOnConstant;
import com.blockchain.server.quantized.common.enums.QuantizedResultEnums;
import com.blockchain.server.quantized.common.exception.QuantizedException;
import com.blockchain.server.quantized.entity.TradingOn;
import com.blockchain.server.quantized.mapper.TradingOnMapper;
import com.blockchain.server.quantized.service.OrderService;
import com.blockchain.server.quantized.service.TradingOnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author: Liusd
 * @create: 2019-04-18 16:28
 **/
@Service
public class TradingOnServiceImpl implements TradingOnService {

    @Autowired
    private OrderService orderService;

    @Autowired
    private TradingOnMapper tradingOnMapper;

    @Override
    public List<TradingOn> list(String state) {
        if (state != null) {
            TradingOn trading = new TradingOn();
            trading.setState(state);
            return tradingOnMapper.select(trading);
        }
        return tradingOnMapper.selectAll();
    }

    @Override
    public TradingOn selectOne(String coinName, String unitName) {
        TradingOn trading = new TradingOn();
        trading.setCoinName(coinName.toLowerCase());
        trading.setUnitName(unitName.toLowerCase());
        return tradingOnMapper.selectOne(trading);
    }

    @Override
    public TradingOn selectByKey(String id) {
        return tradingOnMapper.selectByPrimaryKey(id);
    }

    @Override
    public Boolean checkTradingOnIsOpen(String coinName, String unitName) {
        TradingOn tradingOn = this.selectOne(coinName, unitName);
        if (tradingOn == null) {
            return false;
        }
        if (tradingOn.getState().equalsIgnoreCase(TradingOnConstant.STATE_SUBSCRIBE)) {
            return true;
        }
        return false;
    }
}
