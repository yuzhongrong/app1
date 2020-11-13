package com.blockchain.server.imjg.dto;

import com.blockchain.common.base.dto.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@ApiModel("极光初始化参数值")
public class JgInitDTO extends BaseDTO {

    @ApiModelProperty("毫秒级时间戳")
    private long timestamp;

    @ApiModelProperty("签名")
    private String signature;

    @ApiModelProperty("随机字符串")
    private String random_str;

    @ApiModelProperty("极光账户的AppKey")
    private String appkey;
}
