package com.blockchain.server.user.mapper;

import com.blockchain.server.user.entity.Config;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author huangxl
 * @create 2019-02-25 17:51
 */
@Repository
public interface ConfigMapper extends Mapper<Config> {
}
