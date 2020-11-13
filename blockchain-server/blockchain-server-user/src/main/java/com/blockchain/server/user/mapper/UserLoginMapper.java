package com.blockchain.server.user.mapper;

import com.blockchain.server.user.entity.UserLogin;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * @date 2019-02-21 13:37:18
 * @version 1.0
 */
@Repository
public interface UserLoginMapper extends Mapper<UserLogin> {

    UserLogin selectByUserId(@Param("userId") String userId);
}