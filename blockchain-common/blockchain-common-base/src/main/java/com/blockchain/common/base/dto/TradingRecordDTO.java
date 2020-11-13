package com.blockchain.common.base.dto;

import com.blockchain.common.base.entity.BaseModel;
import lombok.Data;

import java.math.BigDecimal;

/**
 * TradingRecord 数据传输类
 *
 * @version 1.0
 * @date 2019-02-16 15:08:15
 */
@Data
public class TradingRecordDTO extends BaseModel {
    private String id;
    private String makerId;
    private String takerId;
    private BigDecimal makerPrice;
    private BigDecimal takerPrice;
    private BigDecimal tradingNum;
    private String tradingType;
    private String coinName;
    private String unitName;
    private java.util.Date createTime;

}