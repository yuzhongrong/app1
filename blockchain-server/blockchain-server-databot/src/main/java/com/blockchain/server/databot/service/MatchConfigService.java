package com.blockchain.server.databot.service;

import com.blockchain.server.databot.entity.MatchConfig;

import java.util.List;

public interface MatchConfigService {

    /***
     * 查询状态为可用的撮合配置
     * @return
     */
    MatchConfig getStatusIsY(String coinName, String unitName);
}
