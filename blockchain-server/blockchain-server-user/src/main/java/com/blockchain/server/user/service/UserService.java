package com.blockchain.server.user.service;

import java.util.Set;

public interface UserService {

    /***
     * 检查用户高级认证和黑名单
     * 不通过认证或存在黑名单则抛出异常
     *
     * @param userId
     * @return
     */
    void hasHighAuthAndUserList(String userId);

    /***
     * 检查用户初级认证和黑名单
     * 不通过认证或存在黑名单则抛出异常
     *
     * @param userId
     * @return
     */
    void hasLowAuthAndUserList(String userId);

    /**
     * 获取用户认证状态
     */
    Boolean getUserAuthentication(String userId);

}
