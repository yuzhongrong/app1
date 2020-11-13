package com.blockchain.server.sysconf.entity;

import com.blockchain.common.base.entity.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * AboutUsDto 数据传输类
 *
 * @version 1.0
 * @date 2018-08-27 14:24:47
 */
@Table(name = "conf_ad_slider")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdSlider extends BaseModel {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "img_path")
    private String imgPath;
    @Column(name = "serial_number")
    private Integer serialNumber;
    @Column(name = "status")
    private String status;

}