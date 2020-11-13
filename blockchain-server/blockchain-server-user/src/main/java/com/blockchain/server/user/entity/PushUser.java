package com.blockchain.server.user.entity;

import com.blockchain.common.base.entity.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * User 数据传输类
 *
 * @version 1.0
 * @date 2019-06-18 17:27:35
 */
@Table(name = "push_user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PushUser extends BaseModel {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "user_id")
    private String userId;
    @Column(name = "client_id")
    private String clientId;
    @Column(name = "language")
    private String language;
    @Column(name = "create_time")
    private java.util.Date createTime;
    @Column(name = "modify_time")
    private java.util.Date modifyTime;

}