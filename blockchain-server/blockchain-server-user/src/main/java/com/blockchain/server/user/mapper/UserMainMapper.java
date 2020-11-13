package com.blockchain.server.user.mapper;

import com.blockchain.server.user.entity.UserMain;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @date 2019-02-21 13:37:18
 * @version 1.0
 */
@Repository
public interface UserMainMapper extends Mapper<UserMain> {
    UserMain selectByMobilePhone(@Param("mobilePhone") String mobilePhone);

    List<UserMain> listByIds(@Param("ids")String[] ids);
}