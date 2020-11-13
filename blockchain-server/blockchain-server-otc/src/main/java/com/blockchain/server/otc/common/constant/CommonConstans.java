package com.blockchain.server.otc.common.constant;

import java.math.BigDecimal;

/***
 * 公共常量
 */
public class CommonConstans {

    /***
     * 公共状态、类型
     */
    public static final String C2C_APP = "C2C"; //币币交易调用钱包Feign调用应用标识

    public static final String YES = "Y"; //可用状态
    public static final String NO = "N"; //禁用状态

    public static final String BUY = "BUY"; //广告、订单：买入类型
    public static final String SELL = "SELL"; //广告、订单：卖出类型

    public static final String DESC = "DESC"; //排序-倒序
    public static final String ASC = "ASC"; //排序-升序

    public static final BigDecimal MINUS_ONE = new BigDecimal("-1"); //BigDecimal类型的-1
    public static final int LIMIT_CNY_DECIMAL = 2;//交易限额CNY保留小数位长度
}
