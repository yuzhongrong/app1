package com.blockchain.server.user.mapper;

import com.blockchain.server.user.entity.UserAccount;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface UserAccountMapper extends Mapper<UserAccount> {

    /**
     * 通过登录账号获取用户id
     */
    String selectUserId(@Param("accountName") String accountName);

}