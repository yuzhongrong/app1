package com.blockchain.server.user.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name = "dapp_u_sms_count")
public class SmsCount {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "phone")
    private String phone;
    @Column(name = "sms_type")
    private String smsType;
    @Column(name = "sms_count")
    private Integer smsCount;
    @Column(name = "sms_date")
    private Date smsDate;
}
