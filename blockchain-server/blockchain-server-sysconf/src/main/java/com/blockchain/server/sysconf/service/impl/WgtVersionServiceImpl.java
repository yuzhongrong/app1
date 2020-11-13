package com.blockchain.server.sysconf.service.impl;


import com.blockchain.server.sysconf.entity.WgtVersion;
import com.blockchain.server.sysconf.mapper.WgtVersionMapper;
import com.blockchain.server.sysconf.service.WgtVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class WgtVersionServiceImpl implements WgtVersionService {
    @Autowired
    private WgtVersionMapper wgtVersionMapper;


    @Override
    public WgtVersion findNewWgtVersion() {
        return wgtVersionMapper.findNewWgtVersion();
    }

    @Override
    public List<WgtVersion> listAll() {
        return wgtVersionMapper.listAll();
    }

}
