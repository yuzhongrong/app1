package com.blockchain.server.user.service;

import com.blockchain.server.user.dto.UserBaseDTO;
import com.blockchain.server.user.entity.UserMain;

import java.util.List;

/**
 * @author huangxl
 * @create 2019-02-23 14:59
 */
public interface UserMainService {
    /**
     * 根据手机号查询用户信息
     *
     * @param tel 手机号
     */
    UserMain selectByMobilePhone(String tel);

    /**
     * 根据用户id查询用户主体信息
     *
     * @param id 用户id
     */
    UserMain selectById(String id);

    /**
     * 根据用户id查询用户主体信息
     *
     * @param ids 用户id集合
     */
    List<UserMain> selectInIds(String[] ids);

    /**
     * 注册接口
     *
     * @param tel               手机号
     * @param invitationCode    邀请码
     * @param internationalCode 国际标识
     * @param password          密码
     * @param nickName          昵称
     * @return
     */
    UserMain handleRegister(String tel, String invitationCode, String internationalCode, String password, String nickName);

    /**
     * 更新用户昵称
     *
     * @param userId 用户id
     * @param nickName 昵称
     */
    void updateNickName(String userId, String nickName);

    /**
     * 更新手机号信息
     *
     * @param userId 用户id
     * @param tel 手机号
     * @param internationalCode 国际标识码
     */
    void updateTel(String userId, String tel, String internationalCode);

    /**
     * 发送修改手机号验证码之前的业务逻辑处理
     * @param userId 当前登录的用户id
     * @param tel 要更换的手机号
     */
    void handleSendChangePhoneCodeBefore(String userId, String tel);

    /**
     * 查询用户主体信息
     * @param userId 用户id
     * @return
     */
    UserBaseDTO selectUserInfoById(String userId);
}
