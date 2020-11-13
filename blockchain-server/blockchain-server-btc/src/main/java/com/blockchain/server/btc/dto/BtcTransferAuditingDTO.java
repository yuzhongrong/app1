package com.blockchain.server.btc.dto;

import com.blockchain.common.base.entity.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * BtcTransferAuditingDTO 数据传输类
 *
 * @version 1.0
 * @date 2019-02-16 15:08:16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BtcTransferAuditingDTO extends BaseModel {
    private String id;
    private String sysUserId;
    private String ipAddr;
    private String transferId;
    private String transferStatus;
    private java.util.Date createTime;

}