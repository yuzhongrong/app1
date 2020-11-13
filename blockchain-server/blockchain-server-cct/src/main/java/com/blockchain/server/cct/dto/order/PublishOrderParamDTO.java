package com.blockchain.server.cct.dto.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * PublishOrderService 发布订单参数传输类
 *
 * @version 1.0
 * @date 2018-11-06 11:02:51
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PublishOrderParamDTO implements Serializable {
    private String userId;//用户id
    private String pass;//密码
    private String unitName;//单位
    private String coinName;//币种
    private BigDecimal totalNum;//数量
    private BigDecimal unitPrice;//单价
    private BigDecimal turnover;//交易额
}


