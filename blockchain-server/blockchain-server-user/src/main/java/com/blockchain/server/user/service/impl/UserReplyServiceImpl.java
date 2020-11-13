package com.blockchain.server.user.service.impl;

import com.blockchain.server.user.dto.UserReplyDTO;
import com.blockchain.server.user.mapper.UserReplyMapper;
import com.blockchain.server.user.service.UserReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserReplyServiceImpl implements UserReplyService {

    @Autowired
    private UserReplyMapper userReplyMapper;


    @Override
    public List<UserReplyDTO> listMine(String userOpenId) {
        return userReplyMapper.listMine(userOpenId);
    }

}
