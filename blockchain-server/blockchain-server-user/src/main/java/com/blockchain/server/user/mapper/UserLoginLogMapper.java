package com.blockchain.server.user.mapper;

import com.blockchain.server.user.dto.UserLoginLogDto;
import com.blockchain.server.user.entity.UserLoginLog;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @date 2019-02-21 13:37:18
 * @version 1.0
 */
@Repository
public interface UserLoginLogMapper extends Mapper<UserLoginLog> {
    /**
     * 查询用户登录日志
     * @param userId
     * @return
     */
    List<UserLoginLogDto> listUserLoginLogByUserId(@Param("userId") String userId);
}