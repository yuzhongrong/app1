package com.blockchain.server.imjg.dto;

import com.blockchain.common.base.dto.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("极光用户")
public class JgUserDTO extends BaseDTO {

    @ApiModelProperty("用户名登录的惟一的")
    private String username;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("应用APPkey")
    private String appkey;

    @ApiModelProperty("签名")
    private String signature;

    @ApiModelProperty("本系统的用户ID")
    private String userId;

    @ApiModelProperty("本系统的用户昵称")
    private String nickName;

    public JgUserDTO(){

    }
    public JgUserDTO(String username,String password){
        this.username = username;
        this.password = password;
    }
}
