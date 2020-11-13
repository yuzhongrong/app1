package com.blockchain.server.sysconf.dto;

import lombok.Data;

import java.util.Date;

/**
 * 获取文章列表的查询条件
 */
@Data
public class NewsQueryConditionDTO {

    private Integer type;//文章类型
    private String languages;//语种
    private String title;//标题
    private Date beginTime;//开始时间
    private Date endTime;//结束时间
}
