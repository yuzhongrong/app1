package com.blockchain.server.otc.service;

import com.blockchain.server.otc.entity.MarketUser;

public interface MarketUserService {

    /***
     * 判断用户是否是市商
     * @param userId
     */
    void checkMarketUser(String userId);

    /***
     * 查询用户市商状态
     * @param userId
     * @return
     */
    String getMarketUserStatus(String userId);

    /***
     * 查询用户市商
     * @param userId
     * @return
     */
    MarketUser getMarketUserByUserId(String userId);

    /***
     * 更新用户市商
     * @param marketUser
     * @return
     */
    int updateByPrimaryKeySelective(MarketUser marketUser);
}
