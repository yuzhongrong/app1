package com.blockchain.server.cct.service;

import com.blockchain.server.cct.dto.config.ConfigDTO;
import com.blockchain.server.cct.entity.Config;

import java.util.List;

public interface ConfigService {

    /***
     * 查询可用的配置信息
     * @param key
     * @param status
     * @return
     */
    Config selectByKey(String key, String status);

    /***
     * 查询所有手续费配置
     * @return
     */
    List<ConfigDTO> listServiceCharge();
}
