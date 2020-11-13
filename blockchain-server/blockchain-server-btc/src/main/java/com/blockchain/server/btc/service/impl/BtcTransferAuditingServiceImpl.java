package com.blockchain.server.btc.service.impl;

import com.blockchain.server.btc.common.enums.BtcEnums;
import com.blockchain.server.btc.common.exception.BtcException;
import com.blockchain.server.btc.entity.BtcTransferAuditing;
import com.blockchain.server.btc.mapper.BtcTransferAuditingMapper;
import com.blockchain.server.btc.service.BtcTransferAuditingService;
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
