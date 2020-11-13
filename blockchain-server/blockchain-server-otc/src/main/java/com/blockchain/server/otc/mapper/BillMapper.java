package com.blockchain.server.otc.mapper;

import com.blockchain.server.otc.entity.Bill;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * BillMapper 数据访问类
 *
 * @version 1.0
 * @date 2019-04-15 10:37:31
 */
@Repository
public interface BillMapper extends Mapper<Bill> {
}