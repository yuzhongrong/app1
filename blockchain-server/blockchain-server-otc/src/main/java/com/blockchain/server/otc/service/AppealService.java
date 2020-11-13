package com.blockchain.server.otc.service;

public interface AppealService {
    /**
     * 提交申诉
     *
     * @param userId
     * @param orderId
     * @param urls
     * @param remark
     * @return
     */
    void appeal(String userId, String orderId, String[] urls, String remark);

    /***
     * 更新申诉状态
     * @param orderNumber
     * @param appealStatus
     * @return
     */
    int updateAppeal(String orderNumber, String appealStatus);
}
