package com.blockchain.server.imjg.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel("用户实体")
public class UserDTO {
    @ApiModelProperty("用户ID")
    private String id;//用户id

    @ApiModelProperty("手机号")
    private String mobilePhone;//手机号

    @ApiModelProperty("昵称")
    private String nickName;//昵称

    @ApiModelProperty("头像")
    private String avatar;//头像

    @ApiModelProperty("email")
    private String email;//email

    @ApiModelProperty("低级认证")
    private String lowAuth;//低级认证

    @ApiModelProperty("高级认证")
    private String highAuth;//高级认证

    @ApiModelProperty("用户等级")
    private String grade;//用户等级

    @ApiModelProperty("是否设置了登录密码")
    private boolean hasPassword;//是否设置了登录密码

    @ApiModelProperty("token信息")
    private String token;//token信息

    @ApiModelProperty("邀请码")
    private String invitationCode;//邀请码

    @ApiModelProperty("谷歌验证器")
    private boolean bindGoogleAuth;//谷歌验证器
}