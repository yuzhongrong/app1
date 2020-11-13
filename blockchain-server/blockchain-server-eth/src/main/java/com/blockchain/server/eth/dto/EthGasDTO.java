package com.blockchain.server.eth.dto;

import com.blockchain.common.base.entity.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * EthServiceChargeDTO 数据传输类
 *
 * @version 1.0
 * @date 2019-02-16 15:44:06
 */
@Data
public class EthGasDTO  {
    private String gasPrice;
    private String gasTokenAddr;
    private String gasTokenSymbol;
    private String gasTokenName;

}