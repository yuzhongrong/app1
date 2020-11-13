package com.blockchain.server.imjg.service.impl;

import com.blockchain.server.imjg.entity.ImjgUser;
import com.blockchain.server.imjg.mapper.ImjgUserMapper;
import com.blockchain.server.imjg.service.ImjgUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImjgUserServiceImpl implements ImjgUserService {


    @Autowired
    private ImjgUserMapper imjgUserMapper;

    @Override
    public ImjgUser get(String userId) {
        return imjgUserMapper.selectByUserId(userId);
    }

    @Override
    public ImjgUser get(int id) {
        ImjgUser imjgUser = new ImjgUser();
        imjgUser.setId(id);
        return imjgUserMapper.selectOne(imjgUser);
    }

    @Override
    public int save(ImjgUser imjgUser) {
        imjgUserMapper.insertOne(imjgUser);
        return imjgUser.getId();
    }
}
