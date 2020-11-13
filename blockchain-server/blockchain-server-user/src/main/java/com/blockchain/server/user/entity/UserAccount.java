package com.blockchain.server.user.entity;

import com.blockchain.common.base.entity.BaseModel;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "dapp_u_user_login_account")
@Data
public class UserAccount extends BaseModel {
    @Id
    @Column(name = "id")
    private String id;

    /**
     * 用户id
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 登陆账户号
     */
    @Column(name = "account_name")
    private String accountName;

    /**
     * 账户类型，TEL电话号码，EMAIL电子邮件...
     */
    @Column(name = "account_type")
    private String accountType;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Long createTime;

    /**
     * 修改时间
     */
    @Column(name = "modify_time")
    private Long modifyTime;

}