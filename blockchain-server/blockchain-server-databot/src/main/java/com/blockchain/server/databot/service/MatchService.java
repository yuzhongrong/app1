package com.blockchain.server.databot.service;

import com.blockchain.server.databot.dto.rpc.PublishOrderDTO;

public interface MatchService {

    /***
     * 撮合机器人撮合的业务逻辑
     * @param matchUserId
     * @param bymatch
     * @return
     */
    boolean match(String matchUserId, PublishOrderDTO bymatch);
}
