package com.blockchain.server.otc.mapper;

import com.blockchain.server.otc.entity.Appeal;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * AppealMapper 数据访问类
 *
 * @version 1.0
 * @date 2019-04-18 15:54:26
 */
@Repository
public interface AppealMapper extends Mapper<Appeal> {

    /***
     * 根据订单流水号查询申诉信息
     * @param orderNumber
     * @return
     */
    Appeal selectByOrderNumber(@Param("orderNumber") String orderNumber);

}