package com.blockchain.server.imjg.dto;

import com.blockchain.common.base.dto.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("资源路径")
public class JgResourceDTO extends BaseDTO {

    @ApiModelProperty("资源路径")
    private String url;


}
