package com.blockchain.server.user.mapper;

import com.blockchain.server.user.entity.UserOptLog;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * AppUUserOptLogMapper 数据访问类
 * @date 2019-02-21 13:37:18
 * @version 1.0
 */
@Repository
public interface UserOptLogMapper extends Mapper<UserOptLog> {
}