package com.blockchain.server.tron.mapper;

import com.blockchain.server.tron.entity.TronApply;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * ApplyMapper 数据访问类
 * @date 2018-11-05 15:10:47
 * @version 1.0
 */
@Repository
public interface TronApplyMapper extends Mapper<TronApply> {
}