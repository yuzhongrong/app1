package com.blockchain.server.cmc.service.impl;

import com.blockchain.server.cmc.common.enums.ExceptionEnums;
import com.blockchain.server.cmc.common.exception.ServiceException;
import com.blockchain.server.cmc.entity.TransferAuditing;
import com.blockchain.server.cmc.mapper.TransferAuditingMapper;
import com.blockchain.server.cmc.service.TransferAuditingService;
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
