package com.blockchain.server.otc.mapper;

import com.blockchain.server.otc.dto.appeal.AppealHandleLogDTO;
import com.blockchain.server.otc.entity.AppealHandleLog;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * AppealHandleLogMapper 数据访问类
 *
 * @version 1.0
 * @date 2019-04-18 15:54:26
 */
@Repository
public interface AppealHandleLogMapper extends Mapper<AppealHandleLog> {

    /***
     * 根据订单流水查询申诉处理日志
     * @param orderNumber
     * @return
     */
    AppealHandleLogDTO selectByOrderNumber(@Param("orderNumber") String orderNumber);
}