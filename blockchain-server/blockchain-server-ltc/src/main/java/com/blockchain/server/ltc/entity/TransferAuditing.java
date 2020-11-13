package com.blockchain.server.ltc.entity;

import com.blockchain.common.base.entity.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TransferAuditingDTO 数据传输类
 *
 * @version 1.0
 * @date 2019-02-16 15:08:16
 */
@Table(name = "dapp_ltc_transfer_auditing")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferAuditing extends BaseModel {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "sys_user_id")
    private String sysUserId;
    @Column(name = "ip_addr")
    private String ipAddr;
    @Column(name = "transfer_id")
    private String transferId;
    @Column(name = "transfer_status")
    private String transferStatus;
    @Column(name = "create_time")
    private java.util.Date createTime;

}