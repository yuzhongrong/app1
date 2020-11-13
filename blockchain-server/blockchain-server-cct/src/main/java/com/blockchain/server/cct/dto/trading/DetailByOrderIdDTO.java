package com.blockchain.server.cct.dto.trading;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/***
 * 根据发布id查询成交记录列表
 */

@Data
public class DetailByOrderIdDTO {
    private String id;
    private String recordId;
    private BigDecimal unitPrice;
    private BigDecimal tradingNum;
    private BigDecimal serviceCharge;
    private String tradingType;
    @JsonFormat(pattern = "HH:mm MM/dd")
    private Date createTime;
}
