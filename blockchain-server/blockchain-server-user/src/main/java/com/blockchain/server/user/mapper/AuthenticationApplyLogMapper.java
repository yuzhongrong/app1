package com.blockchain.server.user.mapper;

import com.blockchain.server.user.entity.AuthenticationApplyLog;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * AppUAuthenticationApplyMapper 数据访问类
 * @date 2019-02-21 13:37:17
 * @version 1.0
 */
@Repository
public interface AuthenticationApplyLogMapper extends Mapper<AuthenticationApplyLog> {

    AuthenticationApplyLog selectRemarkByUserId(@Param("type") String type, @Param("userId") String userId);
}