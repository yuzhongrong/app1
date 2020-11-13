package com.blockchain.server.user.dto;

import com.blockchain.common.base.entity.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserReplyDTO extends BaseModel {

    private String content;

    private String suggestion;

    private Date createTime;

    private Date usTime;

}