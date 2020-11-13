package com.blockchain.server.user.service.impl;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.server.user.common.constants.sql.UserInfoConstant;
import com.blockchain.server.user.common.enums.UserEnums;
import com.blockchain.server.user.common.exceprion.UserException;
import com.blockchain.server.user.common.utils.JavaSmsApi;
import com.blockchain.server.user.common.utils.SendSmsgCode;
import com.blockchain.server.user.entity.AuthenticationApply;
import com.blockchain.server.user.entity.AuthenticationApplyLog;
import com.blockchain.server.user.entity.UserInfo;
import com.blockchain.server.user.mapper.AuthenticationApplyLogMapper;
import com.blockchain.server.user.mapper.AuthenticationApplyMapper;
import com.blockchain.server.user.service.AuthenticationApplyLogService;
import com.blockchain.server.user.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

@Service
public class AuthenticationApplyLogServiceImpl implements AuthenticationApplyLogService {


    @Autowired
    private AuthenticationApplyLogMapper authenticationApplyLogMapper;


    @Autowired
    private UserInfoService userInfoService;


    @Override
    public Integer insert(AuthenticationApplyLog authenticationApplyLog) {
        return authenticationApplyLogMapper.insert(authenticationApplyLog);
    }

    @Override
    public AuthenticationApplyLog selectRemarkByUserId(String type, String userId) {
        return authenticationApplyLogMapper.selectRemarkByUserId(type,userId);
    }

    @Override
    public void isHightAuth(String userId) {

        UserInfo userInfo = userInfoService.selectByUserId(userId);

        if (userInfo != null) {
            if (!UserInfoConstant.STATUS_HIGHT_AUTH_YES.equals(userInfo.getHighAuth())) {
                throw new UserException(UserEnums.TRANSACTION_NOT_PASS_HIGH_AUTH);
            }
        }
        //代表没有完成高认证 。抛出异常提示
        throw new UserException(UserEnums.TRANSACTION_NOT_PASS_HIGH_AUTH);

    }

    @Override
    public void isPrimarytAuth(String userId) {

        UserInfo userInfo = userInfoService.selectByUserId(userId);
        if (userInfo != null) {
            if (!UserInfoConstant.STATUS_HIGHT_AUTH_YES.equals(userInfo.getLowAuth())) {
                throw new UserException(UserEnums.TRANSACTION_NOT_PASS_LOW_AUTH);
            }
        }
        //代表没有完成初级认证 。抛出异常提示
        throw new UserException(UserEnums.TRANSACTION_NOT_PASS_LOW_AUTH);

    }

    @Override
    public void sendInform(String phone , String describe) {

        String   text = MessageFormat.format(SendSmsgCode.withdrawal, describe,"示，","失败！");
        try {
            JavaSmsApi.sendSms(SendSmsgCode.apikey, text, phone);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
