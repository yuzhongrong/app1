package com.blockchain.server.otc.entity;

import com.blockchain.common.base.entity.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * AppealHandleLog 数据传输类
 *
 * @version 1.0
 * @date 2019-04-18 15:54:26
 */
@Table(name = "otc_appeal_handle_log")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppealHandleLog extends BaseModel {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "order_number")
    private String orderNubmer;
    @Column(name = "sys_user_id")
    private String sysUserId;
    @Column(name = "ip_address")
    private String ipAddress;
    @Column(name = "after_status")
    private String afterStatus;
    @Column(name = "remark")
    private String remark;
    @Column(name = "create_time")
    private java.util.Date createTime;

}