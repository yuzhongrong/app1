package com.blockchain.server.user.service.impl;

import com.blockchain.common.base.enums.BaseResultEnums;
import com.blockchain.common.base.exception.BaseException;
import com.blockchain.common.base.util.ExceptionPreconditionUtils;
import com.blockchain.server.user.common.constants.other.RedisConstant;
import com.blockchain.server.user.common.constants.other.StringFormatConstant;
import com.blockchain.server.user.common.constants.sql.UserOptConstant;
import com.blockchain.server.user.common.enums.SmsCountEnum;
import com.blockchain.server.user.common.enums.UserEnums;
import com.blockchain.server.user.common.exceprion.UserException;
import com.blockchain.server.user.common.utils.CheckUtils;
import com.blockchain.server.user.common.utils.FileUploadHelper;
import com.blockchain.server.user.common.utils.GoogleAuthenticatorUtils;
import com.blockchain.server.user.entity.UserInfo;
import com.blockchain.server.user.mapper.UserInfoMapper;
import com.blockchain.server.user.service.UserInfoService;
import com.blockchain.server.user.service.UserOptLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * @author huangxl
 * @create 2019-02-23 18:23
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private UserOptLogService userOptLogService;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    @Transactional
    public void saveUser(String userId, String email, boolean hasRelation) {
        ExceptionPreconditionUtils.notEmpty(userId);
        UserInfo userInfo = new UserInfo();
        userInfo.setId(UUID.randomUUID().toString());
        userInfo.setUserId(userId);
        if (CheckUtils.checkEmail(email)){
            userInfo.setEmail(email);

        }
        Date now = new Date();
        userInfo.setCreateTime(now);
        userInfo.setModifyTime(now);
        if (hasRelation) {
            int number = 10 + new Random().nextInt(89);//保证一定生成两位数的数值
            userInfo.setRandomNumber(number);
        }
        userInfoMapper.insertSelective(userInfo);
    }

    @Override
    public UserInfo selectByIncrCode(Integer incrCode) {
        if (incrCode == null) {
            throw new BaseException(BaseResultEnums.PARAMS_ERROR);
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setIncrCode(incrCode);
        UserInfo user = userInfoMapper.selectOne(userInfo);
        return user;
    }

    @Override
    public void handleBindGoogleAuthenticator(String userId, String key, Long code) {
        ExceptionPreconditionUtils.notEmpty(userId, key, code);
        boolean pass = GoogleAuthenticatorUtils.check_code(key, code, System.currentTimeMillis());
        if (!pass) {
            throw new UserException(UserEnums.GOOGLE_AUTH_FAIL);//校验失败
        }
        //查询用户信息
        UserInfo userInfo = userInfoMapper.selectByUserId(userId);
        if (userInfo.getGoogleAuth() != null) {
            throw new UserException(UserEnums.GOOGLE_SECRET_KEY_EXIST);//重复绑定
        }
        //将安全码设置到用户信息中
        userInfo.setGoogleAuth(key);
        userInfo.setModifyTime(new Date());
        userInfoMapper.updateByPrimaryKey(userInfo);

        //保存用户操作信息
        userOptLogService.saveUserOptLog(userId, UserOptConstant.TYPE_BIND_GOOGLE_AUTHENTICATOR, StringFormatConstant.getUserBindGoogleAuthenticator(userId));
    }

    @Override
    public UserInfo selectByUserId(String userId) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(userId);
        UserInfo user = userInfoMapper.selectOne(userInfo);
        return user;
    }

    @Override
    public void updateUserInfo(UserInfo userInfo) {
        userInfoMapper.updateByPrimaryKeySelective(userInfo);
    }

    @Override
    @Transactional
    public void handleBindEmail(String userId, String email, String code) {
        ExceptionPreconditionUtils.notEmpty(userId, email, code);
        boolean check = CheckUtils.checkEmail(email);
        if (!check) {
            throw new UserException(UserEnums.EMAIL_FORMAT_ERROR);
        }
        String key = RedisConstant.getSmsHashKey(SmsCountEnum.SMS_COUNT_BIND.getKey(),email);
        Object cache = redisTemplate.opsForValue().get(key);
        if (cache != null && cache.toString().equals(code)) {
            //检查邮箱是否已被绑定
            checkRepeatEmail(email);

            UserInfo userInfo = selectByUserId(userId);
            if (userInfo.getEmail() != null) {
                throw new UserException(UserEnums.EMAIL_BIND_REPEAT);
            }
            userInfo.setEmail(email);
            userInfo.setModifyTime(new Date());
            userInfoMapper.updateByPrimaryKey(userInfo);//绑定邮箱

            //保存用户操作信息
            userOptLogService.saveUserOptLog(userId, UserOptConstant.TYPE_BIND_EMAIL, StringFormatConstant.getUserBindEmail(userId));

            redisTemplate.delete(key);
        } else {
            throw new UserException(UserEnums.EMAIL_VERIFY_FAIL);
        }
    }

    @Override
    @Transactional
    public void updateUserHeadImg(String userId, String img) {
        ExceptionPreconditionUtils.notEmpty(userId, img);
        FileUploadHelper.verifyFileName(img);

        UserInfo userInfo = userInfoMapper.selectByUserId(userId);
        userInfo.setAvatar(img);
        userInfo.setModifyTime(new Date());

        userInfoMapper.updateByPrimaryKeySelective(userInfo);
    }

    @Override
    public void checkRepeatEmail(String email) {
        //校验邮箱是否已被绑定
        UserInfo exist = new UserInfo();
        exist.setEmail(email);
        List<UserInfo> existUser = userInfoMapper.select(exist);
        if (existUser != null && existUser.size() > 0) {
            throw new UserException(UserEnums.EMAIL_EXIST);
        }
    }
}
