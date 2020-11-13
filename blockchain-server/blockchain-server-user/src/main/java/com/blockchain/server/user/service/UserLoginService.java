package com.blockchain.server.user.service;


import com.blockchain.server.user.entity.UserMain;

/**
 * @author huangxl
 * @data 2019/2/21 15:17
 */
public interface UserLoginService {

    /**
     * 用户账号密码登录
     *
     * @param tel      用户手机号
     * @param password 密码
     */
    UserMain handleLoginByPassword(String tel, String password);

    /**
     * 插入记录信息
     *
     * @param userId   用户id
     * @param password 密码
     * @return
     */
    int insertEntity(String userId, String password);

    /**
     * 用户手机验证码登录
     *
     * @param tel
     * @return
     */
    UserMain handleLoginByPhoneCode(String tel);

    /**
     * 初次设置密码
     *
     * @param userId   用户id
     * @param password 密码
     */
    void insertPassword(String userId, String password);

    /**
     * 修改密码
     *
     * @param userId      用户id
     * @param password    新密码
     * @param oldPassword 旧密码
     */
    void updatePassword(String userId, String password, String oldPassword);

    /**
     * 修改密码
     *
     * @param userId      用户id
     * @param password    新密码
     */
    void updatePassword(String userId, String password);

    /**
     * 查询该用户是否设置密码
     * @param userId 用户id
     */
    boolean selectUserPasswordIsExist(String userId);
}
