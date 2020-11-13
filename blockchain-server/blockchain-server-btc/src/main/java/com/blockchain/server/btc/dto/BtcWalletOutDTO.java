package com.blockchain.server.btc.dto;

import com.blockchain.common.base.entity.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * BtcWalletOutDTO 数据传输类
 *
 * @version 1.0
 * @date 2019-02-16 15:08:16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BtcWalletOutDTO extends BaseModel {
    private String id;
    private String addr;
    private Integer tokenId;
    private String tokenSymbol;
    private String privateKey;
    private String password;
    private String remark;

}