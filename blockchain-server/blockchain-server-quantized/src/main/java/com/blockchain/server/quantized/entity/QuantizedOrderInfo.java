package com.blockchain.server.quantized.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "pc_quantized_order_info")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuantizedOrderInfo {

    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "order_id")
    private Long orderId;
    @Column(name = "symbol")
    private String symbol;
    @Column(name = "times")
    private Integer times;
    @Column(name = "status")
    private String status;
    @Column(name = "cct_id")
    private String cctId;
    @Column(name = "is_die")
    private Integer die;
    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "modify_time")
    private Date modifyTime;
}
