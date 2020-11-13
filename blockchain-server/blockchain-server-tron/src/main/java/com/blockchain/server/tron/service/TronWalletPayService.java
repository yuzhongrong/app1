package com.blockchain.server.tron.service;

import com.blockchain.server.tron.dto.*;

import java.util.List;

/**
 * 预支付接口——业务接口
 *
 * @author Harvey
 * @date 2019/3/23 11:33
 */
public interface TronWalletPayService {
    /**
     * 根据预支付id 查询用户id生成秘钥对
     *
     * @param id 预支付id
     * @return 公钥字符串
     */
    String getPublicById(String id);

    /**
     * 下单接口（预支付接口）
     *
     * @param inParams 接收的数据
     * @return 返回的数据
     */
    PrepaymentInsertOutDTO insertOrder(PrepaymentInsertInDTO inParams);

    /**
     * 支付接口
     *
     * @param id       预支付id
     * @param password 加密后的密码
     * @return
     */
    PayInsertOutDTO insertOrder(String openId, String id, String password);

    /**
     * 订单查询接口
     *
     * @param appId     应用ID
     * @param appSecret 应用安全码
     * @param tradeNo   商户单号
     * @return
     */
    PayQueryOrderDTO queryOrder(String appId, String appSecret, String tradeNo);

    /**
     * 退款接口
     *
     * @param inParams 接收的参数数据
     * @return 返回的数据
     */
    RefundOutDTO insertRefund(RefundInDTO inParams);

    /**
     * 退款查询接口
     *
     * @param appId     应用ID
     * @param appSecret 应用安全码
     * @param tradeNo   商户单号
     * @return
     */
    List<RefundOutDTO> queryRefund(String appId, String appSecret, String tradeNo);

    /**
     * 请求用户数据
     *
     * @param appId      应用ID
     * @param appSecret  应用安全码
     * @param userOpenId 用户ID
     */
    UserWalletInfoDTO checkToken(String appId, String appSecret, String userOpenId);

    /**
     * 发放奖金
     *
     * @param inParams 接收的数据
     * @return
     */
    RechargeOutDTO insertRecharge(RechargeInDTO inParams);

    /**
     * 发放奖金查询接口
     *
     * @param appId     应用ID
     * @param appSecret 应用安全码
     * @param tradeNo   商户单号
     * @return
     */
    RechargeOutDTO queryRecharge(String appId, String appSecret, String tradeNo);
}
