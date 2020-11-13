package com.blockchain.server.otc.service;

import com.blockchain.server.otc.dto.appeal.AppealHandleLogDTO;

public interface AppealHandleLogService {

    /***
     * 新增申诉处理日志记录
     * @param orderNumber
     * @param sysUserId
     * @param afterStatus
     * @return
     */
    int insertAppealHandleLog(String orderNumber, String sysUserId, String ipAddress, String afterStatus, String remark);

    /***
     * 根据订单流水查询订单申诉情况
     * @param orderNumber
     * @return
     */
    AppealHandleLogDTO selectByOrderNumber(String orderNumber);
}
