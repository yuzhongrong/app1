package com.blockchain.server.user.service;

import com.blockchain.server.user.entity.UserInfo;

/**
 * @author huangxl
 * @create 2019-02-23 18:21
 */
public interface UserInfoService{

    /**
     * 保存用户信息
     * @param userId 用户id
     * @param email
     * @param hasRelation 是否有关系链信息
     */
    void saveUser(String userId, String email, boolean hasRelation);

    /**
     * 根据自增长id查询用户信息
     * @param incrCode 自增长id
     */
    UserInfo selectByIncrCode(Integer incrCode);

    /**
     * 绑定谷歌验证器
     * @param userId 用户id
     * @param key 谷歌验证器安全码
     * @param code
     */
    void handleBindGoogleAuthenticator(String userId, String key, Long code);

    /**
     * 根据用户id查询用户信息
     * @param userId 用户id
     */
    UserInfo selectByUserId(String userId);

    /**
     * 更新用户信息
     */
    void updateUserInfo(UserInfo userInfo);

    /**
     * 绑定邮箱地址
     * @param userId 用户id
     * @param email email
     * @param code 验证码
     */
    void handleBindEmail(String userId, String email, String code);

    /**
     * 更新用户头像信息
     * @param userId 用户id
     * @param img 头像地址
     */
    void updateUserHeadImg(String userId, String img);

    /**
     * 检查是否有重复邮箱
     * @param email 邮箱地址
     */
    void checkRepeatEmail(String email);
}
