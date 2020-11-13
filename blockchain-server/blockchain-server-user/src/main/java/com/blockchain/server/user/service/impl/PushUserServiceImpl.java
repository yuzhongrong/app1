package com.blockchain.server.user.service.impl;

import com.blockchain.server.user.entity.PushUser;
import com.blockchain.server.user.mapper.PushUserMapper;
import com.blockchain.server.user.service.PushUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

@Service
public class PushUserServiceImpl implements PushUserService {
    @Autowired
    private PushUserMapper userMapper;

    //日志
    private static final Logger LOG = LoggerFactory.getLogger(PushUserServiceImpl.class);

    @Override
    public Integer insertOrUpadteUser(String userId, String clientId, String language) {
        //根据用户id查询
        PushUser pushUser = userMapper.selectByUserId(userId);
        //判断客户端id是否存在
        checkClientIdExist(clientId);
        //返回行数
        Integer row;
        //不等于空，走更新
        if (pushUser != null) {
            //更新
            row = updateUser(userId, clientId, language);
        } else {
            //等于空，走新增
            row = insertUser(userId, clientId, language);
        }

        return row;
    }

    @Override
    @Transactional
    public Integer insertUser(String userId, String clientId, String language) {
        //新增通知用户
        PushUser user = new PushUser();
        Date now = new Date();
        user.setId(UUID.randomUUID().toString());
        user.setUserId(userId);
        user.setClientId(clientId);
        user.setLanguage(language);
        user.setCreateTime(now);
        user.setModifyTime(now);
        int row = userMapper.insertSelective(user);
        //判断更新行数
        if (row != 1) {
            LOG.error("新增通知用户异常：user：{0}，更新行数：{1}", user, row);
        }
        return row;
    }

    @Override
    @Transactional
    public Integer updateUser(String userId, String clientId, String language) {
        int row = userMapper.updateUser(userId, clientId, language, new Date());
        //判断更新行数
        if (row != 1) {
            LOG.error("更新通知用户异常：userId：{0}，clientId：{1}，更新行数：{2}", userId, clientId, row);
        }
        return row;
    }

    @Override
    @Transactional
    public Integer updateUserLanguage(String userId, String language) {
        PushUser pushUser = userMapper.selectByUserId(userId);
        if (pushUser != null) {
            pushUser.setModifyTime(new Date());
            pushUser.setLanguage(language);
            return userMapper.updateByPrimaryKeySelective(pushUser);
        }
        return 0;
    }

    @Override
    public PushUser selectByUserId(String userId) {
        return userMapper.selectByUserId(userId);
    }

    @Override
    public PushUser selectByClientId(String clientId) {
        return userMapper.selectByClientId(clientId);
    }

    /***
     * 判断是否有重复的客户端ID
     * 如果有，将之前用户的客户端ID设置为空
     * @param clientId
     * @return
     */
    private int checkClientIdExist(String clientId) {
        //根据客户端id查询用户
        PushUser user = selectByClientId(clientId);
        //客户端ID已存在，将其设置为空，让后入的用户使用该客户端ID
        if (user != null) {
            //更新
            return userMapper.updateUser(user.getUserId(), null, null, new Date());
        }
        return 0;
    }
}
