package com.blockchain.server.otc.service.impl;

import com.blockchain.server.otc.entity.MarketFreeze;
import com.blockchain.server.otc.mapper.MarketFreezeMapper;
import com.blockchain.server.otc.service.MarketFreezeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MarketFreezeServiceImpl implements MarketFreezeService {

    @Autowired
    private MarketFreezeMapper marketFreezeMapper;

    @Override
    public MarketFreeze getByUserId(String userId) {
        return marketFreezeMapper.selectByUserId(userId);
    }
}
