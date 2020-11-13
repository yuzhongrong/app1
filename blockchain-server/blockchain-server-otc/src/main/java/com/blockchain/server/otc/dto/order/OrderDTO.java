package com.blockchain.server.otc.dto.order;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderDTO {
    private String id;
    private String orderNumber;
    private String adId;
    private String coinName;
    private String unitName;
    private String buyUserId;
    private String buyStatus;
    private String buyUserName;
    private String buyNickName;
    private String buyAvatar;
    private String sellUserId;
    private String sellStatus;
    private String sellUserName;
    private String sellNickName;
    private String sellAvatar;
    private BigDecimal amount;
    private BigDecimal price;
    private BigDecimal turnover;
    private BigDecimal chargeRatio;
    private String orderType;
    private String orderStatus;
    private String orderPayType;
    private String role;
    private java.util.Date createTime;
    private java.util.Date modifyTime;
}
