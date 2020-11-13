package com.blockchain.server.user.service.impl;

import com.blockchain.common.base.util.ExceptionPreconditionUtils;
import com.blockchain.common.base.util.HttpRequestUtil;
import com.blockchain.common.base.util.MD5Utils;
import com.blockchain.server.user.common.constants.other.StringFormatConstant;
import com.blockchain.server.user.common.constants.sql.UserListConstant;
import com.blockchain.server.user.common.constants.sql.UserLoginLogConstant;
import com.blockchain.server.user.common.constants.sql.UserOptConstant;
import com.blockchain.server.user.common.enums.UserEnums;
import com.blockchain.server.user.common.exceprion.UserException;
import com.blockchain.server.user.common.utils.CheckUtils;
import com.blockchain.server.user.entity.UserLogin;
import com.blockchain.server.user.entity.UserMain;
import com.blockchain.server.user.entity.UserOptLog;
import com.blockchain.server.user.mapper.UserLoginMapper;
import com.blockchain.server.user.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

/**
 * @author huangxl
 * @data 2019/2/21 15:18
 */
@Service
public class UserLoginServiceImpl implements UserLoginService {
    @Autowired
    private UserLoginMapper userLoginMapper;
    @Autowired
    private UserLoginLogService userLoginLogService;
    @Autowired
    private UserListService userListService;
    @Autowired
    private UserOptLogService userOptLogService;
    @Autowired
    private UserMainService userMainService;


    @Override
    public UserMain handleLoginByPassword(String tel, String password) {
        ExceptionPreconditionUtils.notEmpty(tel, password);
        //校验用户是否有权限登录
        UserMain userMain = checkUserLoginPermission(tel, password);
        return userMain;
    }

    @Override
    public int insertEntity(String userId, String password) {
        UserLogin userLogin = new UserLogin();
        userLogin.setId(UUID.randomUUID().toString());
        userLogin.setUserId(userId);
        if (password != null && password.trim().length() > 0) {
            password = password.trim();
            if (!CheckUtils.checkPassword(password)) {
                throw new UserException(UserEnums.USER_PASSWORD_ERROR_FORMAT);
            }
            userLogin.setPassword(MD5Utils.MD5(password));
        }
        userLogin.setCreateTime(new Date());
        userLogin.setModifyTime(new Date());
        return userLoginMapper.insert(userLogin);
    }

    @Override
    public UserMain handleLoginByPhoneCode(String tel) {
        ExceptionPreconditionUtils.notEmpty(tel);
        return checkUserLoginPermission(tel, null);//检查是否有登录权限
    }

    @Override
    @Transactional
    public void insertPassword(String userId, String password) {
        ExceptionPreconditionUtils.notEmpty(userId, password);
        UserLogin userLogin = userLoginMapper.selectByUserId(userId);
        if (userLogin.getPassword() != null) {
            throw new UserException(UserEnums.PASSWORD_EXIST);
        }
        //验证校验码
        userLogin.setPassword(MD5Utils.MD5(password));
        userLogin.setModifyTime(new Date());
//        userLoginMapper.insert(userLogin);
        userLoginMapper.updateByPrimaryKey(userLogin);

        //保存用户操作信息
        userOptLogService.saveUserOptLog(userId, UserOptConstant.TYPE_SET_LOGIN_PASSWORD, StringFormatConstant.getUserBindLoginPassword(userId));
    }

    @Override
    public void updatePassword(String userId, String password, String oldPassword) {
        ExceptionPreconditionUtils.notEmpty(userId, password, oldPassword);
        UserLogin userLogin = userLoginMapper.selectByUserId(userId);
        if (userLogin.getPassword() != null && !userLogin.getPassword().equals(MD5Utils.MD5(oldPassword))) {
            //如果已设置密码并且旧密码不匹配
            throw new UserException(UserEnums.PASSWORD_NOT_MATCH);
        }
        Date now = new Date();
        userLogin.setPassword(MD5Utils.MD5(password));
        userLogin.setModifyTime(now);
        userLoginMapper.updateByPrimaryKey(userLogin);

        //保存用户操作信息
        userOptLogService.saveUserOptLog(userId, UserOptConstant.TYPE_CHANGE_LOGIN_PASSWORD, StringFormatConstant.getUserChangeLoginPassword(userId));

    }

    @Override
    public void updatePassword(String userId, String password) {
        ExceptionPreconditionUtils.notEmpty(userId, password);
        UserLogin userLogin = userLoginMapper.selectByUserId(userId);
        Date now = new Date();
        userLogin.setPassword(MD5Utils.MD5(password));
        userLogin.setModifyTime(now);
        userLoginMapper.updateByPrimaryKey(userLogin);

        //保存用户操作信息
        userOptLogService.saveUserOptLog(userId, UserOptConstant.TYPE_CHANGE_LOGIN_PASSWORD, StringFormatConstant.getUserChangeLoginPassword(userId));
    }

    @Override
    public boolean selectUserPasswordIsExist(String userId) {
        ExceptionPreconditionUtils.notEmpty(userId);
        UserLogin userLogin = userLoginMapper.selectByUserId(userId);
        return userLogin != null && userLogin.getPassword() != null;
    }

    /**
     * 检查用户是否有登录权限
     * 如果设置了登录失败限制，则当天无法登录
     * 如果密码不为空，则会校验密码的一致性
     *
     * @param tel      手机号
     * @param password 密码，如果密码不为空，会校验密码的一致性
     * @return 用户信息
     */
    private UserMain checkUserLoginPermission(String tel, String password) {
        UserMain userMain = userMainService.selectByMobilePhone(tel);
        if (userMain == null || userMain.getId() == null) {
            throw new UserException(UserEnums.USER_NOT_EXISTS);
        }
        String userId = userMain.getId();
        //黑名单校验
        boolean blackRecord = userListService.checkUserByUserIdAndType(UserListConstant.LIST_TYPE_BLACK, userId, UserListConstant.TYPE_BAN_LOGIN);
        if (blackRecord) {
            throw new UserException(UserEnums.LOGIN_FORBIDDEN);
        }

        if (password != null) {
            //校验密码的一致性
            UserLogin userLogin = userLoginMapper.selectByUserId(userId);
            String pw = userLogin.getPassword();
            if (!MD5Utils.MD5(password).equals(pw)) {
                userLoginLogService.saveLoginLog(userId, UserLoginLogConstant.STATUS_FAIL);//保存登录失败信息
                throw new UserException(UserEnums.LOGIN_PASSWORD_ERROR);
            }
        }
        userLoginLogService.saveLoginLog(userId, UserLoginLogConstant.STATUS_SUCCESS);//保存登录信息
        return userMain;
    }
}
