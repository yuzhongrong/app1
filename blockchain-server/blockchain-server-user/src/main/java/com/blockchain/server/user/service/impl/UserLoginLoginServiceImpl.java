package com.blockchain.server.user.service.impl;

import com.blockchain.common.base.util.ExceptionPreconditionUtils;
import com.blockchain.common.base.util.HttpRequestUtil;
import com.blockchain.server.user.dto.UserLoginLogDto;
import com.blockchain.server.user.entity.UserLoginLog;
import com.blockchain.server.user.mapper.UserLoginLogMapper;
import com.blockchain.server.user.service.UserLoginLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author huangxl
 * @create 2019-02-23 14:43
 */
@Service
public class UserLoginLoginServiceImpl implements UserLoginLogService {

    @Autowired
    private UserLoginLogMapper userLoginLogMapper;
    @Override
    //使用非事务提交方式，防止登录失败跑出异常事务回滚后不插入登录失败记录
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public int saveLoginLog(String userId,String status) {
        UserLoginLog loinLog = new UserLoginLog();
        String ip = HttpRequestUtil.getIpAddr();
        loinLog.setId(UUID.randomUUID().toString());
        loinLog.setUserId(userId);
        loinLog.setStatus(status);
        loinLog.setCreateTime(new Date());
        loinLog.setIpAddress(ip);
        return userLoginLogMapper.insertSelective(loinLog);
    }

    /**
     * 查询用户登录日志
     * @param userId
     * @return
     */
    @Override
    public List<UserLoginLogDto> listUserLoginLogByUserId(String userId) {
        ExceptionPreconditionUtils.notEmpty(userId);
        return userLoginLogMapper.listUserLoginLogByUserId(userId);
    }
}
