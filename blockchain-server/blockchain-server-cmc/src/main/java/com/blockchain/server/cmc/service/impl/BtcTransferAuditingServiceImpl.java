package com.blockchain.server.cmc.service.impl;

import com.blockchain.server.cmc.common.enums.BtcEnums;
import com.blockchain.server.cmc.common.exception.BtcException;
import com.blockchain.server.cmc.entity.BtcTransferAuditing;
import com.blockchain.server.cmc.mapper.BtcTransferAuditingMapper;
import com.blockchain.server.cmc.service.BtcTransferAuditingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BtcTransferAuditingServiceImpl implements BtcTransferAuditingService {
    @Autowired
    private BtcTransferAuditingMapper btcTransferAuditingMapper;

    @Override
    public void insertAuditing(BtcTransferAuditing btcTransferAuditing) {
        int countIa = btcTransferAuditingMapper.insertSelective(btcTransferAuditing);
        if (countIa != 1) {
            throw new BtcException(BtcEnums.INSERT_AUDITING_ERROR);
        }
    }

}
