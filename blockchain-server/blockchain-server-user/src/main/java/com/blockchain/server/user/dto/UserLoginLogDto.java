package com.blockchain.server.user.dto;

import lombok.Data;

/**
 * @author Harvey
 * @date 2019/3/5 15:37
 * @user WIN10
 */
@Data
public class UserLoginLogDto {
    private String id;
    private String nickName;
    private String realName;
    private String mobilePhone;
    private String ipAddress;
    private String createTime;
    private String status;
}
