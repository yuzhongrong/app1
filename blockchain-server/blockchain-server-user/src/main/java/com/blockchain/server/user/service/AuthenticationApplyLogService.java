package com.blockchain.server.user.service;

import com.blockchain.server.user.entity.AuthenticationApplyLog;

public interface AuthenticationApplyLogService {


    Integer insert(AuthenticationApplyLog authenticationApplyLog);

    AuthenticationApplyLog selectRemarkByUserId(String type, String userId);

    /**
     * 是否是高级
     * @param userId
     */
    void isHightAuth(String userId);

    /**
     * 是否是初级的
     * @param userId
     */
    void isPrimarytAuth(String userId);


    /**
     * 发送消息
     * @param  phone 手机
     * @param describe  内容 xxx 失败
     */
    void sendInform(String phone ,String describe);

}
