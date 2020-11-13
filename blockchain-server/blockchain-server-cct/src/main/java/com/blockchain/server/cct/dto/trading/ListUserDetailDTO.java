package com.blockchain.server.cct.dto.trading;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/***
 * 用户成交记录DTO
 */

@Data
public class ListUserDetailDTO {
    private String recordId; //成交记录id
    private String orderId; //订单id
    private BigDecimal dealPrice; //成交均价
    private BigDecimal dealNum; //成交数量
    private BigDecimal dealTurnover; //成交总额
    private BigDecimal dealCharge; //成交总手续费
    private BigDecimal entrustPrice; //委托单价
    private BigDecimal entrustNum; //委托数量
    private BigDecimal entrustTurnover; //委托交易额
    private String publishType; //发布类型
    private String orderType; //订单类型
    private String coinName; //基本货币
    private String unitName; //二级货币
    private String orderStatus; //订单状态
    @JsonFormat(pattern = "HH:mm MM/dd")
    private Date createTime;
}
