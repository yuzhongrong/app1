package com.blockchain.server.sysconf.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 关于我们信息表
 */
@Data
public class ContactUsQueryConditionDTO implements Serializable{

    private Integer showStatus;
    private String contactValue;
    private Date beginTime;//开始时间
    private Date endTime;//结束时间

}
