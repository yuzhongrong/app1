package com.blockchain.server.otc.dto.ad;

import com.blockchain.common.base.dto.BaseDTO;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

@Data
public class ListAdDTO extends BaseDTO {
    private String id;
    private String adNumber;
    private String userId;
    private String username;
    private String nickname;
    private String userImg;
    private Integer adTransNum;
    private Integer adMarkNum;
    private BigDecimal price;
    private BigDecimal totalNum;
    private BigDecimal lastNum;
    private String coinName;
    private String unitName;
    private BigDecimal minLimit;
    private BigDecimal maxLimit;
    private String adPay;
    private String adType;
    private String adRemark;
    private java.util.Date createTime;
    private java.util.Date modifyTime;
}
