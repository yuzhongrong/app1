package com.blockchain.server.tron.dto;

import lombok.Data;

/**
 * 用户基本信息
 */
@Data
public class UserBaseDTO {
    private String id;//用户id
    private String mobilePhone;//手机号
    private String nickName;//昵称
    private String avatar;//头像
    private String email;//email
    private String lowAuth;//低级认证
    private String highAuth;//高级认证
    private String grade;//用户等级


    private boolean hasPassword;//是否设置了登录密码
    private String token;//token信息
    private String invitationCode;//邀请码
    private boolean bindGoogleAuth;//谷歌验证器
}