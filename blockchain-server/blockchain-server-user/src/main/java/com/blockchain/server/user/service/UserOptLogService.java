package com.blockchain.server.user.service;

import com.blockchain.server.user.entity.UserOptLog;

/**
 * @author huangxl
 * @create 2019-02-24 11:17
 */
public interface UserOptLogService {
    /**
     * 保存用户操作记录
     */
    int insertUserOpt(UserOptLog userOptLog);

    /**
     * 保存用户操作记录
     */
    int saveUserOptLog(String userId, String optType, String content);
}
