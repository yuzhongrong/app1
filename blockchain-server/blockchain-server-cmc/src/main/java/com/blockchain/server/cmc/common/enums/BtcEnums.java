package com.blockchain.server.cmc.common.enums;

public enum BtcEnums {
    ADDRESS_ERROR(7000, "请输入有效的地址", "Please enter a valid address.", "請輸入有效的地址"),
    AMOUNT_NULL(7001, "请输入数量", "Please input quantity.", "請輸入數量"),
    SENDTOADDRESS_ERROR(7002, "交易失败", "Transaction failure.", "交易失敗"),
    GET_NEW_ADDRESS_ERROR(7003, "生成地址失败", "Request success", "生成地址失敗"),
    LIST_UNSPENT_ERROR(7004, "获取UTXO失败", "Failed to get UTXO", "獲取UTXO失敗"),
    CREATE_WALLET_ERROR(7005, "创建钱包失败", "Failed to create wallet.", "創建錢包失敗"),
    PARSE_TRANSFER_IN_ERROR(7006, "解析区块充值信息到数据库失败", "Failed to parse block recharge information to database.", "解析區塊充值信息到數據庫失敗"),
    INSERT_BLOCKNUMBER_ERROR(7007, "插入同步区块号失败", "Failed to insert synchronized block number.", "插入同步區塊號失敗"),
    WITHDRAW_ERROR(7008, "提现失败", "Failed to withdrawal.", "提現失敗"),
    FREEBALANCE_NOT_ENOUGH(7009, "钱包可用余额不足", "Insufficient balance available in wallet.", "錢包可用餘額不足"),
    INSERT_AUDITING_ERROR(7010, "插入审核记录失败", "Failed to insert audit record.", "插入審覈記錄失敗"),
    INSERT_WALLET_OUT_ERROR(7011, "插入提现资金钱包失败", "Failed to insert withdrawal fund wallet.", "插入提現資金錢包失敗"),
    UPDATE_BLOCK_NUMBER_STATUS_ERROR(7012, "修改同步区块状态失败", "Failed to modify synchronized block state.", "修改同步區塊狀態失敗"),
    TRANSFER_ERROR(7013, "交易失败", "Failed to transact.", "交易失敗"),
    BALANCE_NOT_ENOUGH(7014, "钱包余额不足", "Insufficient balance of wallet.", "錢包餘額不足"),
    INEXISTENCE_TOKENNAME(7015, "不识别该币种名称", "The currency name is not recognized.", "不識別該幣種名稱"),
    INEXISTENCE_TOKENID(7016, "不识别该币种类型", "The currency type is not recognized.", "不識別該幣種類型"),
    INEXISTENCE_WALLETTYPE(7017, "该钱包类型不存在", "The wallet type does not exist.", "該錢包類型不存在"),
    LOW_WITHDRAW_AMOUNT_ERROR(7018, "提现数量过低", "The withdrawal amount is too low.", "提現數量過低"),
    NO_WALLET_ERROR(7019, "钱包信息未找到", "Wallet information not found", "錢包信息未找到"),
    SERVER_IS_TOO_BUSY(15000, "服务器繁忙,请稍后重试", "The server is busy, please try again later", "服務器繁忙,請稍後重試"),
    INEXISTENCE_WALLET(7017, "该钱包不存在", "The wallet does not exist.", "該錢包不存在"),
    WITHDRAW_CODE_NULL(7020, "请输入验证码", "Please enter verification code", "請輸入驗證碼"),

    ;

    private int code;
    private String cnmsg;
    private String enMsg;
    private String hkmsg;

    BtcEnums(int code, String cnmsg, String enMsg, String hkmsg) {
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
