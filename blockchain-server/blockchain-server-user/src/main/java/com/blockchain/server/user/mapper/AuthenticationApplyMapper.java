package com.blockchain.server.user.mapper;

import com.blockchain.server.user.entity.AuthenticationApply;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * AppUAuthenticationApplyMapper 数据访问类
 * @date 2019-02-21 13:37:17
 * @version 1.0
 */
@Repository
public interface AuthenticationApplyMapper extends Mapper<AuthenticationApply> {

    /**
     * 查询用户初级认证状态
     * @param userId
     * @return
     */
    String judgeAuthentication(@Param("userId") String userId);
}