package com.blockchain.server.imjg.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("用户实体")
public class JgUserInfoDTO {

    @ApiModelProperty("用户ID")
    private String id;//用户id

    @ApiModelProperty("手机号")
    private String mobilePhone;//手机号

    @ApiModelProperty("昵称")
    private String nickName;//昵称

    @ApiModelProperty("头像")
    private String avatar;//头像

    public JgUserInfoDTO(UserDTO userDTO){

        if(userDTO != null){
            this.id = userDTO.getId();
            this.mobilePhone = userDTO.getMobilePhone();
            this.nickName = userDTO.getNickName();
            this.avatar = userDTO.getAvatar();
        }
    }

}
