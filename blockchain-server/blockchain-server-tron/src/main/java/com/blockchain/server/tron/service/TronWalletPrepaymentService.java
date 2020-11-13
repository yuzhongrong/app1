package com.blockchain.server.tron.service;


import com.blockchain.server.tron.dto.PrepaymentInsertInDTO;
import com.blockchain.server.tron.dto.PrepaymentInsertOutDTO;
import com.blockchain.server.tron.entity.TronWalletPrepayment;

public interface TronWalletPrepaymentService {

    /**
     * 根据ID 查询 预支付信息
     *
     * @param id 预支付ID
     * @return WalletPrepayment 预支付信息
     */
    TronWalletPrepayment findById(String id);

    /**
     * 根据traderNo（商户单号） 查询 订单信息
     *
     * @param appId   应用ID
     * @param tradeNo 商户单号
     * @return
     */
    TronWalletPrepayment findByTradeNo(String appId, String tradeNo);

    /**
     * 根据{appid}应用标识,{tradeNo}商户单号
     *
     * @param appId   应用标识
     * @param tradeNo 商户单号
     * @return 1——存在，0——不存在
     */
    int findByAppIdAndTradeNoLimit(String appId, String tradeNo);

    /**
     * 插入一条预支付信息
     *
     * @param inParams 接收的数据信息
     * @return PrepaymentInsertOutDTO   返回数据
     */
    PrepaymentInsertOutDTO insert(PrepaymentInsertInDTO inParams);
}
