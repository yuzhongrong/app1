package com.blockchain.server.sysconf.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name = "u_sms_count")
public class SmsCount {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "phone")
    private String phone;
    @Column(name = "sms_type")
    private Integer smsType;
    @Column(name = "sms_count")
    private Integer smsCount;
    @Column(name = "sms_date")
    private Date smsDate;
}
