package com.blockchain.server.user.mapper;

import com.blockchain.server.user.entity.UserList;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * AppUUserListMapper 数据访问类
 * @date 2019-02-21 13:37:18
 * @version 1.0
 */
@Repository
public interface UserListMapper extends Mapper<UserList> {
}