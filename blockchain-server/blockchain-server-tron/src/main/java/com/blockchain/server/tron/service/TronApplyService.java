package com.blockchain.server.tron.service;


import com.blockchain.server.tron.entity.TronApply;

/**
 * 应用信息——业务接口
 *
 * @author YH
 * @date 2018年12月5日10:21:27
 */
public interface TronApplyService {

    /**
     * 根据 appid 与 appSecret 查询应用信息表是否存在
     *
     * @param appid     应用标识
     * @param appSecret 安全码
     * @return 应用信息数据
     */
    TronApply findByAppidAndAppSecret(String appid, String appSecret);
}
