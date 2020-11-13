package com.blockchain.server.user.service;

import com.blockchain.server.user.dto.UserLoginLogDto;

import java.util.List;

/**
 * @author huangxl
 * @create 2019-02-23 14:43
 */
public interface UserLoginLogService {
    int saveLoginLog(String userId, String status);

    /**
     * 查询用户登录日志
     * @param userId
     * @return
     */
    List<UserLoginLogDto> listUserLoginLogByUserId(String userId);
}
