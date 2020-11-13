package com.blockchain.server.eos.common.enums;

public enum EosWalletEnums {
    SESSION_ERROR(201, "操作失败，用户未登录！", "The operation failed and the user is not logged in!", "操作失敗，用戶未登錄！"),
    EOSWALLET_GETWALLET_ERROR(202, "该钱包不存在！", "The wallet does not exist!", "該錢包不存在！"),
    ERROR(203, "操作失败！", "operation failure!", "操作失敗！"),
    BALANCE_AMOUNT_ERROR(204, "操作失败，余额不足！", "The operation failed and the balance is insufficient!", "操作失敗，餘額不足！"),
    PARAM_ERROR(205, "操作失败，参数有误！", "The operation failed and the parameters are incorrect!", "操作失敗，參數有誤"),
    WALLETTOKENT_ADD_ERROR(209, "操作失败，该代币钱包已存在！", "The operation failed and the token wallet already exists!", "操作失敗，該代幣錢包已存在！"),
    DIGCOIN_ERROR(211, "操作失败，数据异常！", "The operation failed and the data is abnormal!", "操作失敗，數據異常！"),
    TOKEN_TRANSFER_UPDATEHASH_ERROR(212, "操作失败，数据不存在！", "The operation failed and the data does not exist!", "操作失敗，數據不存在！"),
    TOKEN_NOTTOKENADDR_ERROR(213, "操作失败，该数字货币暂不支持", "The operation failed, the digital currency is not supported yet.", "操作失敗，該數字貨幣暫不支持！"),
    CREATION_WALLET_ERROR(214, "操作失败，托管钱包创建失败，请稍后再试！", "The operation failed, the managed wallet creation failed, please try again later!", "操作失敗，托管錢包創建失敗，請稍後再試！"),
    GET_PUBLICKEY_ERROR(215, "服务器繁忙，请稍后再试！", "The server is busy, please try again later!", "服務器繁忙，請稍後再試！"),
    EOS_RPC_ERROR(10000, "RPC post请求异常！", "RPC post request exception!", "RPC post請求異常！"),
    EOS_RPC_GET_BLOCK_ERROR(10001, "获取区块异常！", "Get block exceptions!", "獲取區塊異常！"),
    WITHDRAW_ERROR(7008, "提现失败", "Failed to withdrawal.", "提現失敗"),
    CURRENCY_FAILURE_ERROR(7008, "出币失败", "Currency failure.", "出幣失敗"),
    INEXISTENCE_TX(12500, "该记录未找到", "The record was not found", "該記錄未找到"),
    INEXISTENCE_WALLET(7017, "该钱包不存在", "The wallet does not exist.", "該錢包不存在"),
    REMAEKLENGTH_ERROR(12800, "转账备注不能超过50个字符", "Transfer notes cannot exceed 50 characters", ""),
    TRANSFER_ERROR(12801, "转账失败，请确认清楚后重试", "Transfer failed, please confirm and try again", "轉賬失敗，請確認清楚後重試"),
    DEPOSIT_ACCOUNT_ERROR(12802, "操作失败，提现地址有误，请重试", "The operation failed, the withdrawal address is incorrect, please try again", "操作失敗，提現地址有誤，請重試"),
    WITHDRAW_CODE_NULL(12803, "请输入验证码", "Please enter verification code", "請輸入驗證碼"),

    ;


    private int code;
    private String hkmsg;
    private String enMsg;
    private String cnmsg;

    EosWalletEnums(int code, String cnmsg, String enMsg, String hkmsg) {
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
