package com.blockchain.server.user.service.impl;

import com.blockchain.common.base.util.HttpRequestUtil;
import com.blockchain.server.user.entity.UserOptLog;
import com.blockchain.server.user.mapper.UserOptLogMapper;
import com.blockchain.server.user.service.UserOptLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

/**
 * @author huangxl
 * @create 2019-02-24 11:18
 */
@Service
public class UserOptLogServiceImpl implements UserOptLogService {
    @Autowired
    private UserOptLogMapper userOptLogMapper;

    @Override
    @Transactional
    public int insertUserOpt(UserOptLog userOptLog) {
        return userOptLogMapper.insert(userOptLog);
    }

    @Override
    public int saveUserOptLog(String userId, String optType, String content) {
        UserOptLog userOptLog = new UserOptLog();
        userOptLog.setId(UUID.randomUUID().toString());
        userOptLog.setUserId(userId);
        userOptLog.setIpAddress(HttpRequestUtil.getIpAddr());
        userOptLog.setCreateTime(new Date());
        userOptLog.setOptType(optType);
        userOptLog.setContent(content);
        return userOptLogMapper.insert(userOptLog);
    }
}
