package com.blockchain.server.cmc.dto;

import com.blockchain.common.base.entity.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * BtcBlockNumberDTO 数据传输类
 *
 * @version 1.0
 * @date 2019-02-16 15:08:16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BtcBlockNumberDTO extends BaseModel {
    private Integer blockNumber;
    private java.util.Date createTime;
    private java.util.Date updateTime;
    private String status;

}