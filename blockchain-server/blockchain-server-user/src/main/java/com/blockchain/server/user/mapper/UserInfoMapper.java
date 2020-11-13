package com.blockchain.server.user.mapper;

import com.blockchain.server.user.entity.UserInfo;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * AppUUserInfoMapper 数据访问类
 * @date 2019-02-21 13:37:18
 * @version 1.0
 */
@Repository
public interface UserInfoMapper extends Mapper<UserInfo> {
    /**
     * 根据用户id查找用户信息
     * @param userId 用户id
     */
    UserInfo selectByUserId(String userId);
}