package com.blockchain.server.user.service.impl;

import com.blockchain.server.user.entity.UserList;
import com.blockchain.server.user.mapper.UserListMapper;
import com.blockchain.server.user.service.UserListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author huangxl
 * @create 2019-02-23 15:22
 */
@Service
public class UserListServiceImpl implements UserListService {
    @Autowired
    private UserListMapper userListMapper;

    @Override
    public boolean checkUserByUserIdAndType(String listType, String userId, String type) {
        UserList userList = new UserList();
        userList.setListType(listType);
        userList.setType(type);
        userList.setUserId(userId);
        int record = userListMapper.selectCount(userList);
        return record > 0;
    }
}
