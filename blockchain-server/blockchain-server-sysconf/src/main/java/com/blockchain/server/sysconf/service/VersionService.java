package com.blockchain.server.sysconf.service;


import com.blockchain.server.sysconf.entity.Version;

import java.util.List;
import java.util.Map;

public interface VersionService {

    /**
     * 根据系统类型查询最新app版本信息
     *
     * @param device 设备型号
     * @return
     */
    Version findNewVersion(String device);

    /**
     * 查询所有app版本信息列表
     *
     * @param device
     * @return
     */
    List<Version> listAll(String device);

    /**
     * 查询所有系统最新的版本信息
     *
     * @return
     */
    Map<String, Version> findNewVersionAll();



}
