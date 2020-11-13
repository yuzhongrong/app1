package com.blockchain.server.user.service;

/**
 * 用户黑|白名单服务
 *
 * @author huangxl
 * @create 2019-02-23 15:20
 */
public interface UserListService {
    boolean checkUserByUserIdAndType(String listType, String userId, String type);
}
