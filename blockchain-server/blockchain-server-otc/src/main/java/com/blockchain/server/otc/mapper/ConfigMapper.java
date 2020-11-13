package com.blockchain.server.otc.mapper;

import com.blockchain.server.otc.entity.Config;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * ConfigMapper 数据访问类
 *
 * @version 1.0
 * @date 2019-04-15 10:37:31
 */
@Repository
public interface ConfigMapper extends Mapper<Config> {

    /***
     * 根据key查询配置信息
     * @param key
     * @return
     */
    Config selectByKey(@Param("key") String key);
}