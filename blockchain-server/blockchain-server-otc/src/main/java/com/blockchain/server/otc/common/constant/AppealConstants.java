package com.blockchain.server.otc.common.constant;

/***
 * 申诉表（appeal、appealDetail、appealImg）常量
 */
public class AppealConstants {

    /***
     * 申诉记录状态
     */
    public static final String NEW = "NEW";
    public static final String HANDLE = "HANDLE";

    /***
     * 申诉方角色类型
     */
    public static final String AD = "AD"; //广告方申诉
    public static final String ORDER = "ORDER"; //订单方申诉

    /***
     * 申诉处理备注
     */
    public static final String RECEIPT_REMARK = "卖家确认收款，自动取消申诉"; //确认付款时的申诉处理日志备注
    public static final String RECEIPT_IP_ADDR = "127.0.0.1"; //确认付款时的申诉处理日志的IP地址
    public static final String RECEIPT_SYS_USER_ID = "admin"; //确认付款时的申诉处理日志的管理员Id

    /***
     * 申诉图片
     */
    public static final String APPEAL_URL = "/appeal"; //申诉图片上传路径
}
