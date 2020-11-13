package com.blockchain.server.user.service.impl;

import com.blockchain.common.base.util.ExceptionPreconditionUtils;
import com.blockchain.server.user.common.constants.sql.AuthenticationApplyConstant;
import com.blockchain.server.user.common.constants.sql.UserAuthenticationConstant;
import com.blockchain.server.user.common.constants.sql.UserInfoConstant;
import com.blockchain.server.user.common.enums.UserEnums;
import com.blockchain.server.user.common.exceprion.UserException;
import com.blockchain.server.user.common.utils.FileUploadHelper;
import com.blockchain.server.user.common.utils.JavaSmsApi;
import com.blockchain.server.user.common.utils.SendSmsgCode;
import com.blockchain.server.user.entity.*;
import com.blockchain.server.user.mapper.AuthenticationApplyMapper;
import com.blockchain.server.user.mapper.HighAuthenticationApplyMapper;
import com.blockchain.server.user.mapper.UserAuthenticationMapper;
import com.blockchain.server.user.service.AuthenticationApplyLogService;
import com.blockchain.server.user.service.AuthenticationApplyService;
import com.blockchain.server.user.service.UserInfoService;
import com.blockchain.server.user.service.UserMainService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @author huangxl
 * @create 2019-02-26 14:26
 */
@Service
public class AuthenticationApplyServiceImpl implements AuthenticationApplyService {

    private static final Logger LOG = LoggerFactory.getLogger(AuthenticationApplyServiceImpl.class);

    @Autowired
    private UserAuthenticationMapper userAuthenticationMapper;

    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private AuthenticationApplyMapper authenticationApplyMapper;
    @Autowired
    private HighAuthenticationApplyMapper highAuthenticationApplyMapper;
    @Autowired
    private AuthenticationApplyLogService authenticationApplyLogService;

    @Autowired
    private UserMainService userMainService ;


    @Value("${baidu.notifyThePhone}")
    private String notifyThePhone;


    @Override
    public String selectHighRemarkByUserId(String userId) {
        AuthenticationApplyLog authenticationApplyLog = authenticationApplyLogService.selectRemarkByUserId(UserAuthenticationConstant.HIGH,userId);
        return authenticationApplyLog!=null?authenticationApplyLog.getRemark():null;
    }

    @Override
    public String selectLowRemarkByUserId(String userId) {
        AuthenticationApplyLog authenticationApplyLog = authenticationApplyLogService.selectRemarkByUserId(UserAuthenticationConstant.LOW,userId);
        return authenticationApplyLog!=null?authenticationApplyLog.getRemark():null;
    }

    @Override
    @Transactional
    public void insertBasicAuth(String userId, String realName, String idNumber, String type, String imgs,boolean mark) {

        ExceptionPreconditionUtils.notEmpty(userId, realName, idNumber, imgs);
        String authStatus = AuthenticationApplyConstant.STATUS_YES;
        String lowAuth= UserInfoConstant.STATUS_LOW_AUTH_YES;
        if(!mark ){
            FileUploadHelper.verifyFileNameList(imgs, 2);//初级认证需要身份证正反面，手持身份证照片
            authStatus = AuthenticationApplyConstant.STATUS_WAIT;
            lowAuth= UserInfoConstant.STATUS_LOW_AUTH_WAIT;
        }
        if (StringUtils.isEmpty(type)) {
            type = AuthenticationApplyConstant.TYPE_DEFAULT;
        }

        UserInfo userInfo = userInfoService.selectByUserId(userId);
        if (userInfo.getLowAuth().equals(UserInfoConstant.STATUS_LOW_AUTH_WAIT)) {
            //已经是等待审核状态
            throw new UserException(UserEnums.AUTH_WAIT);
        }
        if (userInfo.getLowAuth().equals(UserInfoConstant.STATUS_LOW_AUTH_YES)) {
            //已经是认证状态
            throw new UserException(UserEnums.AUTH_YES);
        }

        Date now = new Date();
        String[] filePath = imgs.split(",");


        AuthenticationApply apply = new AuthenticationApply();
        apply.setId(UUID.randomUUID().toString());
        apply.setUserId(userId);
        apply.setCreateTime(now);
        apply.setFileUrl1(filePath[0]);
        apply.setFileUrl2(filePath[1]);
        // apply.setFileUrl3(filePath[2]);
        apply.setIdNumber(idNumber);
        apply.setRealName(realName);
        apply.setType(type);
        apply.setModifyTime(now);
        apply.setStatus(authStatus);
        authenticationApplyMapper.insert(apply);

        userInfo.setLowAuth(lowAuth);
        userInfo.setModifyTime(now);
        userInfoService.updateUserInfo(userInfo);

  /*      if(!mark){
            //代表非国内用户，通知短信

            //发送短信验证
            UserMain user = userMainService.selectById(userId);
            String   text = MessageFormat.format(SendSmsgCode.AUTHENTICATION, user.getMobilePhone(),"  ","发起初级认证");
            try {
                LOG.info("身份验证发送通知："+text);
                JavaSmsApi.sendSms(SendSmsgCode.apikey, text, notifyThePhone);
            }catch (IOException ex){
                ex.printStackTrace();
            }
        }*/

    }
    @Override
    @Transactional
    public void insertHighAuth(String userId, String img,boolean mark) {
        ExceptionPreconditionUtils.notEmpty(userId, img);
        FileUploadHelper.verifyFileName(img);
        UserInfo userInfo = userInfoService.selectByUserId(userId);
        if (!userInfo.getLowAuth().equals(UserInfoConstant.STATUS_LOW_AUTH_YES)) {
            //提交高级认证前必须经过初级认证
            throw new UserException(UserEnums.AUTH_BASIC_BEFORE);
        }
        if (userInfo.getHighAuth().equals(UserInfoConstant.STATUS_HIGHT_AUTH_WAIT)) {
            //已经是等待审核状态
            throw new UserException(UserEnums.AUTH_WAIT);
        }
        if (userInfo.getHighAuth().equals(UserInfoConstant.STATUS_HIGHT_AUTH_YES)) {
            //已经是认证状态
            throw new UserException(UserEnums.AUTH_YES);
        }
        String authStatus = AuthenticationApplyConstant.STATUS_YES;
        String highAuth= UserInfoConstant.STATUS_HIGHT_AUTH_YES;
        Date now = new Date();
        if(!mark ){
            authStatus = AuthenticationApplyConstant.STATUS_WAIT;
            highAuth= UserInfoConstant.STATUS_LOW_AUTH_WAIT;
        }else{
            UserAuthentication example = new UserAuthentication();
            example.setUserId(userId);
            UserAuthentication userAuthentication = userAuthenticationMapper.selectOne(example);
            // 审核通过修改数据并插入审核记录表
            userAuthentication.setFileUrl4(img);
            userAuthentication.setCreateTime(now);
            userAuthenticationMapper.updateByPrimaryKeySelective(userAuthentication);

        }

        HighAuthenticationApply apply = new HighAuthenticationApply();
        apply.setId(UUID.randomUUID().toString());
        apply.setCreateTime(now);
        apply.setFileUrl(img);
        apply.setModifyTime(now);
        apply.setStatus(authStatus);
        apply.setUserId(userId);
        highAuthenticationApplyMapper.insert(apply);
        userInfo.setHighAuth(highAuth);
        userInfo.setModifyTime(now);
        userInfoService.updateUserInfo(userInfo);

/*        if(!mark){
            //代表非国内用户，通知短信
            //发送短信验证
            UserMain user = userMainService.selectById(userId);
            String   text = MessageFormat.format(SendSmsgCode.AUTHENTICATION, user.getMobilePhone(),"  ","发起高级认证");
            try {
                LOG.info("身份验证发送通知："+text);
                JavaSmsApi.sendSms(SendSmsgCode.apikey, text, notifyThePhone);
            }catch (IOException ex){
                ex.printStackTrace();
            }
        }*/


    }

    /**
     * 判断用户认证状态接口
     * @param userId
     * @param authenticationType
     * @return
     */
    @Override
    public String judgeAuthentication(String userId, String authenticationType) {
        if (UserAuthenticationConstant.LOW.equalsIgnoreCase(authenticationType))
            return authenticationApplyMapper.judgeAuthentication(userId);
        else if (UserAuthenticationConstant.HIGH.equalsIgnoreCase(authenticationType))
            return highAuthenticationApplyMapper.judgeAuthentication(userId);
        return null;
    }
}
