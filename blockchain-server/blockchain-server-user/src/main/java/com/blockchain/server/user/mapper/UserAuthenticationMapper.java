package com.blockchain.server.user.mapper;

import com.blockchain.server.user.entity.UserAuthentication;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * AppUUserAuthenticationMapper 数据访问类
 * @date 2019-02-21 13:37:18
 * @version 1.0
 */
@Repository
public interface UserAuthenticationMapper extends Mapper<UserAuthentication> {
}