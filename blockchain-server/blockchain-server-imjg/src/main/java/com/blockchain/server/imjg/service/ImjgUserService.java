package com.blockchain.server.imjg.service;

import com.blockchain.server.imjg.entity.ImjgUser;

public interface ImjgUserService {

    /**
     * 根据系统userId获取极光账号
     * @param userId
     * @return
     */
    ImjgUser get(String userId);

    /**
     * 根据Id获取极光账号
     * @param id
     * @return
     */
    ImjgUser get(int id);

    /**
     * 保存一条系统账号和极光关联的信息
     * @param imjgUser
     * @return
     */
    int save(ImjgUser imjgUser);



}
