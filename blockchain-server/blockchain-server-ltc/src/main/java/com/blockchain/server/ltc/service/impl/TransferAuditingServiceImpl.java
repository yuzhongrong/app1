package com.blockchain.server.ltc.service.impl;

import com.blockchain.server.ltc.common.enums.ExceptionEnums;
import com.blockchain.server.ltc.common.exception.ServiceException;
import com.blockchain.server.ltc.entity.TransferAuditing;
import com.blockchain.server.ltc.mapper.TransferAuditingMapper;
import com.blockchain.server.ltc.service.TransferAuditingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransferAuditingServiceImpl implements TransferAuditingService {
    @Autowired
    private TransferAuditingMapper transferAuditingMapper;

    @Override
    public void insertAuditing(TransferAuditing transferAuditing) {
        int countIa = transferAuditingMapper.insertSelective(transferAuditing);
        if (countIa != 1) {
            throw new ServiceException(ExceptionEnums.INSERT_AUDITING_ERROR);
        }
    }

}
