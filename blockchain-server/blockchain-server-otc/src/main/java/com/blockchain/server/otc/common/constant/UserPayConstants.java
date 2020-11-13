package com.blockchain.server.otc.common.constant;

/***
 * 用户支付信息表常量
 */
public class UserPayConstants {

    /***
     * 用户支付类型
     */
    public static final String BANK = "BANK"; //银行卡
    public static final String WX = "WX"; //微信
    public static final String ZFB = "ZFB"; //支付宝

    /***
     * 收款图片上传路径
     */
    public static final String WX_URL = "/userpay/wx"; //微信图片上传路径
    public static final String ZFB_URL = "/userpay/zfb"; //支付宝图片上传路径
}
