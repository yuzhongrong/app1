package com.blockchain.server.tron.service;


import com.blockchain.server.tron.entity.TronInform;

import java.util.List;

/**
 * 请求通知——业务接口
 *
 * @author YH
 * @date 2018年12月5日10:21:27
 */
public interface TronInformService {
    /**
     * 插入一条请求通知数据
     *
     * @param paramsId   数据来源标识ID
     * @param paramsJson 请求参数
     * @param url        地址
     * @param informType 请求类型
     * @return int
     */
    int insert(String paramsId, String paramsJson, String url, String informType);

    /**
     * 根据请求类型查询未处理，且请求次数不超过{time}次的请求
     *
     * @param informType 请求类型
     * @param time       请求次数
     * @return int
     */
    List<TronInform> selectByInformTypePendingAll(String informType, int time);

    /**
     * 根据id 增加请求的次数+1
     *
     * @param id
     * @return
     */
    int updateTimeInRowLock(String id);


}
