package com.blockchain.server.sysconf.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 首页广告轮播表 app_ad_slider
 *
 * @author hugq
 * @date 2018-09-03
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdSliderDto implements Serializable {
    /**
     * 主键
     */
    private Integer id;
    /**
     * 图片路径
     */
    private String imgPath;
    /**
     * 序号
     */
    private Integer serialNumber;
    /**
     * 广告状态：0-隐藏，1-显示
     */
    private String status;

}
