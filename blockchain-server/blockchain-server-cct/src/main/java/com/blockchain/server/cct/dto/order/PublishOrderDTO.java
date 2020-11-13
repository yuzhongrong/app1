package com.blockchain.server.cct.dto.order;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class PublishOrderDTO implements Serializable {
    private String id;
    private String userId;
    private BigDecimal unitPrice;
    private BigDecimal totalNum;
    private BigDecimal lastNum;
    private BigDecimal totalTurnover;
    private BigDecimal lastTurnover;
    private String coinName;
    private String unitName;
    private String orderStatus;
    private String orderType;
    private String publishType;
    private int version;
    private java.util.Date createTime;
    private java.util.Date modifyTime;
}
