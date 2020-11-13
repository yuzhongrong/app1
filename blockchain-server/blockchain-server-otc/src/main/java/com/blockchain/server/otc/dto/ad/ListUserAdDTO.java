package com.blockchain.server.otc.dto.ad;

import com.blockchain.common.base.dto.BaseDTO;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ListUserAdDTO extends BaseDTO {
    private String id;
    private String adNumber;
    private String userId;
    private BigDecimal price;
    private BigDecimal totalNum;
    private BigDecimal lastNum;
    private String coinName;
    private String unitName;
    private BigDecimal minLimit;
    private BigDecimal maxLimit;
    private String adPay;
    private String adType;
    private String adStatus;
    private String adRemark;
    private java.util.Date createTime;
    private java.util.Date modifyTime;

}