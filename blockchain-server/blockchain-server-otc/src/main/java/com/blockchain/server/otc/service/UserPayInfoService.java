package com.blockchain.server.otc.service;

import com.blockchain.server.otc.entity.UserPayInfo;

import java.util.List;

public interface UserPayInfoService {

    /***
     * 根据userId和支付类型查询用户支付信息
     * @param userId
     * @param payType
     * @return
     */
    UserPayInfo selectByUserIdAndPayType(String userId, String payType);

    /***
     * 根据userId查询用户支付信息列表
     * @param userId
     * @return
     */
    List<UserPayInfo> listUserPay(String userId);

    /***
     * 根据广告设置的支付类型查询卖家支付信息
     *
     * @param userId
     * @param orderId
     * @return
     */
    List<UserPayInfo> selectSellUserPayInfoByAdPayType(String userId, String orderId);

    /***
     * 新增微信或支付宝支付信息
     * @param userId
     * @param payType
     * @param accountInfo
     * @param codeUrl
     * @param pass
     * @return
     */
    int insertWXorZFB(String userId, String payType, String accountInfo, String codeUrl, String pass);

    /***
     * 新增银行卡支付信息
     * @param userId
     * @param bankNumber
     * @param bankUserName
     * @param bankType
     * @return
     */
    int insertBank(String userId, String bankNumber, String bankUserName, String bankType, String pass);

    /***
     * 更新微信、支付宝信息
     * @param userId
     * @param payType
     * @param accountInfo
     * @param codeUrl
     * @return
     */
    int updateWXorZFB(String userId, String payType, String accountInfo, String codeUrl, String pass);

    /***
     * 更新银行卡信息
     * @param userId
     * @param bankNumber
     * @param bankUserName
     * @param bankType
     * @return
     */
    int updateBank(String userId, String bankNumber, String bankUserName, String bankType, String pass);
}
