package com.blockchain.common.base.dto;

import com.blockchain.common.base.entity.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TxForOreDTO extends BaseModel {
    private String userId;
    private String txType;
    private String tokenSymbol;
    private BigDecimal tokenAmount;
    private BigDecimal fee;
    private java.util.Date txDate;

}