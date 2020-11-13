package com.blockchain.server.teth.dto;

import lombok.Data;

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