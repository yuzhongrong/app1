package com.blockchain.server.otc.mapper;

import com.blockchain.server.otc.entity.UserHandleLog;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * UserHandleLogMapper 数据访问类
 *
 * @version 1.0
 * @date 2019-04-15 10:37:32
 */
@Repository
public interface UserHandleLogMapper extends Mapper<UserHandleLog> {
}