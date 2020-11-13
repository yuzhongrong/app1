package com.blockchain.server.cct.mapper;

import com.blockchain.server.cct.entity.Config;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * AppCctConfigMapper 数据访问类
 *
 * @version 1.0
 * @date 2019-02-15 17:38:07
 */
@Repository
public interface ConfigMapper extends Mapper<Config> {

    /***
     * 根据key名和状态查询
     * @param key
     * @param status
     * @return
     */
    Config selectByKeyAndStatus(@Param("key") String key, @Param("status") String status);

    /***
     * 根据key名查询配置信息
     * @param key
     * @return
     */
    Config selectByKey(@Param("key") String key);
}