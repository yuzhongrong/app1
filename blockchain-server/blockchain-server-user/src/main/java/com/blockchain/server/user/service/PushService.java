package com.blockchain.server.user.service;

import com.blockchain.common.base.enums.PushEnums;

import java.util.Map;

public interface PushService {

    /***
     * 对单个用户推送消息
     * @param userId
     * @param pushType
     * @param payLoadMap
     */
    void pushToSingle(String userId, String pushType, Map<String, Object> payLoadMap);
}
