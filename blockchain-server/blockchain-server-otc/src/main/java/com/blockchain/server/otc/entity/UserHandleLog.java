package com.blockchain.server.otc.entity;

import com.blockchain.common.base.entity.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * UserHandleLog 数据传输类
 *
 * @version 1.0
 * @date 2019-04-15 10:37:32
 */
@Table(name = "otc_user_handle_log")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserHandleLog extends BaseModel {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "user_id")
    private String userId;
    @Column(name = "handle_number")
    private String handleNumber;
    @Column(name = "handle_type")
    private String handleType;
    @Column(name = "handle_number_type")
    private String handleNumberType;
    @Column(name = "create_time")
    private java.util.Date createTime;

}