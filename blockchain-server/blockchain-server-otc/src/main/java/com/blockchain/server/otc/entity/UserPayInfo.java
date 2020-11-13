package com.blockchain.server.otc.entity;

import com.blockchain.common.base.entity.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * UserPayInfo 数据传输类
 *
 * @version 1.0
 * @date 2019-04-15 14:35:49
 */
@Table(name = "otc_user_pay_info")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPayInfo extends BaseModel {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "user_id")
    private String userId;
    @Column(name = "pay_type")
    private String payType;
    @Column(name = "account_info")
    private String accountInfo;
    @Column(name = "collection_code_url")
    private String collectionCodeUrl;
    @Column(name = "bank_number")
    private String bankNumber;
    @Column(name = "bank_user_name")
    private String bankUserName;
    @Column(name = "bank_type")
    private String bankType;
    @Column(name = "create_time")
    private java.util.Date createTime;
    @Column(name = "modify_time")
    private java.util.Date modifyTime;

}