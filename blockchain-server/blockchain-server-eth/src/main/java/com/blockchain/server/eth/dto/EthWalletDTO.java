package com.blockchain.server.eth.dto;

import com.blockchain.common.base.entity.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * EthWallet 数据传输类
 *
 * @version 1.0
 * @date 2019-02-16 15:44:06
 */
@Data
public class EthWalletDTO {
    private String addr;
    private String tokenAddr;
    private String userOpenId;
    private String tokenSymbol;
    private int tokenDecimals;
    private String balance;
    private String freeBalance;
    private String freezeBalance;
    private String walletType;
    private java.util.Date createTime;
    private java.util.Date updateTime;
}