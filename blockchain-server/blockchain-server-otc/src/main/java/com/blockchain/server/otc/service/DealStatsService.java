package com.blockchain.server.otc.service;

import com.blockchain.server.otc.entity.DealStats;

public interface DealStatsService {
    /***
     * 如果不存在交易统计记录，新增一条
     * @param userId
     * @return
     */
    int insertIsNotExist(String userId);

    /***
     * 根据userId查询用户成交记录
     * @param userId
     * @return
     */
    DealStats selectByUserId(String userId);

    /***
     * 更新广告交易次数
     * @param userId
     * @return
     */
    int updateAdTransNum(String userId);

    /***
     * 更新广告成交次数
     * @param userId
     * @return
     */
    int updateAdMarkNum(String userId);

    /***
     * 更新卖单成交次数
     * @param userId
     * @return
     */
    int updateOrderSellNum(String userId);

    /***
     * 更新买单成交次数
     * @param userId
     * @return
     */
    int updateOrderBuyNum(String userId);
}
