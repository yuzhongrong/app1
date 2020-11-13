package com.blockchain.server.sysconf.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewsDTO {

    private String id;
    private String title;
    private String content;
    private String url;
    private Date createTime;
}
