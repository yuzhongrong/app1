package com.blockchain.server.cct.dto.trading;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/***
 * 查询用户历史成交DTO
 */
@Data
public class ListUserHistoryDTO {
    private String recordId;
    private String orderId;
    private String makerId;
    private String takerId;
    private String userId;
    private String unitName;
    private String coinName;
    private BigDecimal unitPrice;
    private BigDecimal tradingNum;
    private BigDecimal totalAmount;
    private BigDecimal realAmount;
    private BigDecimal chargeRatio;
    private BigDecimal serviceCharge;
    private String tradingType;
    private String orderType;
    private Date createTime;
}
