package com.blockchain.server.otc.service;

public interface AppealDetailService {

    /***
     * 新增申诉详情
     * @param userId
     * @param appealId
     * @param role
     * @param remark
     * @return
     */
    String insertAppealDetail(String userId, String appealId, String role, String remark);
}
