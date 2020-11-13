package com.blockchain.server.otc.entity;

import com.blockchain.common.base.entity.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * AppealDetail 数据传输类
 *
 * @version 1.0
 * @date 2019-04-18 15:54:26
 */
@Table(name = "otc_appeal_detail")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppealDetail extends BaseModel {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "appeal_id")
    private String appealId;
    @Column(name = "user_id")
    private String userId;
    @Column(name = "appeal_role")
    private String appealRole;
    @Column(name = "remark")
    private String remark;
    @Column(name = "create_time")
    private java.util.Date createTime;

}