package com.blockchain.server.otc.service;

import com.blockchain.server.otc.entity.MarketFreeze;

public interface MarketFreezeService {

    /***
     * 根据用户id查询保证金信息
     * @param userId
     * @return
     */
    MarketFreeze getByUserId(String userId);
}
