package com.blockchain.server.quantized.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author: Liusd
 * @create: 2019-04-19 16:47
 * CREATE TABLE `pc_quantized_account` (
 *   `id` varchar(36) NOT NULL,
 *   `api_key` varchar(255) DEFAULT NULL,
 *   `secret_key` varchar(255) DEFAULT NULL,
 *   PRIMARY KEY (`id`)
 * ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 **/
@Table(name = "pc_quantized_account")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuantizedAccount {

    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "api_key")
    private String apiKey;
    @Column(name = "secret_key")
    private String secretKey;
    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "modify_time")
    private Date modifyTime;
}
