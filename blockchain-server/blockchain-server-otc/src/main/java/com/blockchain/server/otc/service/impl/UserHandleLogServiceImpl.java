package com.blockchain.server.otc.service.impl;

import com.blockchain.server.otc.entity.UserHandleLog;
import com.blockchain.server.otc.mapper.UserHandleLogMapper;
import com.blockchain.server.otc.service.UserHandleLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

@Service
public class UserHandleLogServiceImpl implements UserHandleLogService {

    @Autowired
    private UserHandleLogMapper userHandleLogMapper;

    @Override
    @Transactional
    public int insertUserHandleLog(String userId, String handleNumber, String handleType, String handleNumberType) {
        UserHandleLog userHandleLog = new UserHandleLog();
        Date now = new Date();
        userHandleLog.setId(UUID.randomUUID().toString());
        userHandleLog.setUserId(userId);
        userHandleLog.setHandleNumber(handleNumber);
        userHandleLog.setHandleType(handleType);
        userHandleLog.setHandleNumberType(handleNumberType);
        userHandleLog.setCreateTime(now);
        return userHandleLogMapper.insertSelective(userHandleLog);
    }
}
