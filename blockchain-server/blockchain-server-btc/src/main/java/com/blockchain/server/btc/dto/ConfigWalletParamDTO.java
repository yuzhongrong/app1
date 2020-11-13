package com.blockchain.server.btc.dto;

import com.blockchain.common.base.entity.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * EthApplication 数据传输类
 *
 * @version 1.0
 * @date 2019-02-16 15:44:06
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConfigWalletParamDTO extends BaseModel {
    private String id;
    private String moduleType;
    private String paramName;
    private String paramValue;
    private String paramDescr;
    private Integer status;
}