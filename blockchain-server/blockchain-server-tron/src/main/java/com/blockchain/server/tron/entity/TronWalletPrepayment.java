package com.blockchain.server.tron.entity;

import com.blockchain.common.base.entity.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * WallletPrepayment 数据传输类
 *
 * @version 1.0
 * @date 2018年12月5日10:05:13
 */
@Table(name = "dapp_tron_wallet_prepayment")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TronWalletPrepayment extends BaseModel {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "app_id")
    private String appId;
    @Column(name = "app_secret")
    private String appSecret;
    @Column(name = "open_id")
    private String openId;
    @Column(name = "trade_no")
    private String tradeNo;
    @Column(name = "notify_url")
    private String notifyUrl;
    @Column(name = "coin_name")
    private String coinName;
    @Column(name = "coin_address")
    private String coinAddress;
    @Column(name = "amount")
    private String amount;
    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "sign")
    private String sign;

}