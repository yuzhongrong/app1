package com.blockchain.server.otc.service;

import com.blockchain.server.otc.entity.MarketApply;

public interface MarketApplyService {

    /***
     * 申请市商或申请取消市商
     * @param userId
     * @param applyType
     */
    void applyMarket(String userId, String applyType);

    /***
     * 根据用户id和申请类型查询
     * @param userId
     * @param applyType
     * @param status
     * @return
     */
    MarketApply getByUserIdAndApplyTypeAndStatus(String userId, String applyType, String status);
}
