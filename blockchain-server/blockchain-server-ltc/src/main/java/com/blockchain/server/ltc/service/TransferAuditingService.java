package com.blockchain.server.ltc.service;

import com.blockchain.server.ltc.entity.TransferAuditing;

public interface TransferAuditingService {

    /**
     * 插入一条交易审核记录
     *
     * @param transferAuditing 审核记录
     * @return
     */
    void insertAuditing(TransferAuditing transferAuditing);


}
