package com.blockchain.common.base.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 放到session里的用户
 */
@Data
public class SessionUserDTO extends BaseDTO{
    private String id;//用户id
    private String tel;//手机号
    private Long timestamp;//时间戳
}
