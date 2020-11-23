package com.blockchain.server.cmc.service;

import com.blockchain.server.cmc.entity.BtcTransferAuditing;

public interface BtcTransferAuditingService {

    /**
     * 插入一条交易审核记录
     *
     * @param BtcTransferAuditing 审核记录
     * @return
     */
    void insertAuditing(BtcTransferAuditing BtcTransferAuditing);


}
