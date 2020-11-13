package com.blockchain.server.user.entity;

import com.blockchain.common.base.entity.BaseModel;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * AuthenticationApplyLog
 *
 * @version 1.0
 * @date 2019-02-21 13:37:18
 */
@Table(name = "pc_u_authentication_apply_log")
@Data
public class AuthenticationApplyLog extends BaseModel {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "ip_address")
    private String ipAddress;
    @Column(name = "sys_user_id")
    private String SysUserId;
    @Column(name = "user_id")
    private String userId;
    @Column(name = "apply_id")
    private String applyId;
    @Column(name = "type")
    private String type;
    @Column(name = "apply_result")
    private String applyResult;
    @Column(name = "remark")
    private String remark;
    @Column(name = "create_time")
    private java.util.Date createTime;

}