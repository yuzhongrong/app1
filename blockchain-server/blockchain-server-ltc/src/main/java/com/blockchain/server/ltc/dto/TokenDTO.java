package com.blockchain.server.ltc.dto;

import com.blockchain.common.base.entity.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * TokenDTO 数据传输类
 *
 * @version 1.0
 * @date 2019-02-16 15:08:16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenDTO extends BaseModel {
    private Integer tokenId;
    private String tokenSymbol;
    private java.util.Date issueTime;
    private String totalSupply;
    private String totalCirculation;
    private String descr;

}