package com.blockchain.server.otc.entity;

import com.blockchain.common.base.entity.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * AppealImg 数据传输类
 *
 * @version 1.0
 * @date 2019-04-19 17:14:29
 */
@Table(name = "otc_appeal_img")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppealImg extends BaseModel {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "appeal_detail_id")
    private String appealDetailId;
    @Column(name = "appeal_img_url")
    private String appealImgUrl;
    @Column(name = "create_time")
    private java.util.Date createTime;

}