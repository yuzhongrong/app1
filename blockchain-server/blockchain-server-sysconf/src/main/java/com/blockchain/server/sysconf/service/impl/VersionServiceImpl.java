package com.blockchain.server.sysconf.service.impl;

import com.blockchain.common.base.enums.BaseResultEnums;
import com.blockchain.common.base.exception.BaseException;
import com.blockchain.server.sysconf.common.constants.VersionConstant;
import com.blockchain.server.sysconf.entity.Version;
import com.blockchain.server.sysconf.mapper.VersionMapper;
import com.blockchain.server.sysconf.service.VersionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class VersionServiceImpl implements VersionService {

    @Autowired
    private VersionMapper versionMapper;

    @Override
    public Version findNewVersion(String device) {
        if (StringUtils.isBlank(device)) {
            throw new BaseException(BaseResultEnums.PARAMS_ERROR);
        }
        return versionMapper.findNewVersion(device);
    }

    @Override
    public List<Version> listAll(String device) {
        return versionMapper.listAll(device);
    }

    @Override
    public Map<String, Version> findNewVersionAll() {
        Version versionIos = versionMapper.findNewVersion(VersionConstant.SYSTEMTYPE_IOS);
        Version versionAndroid = versionMapper.findNewVersion(VersionConstant.SYSTEMTYPE_ANDROID);
        Map<String, Version> VersionMap = new HashMap();
        VersionMap.put(VersionConstant.SYSTEMTYPE_IOS, versionIos);
        VersionMap.put(VersionConstant.SYSTEMTYPE_ANDROID, versionAndroid);
        return VersionMap;
    }





}
