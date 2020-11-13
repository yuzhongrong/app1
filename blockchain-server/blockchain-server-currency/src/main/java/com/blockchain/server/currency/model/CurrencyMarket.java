package com.blockchain.server.currency.model;

import com.blockchain.common.base.entity.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Table(name = "dapp_currency_market")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyMarket extends BaseModel {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "currency_pair")
    private String currencyPair;
    @Column(name = "amount")
    private BigDecimal amount;
    @Column(name = "total")
    private BigDecimal total;
    @Column(name = "timestamp")
    private Long timestamp;


}