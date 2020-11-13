package com.blockchain.server.user.mapper;

import com.blockchain.server.user.entity.HighAuthenticationApply;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * AppUHighAuthenticationApplyMapper 数据访问类
 * @date 2019-02-21 13:37:18
 * @version 1.0
 */
@Repository
public interface HighAuthenticationApplyMapper extends Mapper<HighAuthenticationApply> {

    /**
     * 查询用户高级审核状态
     * @param userId
     * @return
     */
    String judgeAuthentication(@Param("userId") String userId);
}