package com.blockchain.common.base.enums;

public enum BaseResultEnums {
    SUCCESS(200, "请求成功", "Request success", "請求成功"),
    NO_LOGIN(201, "未登录", "No login", "未登錄"),
    LOGIN_REPLACED(202, "您的账号在别处登录！", "Your account is logged in elsewhere！", "您的帳號在別處登錄"),
    RSA_ERROR(250, "加密错误", "RSACoder error", "加密錯誤"),
    BUSY(300, "服务器繁忙", "Server busy", "伺服器繁忙"),
    DEFAULT(500, "系统错误", "System error", "系統錯誤"),
    PASSWORD_ERROR(300, "密码错误", "Password error", "密碼錯誤"),
    PARAMS_ERROR(403, "参数异常", "Params error", "參數異常"),
    NOT_FOUND(404, "NOT FOUND", "Params error", "參數異常");

    private int code;
    private String hkmsg;
    private String enMsg;
    private String cnmsg;

    BaseResultEnums(int code, String cnmsg, String enMsg, String hkmsg) {
        this.code = code;
        this.cnmsg = cnmsg;
        this.enMsg = enMsg;
        this.hkmsg = hkmsg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getHkmsg() {
        return hkmsg;
    }

    public void setHkmsg(String hkmsg) {
        this.hkmsg = hkmsg;
    }

    public String getEnMsg() {
        return enMsg;
    }

    public void setEnMsg(String enMsg) {
        this.enMsg = enMsg;
    }

    public String getCnmsg() {
        return cnmsg;
    }

    public void setCnmsg(String cnmsg) {
        this.cnmsg = cnmsg;
    }
}
