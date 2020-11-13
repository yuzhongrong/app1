package com.blockchain.server.teth.mapper;

import com.blockchain.server.teth.entity.EthApplication;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * EthApplicationMapper 数据访问类
 * @date 2019-02-16 15:44:06
 * @version 1.0
 */
@Repository
public interface EthApplicationMapper extends Mapper<EthApplication> {
}