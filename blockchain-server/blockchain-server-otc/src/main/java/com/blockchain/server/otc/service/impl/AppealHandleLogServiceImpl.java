package com.blockchain.server.otc.service.impl;

import com.blockchain.server.otc.dto.appeal.AppealHandleLogDTO;
import com.blockchain.server.otc.entity.AppealHandleLog;
import com.blockchain.server.otc.mapper.AppealHandleLogMapper;
import com.blockchain.server.otc.service.AppealHandleLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

@Service
public class AppealHandleLogServiceImpl implements AppealHandleLogService {

    @Autowired
    private AppealHandleLogMapper appealHandleLogMapper;

    @Override
    @Transactional
    public int insertAppealHandleLog(String orderNumber, String sysUserId, String ipAddress, String afterStatus, String remark) {
        AppealHandleLog appealHandleLog = new AppealHandleLog();
        appealHandleLog.setId(UUID.randomUUID().toString());
        appealHandleLog.setOrderNubmer(orderNumber);
        appealHandleLog.setSysUserId(sysUserId);
        appealHandleLog.setIpAddress(ipAddress);
        appealHandleLog.setRemark(remark);
        appealHandleLog.setAfterStatus(afterStatus);
        appealHandleLog.setCreateTime(new Date());
        return appealHandleLogMapper.insertSelective(appealHandleLog);
    }

    @Override
    public AppealHandleLogDTO selectByOrderNumber(String orderNumber) {
        return appealHandleLogMapper.selectByOrderNumber(orderNumber);
    }
}
