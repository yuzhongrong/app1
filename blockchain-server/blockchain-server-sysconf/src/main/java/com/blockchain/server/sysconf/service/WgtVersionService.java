package com.blockchain.server.sysconf.service;


import com.blockchain.server.sysconf.entity.WgtVersion;

import java.util.List;

public interface WgtVersionService {
    /**
     * 根据查询最新补丁版本信息
     * @return
     */
    WgtVersion findNewWgtVersion();
    /**
     * 查询所有版本版本信息列表
     * @return
     */
    List<WgtVersion> listAll();

}
