package com.blockchain.server.tron.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * WalletTokentDTO 数据传输类（托管钱包代币）
 *
 * @version 1.0
 * @date 2018-11-05 15:10:47
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WalletPayTokentDTO {
    private String tokenAddress;        //	代币地址
    private String tokenName;        //	代币名称
    private String balance;        // 可用
    private Integer decimals;    //	代币小数位
    private String walletType;    //	代币小数位
}