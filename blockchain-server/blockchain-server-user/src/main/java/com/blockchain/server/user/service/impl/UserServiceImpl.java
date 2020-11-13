package com.blockchain.server.user.service.impl;

import com.blockchain.server.user.common.constants.sql.GlobalConstant;
import com.blockchain.server.user.common.constants.sql.UserListConstant;
import com.blockchain.server.user.common.enums.UserEnums;
import com.blockchain.server.user.common.exceprion.UserException;
import com.blockchain.server.user.entity.UserInfo;
import com.blockchain.server.user.service.UserInfoService;
import com.blockchain.server.user.service.UserListService;
import com.blockchain.server.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private UserListService userListService;

    @Override
    public void hasHighAuthAndUserList(String userId) {
        //查询用户信息
        UserInfo userInfo = selectByUserIdAndCheckNull(userId);

        //高级认证状态
        boolean hasHighAuth = false;
        //是否已通过高级认证
        if (userInfo.getHighAuth().equals(GlobalConstant.STATUS_YES)) {
            hasHighAuth = true;
        }

        //用户是否在禁止交易黑名单中
        boolean banTransaction = userListService.checkUserByUserIdAndType(UserListConstant.LIST_TYPE_BLACK, userId, UserListConstant.TYPE_BAN_TRANSACTION);

        //没通过高级认证
        if (!hasHighAuth) {
            throw new UserException(UserEnums.TRANSACTION_NOT_PASS_HIGH_AUTH);
        }
        //黑名单禁止交易
        if (banTransaction) {
            throw new UserException(UserEnums.TRANSACTION_FORBIDDEN);
        }
    }

    @Override
    public void hasLowAuthAndUserList(String userId) {
        //查询用户信息
        UserInfo userInfo = selectByUserIdAndCheckNull(userId);

        //高级认证状态
        boolean hasLowAuth = false;
        //是否已通过高级认证
        if (userInfo.getLowAuth().equals(GlobalConstant.STATUS_YES)) {
            hasLowAuth = true;
        }

        //用户是否在禁止交易黑名单中
        boolean banTransaction = userListService.checkUserByUserIdAndType(UserListConstant.LIST_TYPE_BLACK, userId, UserListConstant.TYPE_BAN_TRANSACTION);

        //没通过高级认证
        if (!hasLowAuth) {
            throw new UserException(UserEnums.TRANSACTION_NOT_PASS_LOW_AUTH);
        }
        //黑名单禁止交易
        if (banTransaction) {
            throw new UserException(UserEnums.TRANSACTION_FORBIDDEN);
        }
    }

    @Override
    public Boolean getUserAuthentication(String userId) {
        //查询用户信息
        UserInfo userInfo = selectByUserIdAndCheckNull(userId);
        return userInfo.getLowAuth().equals(GlobalConstant.STATUS_YES) || userInfo.getHighAuth().equals(GlobalConstant.STATUS_YES);
    }


    /**
     * 查询用户信息并判空
     *
     * @param userId
     * @return
     */
    private UserInfo selectByUserIdAndCheckNull(String userId) {
        //查询用户信息
        UserInfo userInfo = userInfoService.selectByUserId(userId);
        if (userInfo == null) {
            throw new UserException(UserEnums.USER_NOT_EXISTS);
        }
        return userInfo;
    }
}
