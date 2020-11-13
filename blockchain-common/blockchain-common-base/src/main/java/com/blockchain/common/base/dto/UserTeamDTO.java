package com.blockchain.common.base.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 用户基本信息
 */
@Data
public class UserTeamDTO extends BaseDTO {
    private String userId; //id
    private String mobilePhone; //手机号
    private String nickName; //昵称
    private String avatar; //头像
    private String lowAuth; //低级认证
    private String highAuth; //高级认证
    private String createTime; //注册时间
    private String teamNum; //社区人数
    private BigDecimal coinAmount; //社区人数
}