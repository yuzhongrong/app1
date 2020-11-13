package com.blockchain.server.tron.common.enums;

public enum TronWalletEnums {
    SESSION_ERROR(14211, "操作失败，用户未登录！", "The operation failed and the user is not logged in!", "操作失敗，用戶未登錄！"),
    TRONWALLET_GETWALLET_ERROR(14212, "该钱包不存在！", "The wallet does not exist!", "該錢包不存在！"),
    ERROR(14213, "操作失败！", "operation failure!", "操作失敗！"),
    BALANCE_AMOUNT_ERROR(14214, "操作失败，余额不足！", "The operation failed and the balance is insufficient!", "操作失敗，餘額不足！"),
    PARAM_ERROR(14215, "操作失败，参数有误！", "The operation failed and the parameters are incorrect!", "操作失敗，參數有誤"),
    WALLETTOKENT_ADD_ERROR(14216, "操作失败，该代币钱包已存在！", "The operation failed and the token wallet already exists!", "操作失敗，該代幣錢包已存在！"),
    CREATE_WALLET_ERROR(14217, "创建钱包失败，请稍后重试！", "Failed to create wallet, please try again later!", "創建錢包失敗，請稍後重試！"),
    BLOCKCHAIN_WALLET_NOT_EXIST(14218, "钱包地址不存在，请重新输入钱包地址！", "The wallet address does not exist, please re-enter the wallet address!", "錢包地址不存在，請重新輸入錢包地址！"),
    DIGCOIN_ERROR(14219, "操作失败，数据异常！", "The operation failed and the data is abnormal!", "操作失敗，數據異常！"),
    TOKEN_TRANSFER_UPDATEHASH_ERROR(14220, "操作失败，数据不存在！", "The operation failed and the data does not exist!", "操作失敗，數據不存在！"),
    TOKEN_NOTTOKENADDR_ERROR(14221, "操作失败，该数字货币暂不支持", "The operation failed, the digital currency is not supported yet.", "操作失敗，該數字貨幣暫不支持！"),
    CREATION_WALLET_ERROR(14222, "钱包创建维护中，请稍后再试！", "Wallet creation and maintenance, please try again later!", "錢包創建維護中，請稍後再試！"),
    GET_PUBLICKEY_ERROR(14223, "服务器繁忙，请稍后再试！", "The server is busy, please try again later!", "服務器繁忙，請稍後再試！"),
    NUMBER_COUNT_ERROR(14224, "操作失败，转账金额必须大于0", "Operation failed, transfer amount must be greater than 0",
            "操作失敗，轉賬金額必須大於0"),
    NUMBER_TYPE_ERROR(14225, "操作失败，转账金额格式有误", "Operation failed, the transfer amount format is wrong", "操作失敗，轉賬金額格式有誤"),
    NULL_TOKENADDR(14226, "货币地址不能为空", "The currency address cannot be empty", "貨幣地址不能為空"),
    TRON_RPC_ERROR(14227, "RPC post请求异常！", "RPC post request exception!", "RPC post請求異常！"),
    TRON_RPC_GET_BLOCK_ERROR(14228, "获取区块异常！", "Get block exceptions!", "獲取區塊異常！"),
    WITHDRAW_ERROR(14229, "提现失败，请稍后重试！", "The withdrawal failed, please try again later!", "提現失敗，請稍後重試！"),
    CURRENCY_FAILURE_ERROR(14230, "出币失败", "Currency failure.", "出幣失敗"),
    INEXISTENCE_TX(14231, "该记录未找到", "The record was not found", "該記錄未找到"),
    INEXISTENCE_WALLET(14232, "该钱包不存在", "The wallet does not exist.", "該錢包不存在"),
    CHECK_ADDRESS_ERROR(14233, "地址异常，请稍后重试！", "The address is abnormal. Please try again later!", "地址異常，請稍後重試！"),
    WITHDRAW_DEPOSIT_ADDRESS_ERROR(14234, "提现地址有误，请重试！", "The withdrawal address is incorrect, please try again!", "提現地址有誤，請重試！"),
    WITHDRAW_DEPOSIT_ADDRESS_NONSUPPORT(14235, "暂不支持该地址提现，请更换提现地址！", "The address withdrawal is not supported at this time, please replace the withdrawal address!", "暫不支持該地址提現，請更換提現地址！"), PAY_INPARAMSNULL_ERROR(301, "下单失败，未接收到订单信息！", "", ""),
    NULL_ADDR(12500, "钱包地址不能为空", "The wallet address cannot be empty", "錢包地址不能為空"),
    PAY_INPARAMS_APPIDNULL_ERROR(301, "下单失败，应用标识不存在！", "", ""),
    PAY_INPARAMS_APPSECRETNULL_ERROR(301, "下单失败，安全码缺失！", "", ""),
    PAY_INPARAMS_AMOUNTNULL_ERROR(301, "下单失败，未接收到支付金额！", "", ""),
    PAY_INPARAMS_COINNAMENULL_ERROR(301, "下单失败，未接收到数字货币名称！", "", ""),
    PAY_INPARAMS_COINADDRESSNULL_ERROR(301, "下单失败，未接收到数字货币合约地址！", "", ""),
    PAY_INPARAMS_NOTIFYURLNULL_ERROR(301, "下单失败，未接收到通知支付结果接口！", "", ""),
    PAY_INPARAMS_TRADENONULL_ERROR(301, "下单失败，未接收到商户单号！", "", ""),
    PAY_SIGE_TRADENONULL_ERROR(301, "下单失败，未接收到签名！", "", ""),
    PAY_INPARAMS_OPENIDNULL_ERROR(301, "下单失败，未接收到用户标识！", "", ""),
    PAY_INPARAMS_APPLY_ERROR(300, "下单失败，应用信息未识别！", "", ""),
    PAY_INPARAMS_OPENID_ERROR(300, "下单失败，用户信息未识别！", "", ""),
    PAY_INPARAMS_TRADENO_ERROR(300, "下单失败，该商户单号已存在！", "", ""),
    PAY_INPARAMS_AMOUNT_ERROR(300, "下单失败，支付金额格式有误！", "", ""),
    PAY_INPARAMS_COINNAME_ERROR(300, "下单失败，接收的数字货币名称不一致！", "", ""),
    PAY_INPARAMS_AMOUNTLACK_ERROR(300, "下单失败，数字货币余额不足！", "", ""),
    PAY_INPARAMS_COINADDRESS_ERROR(300, "下单失败，该数字货币钱包不存在，可能是代币地址有误或用户没有该代币钱包！", "", ""),
    PAY_PAY_PREPAYIDNULL_ERROR(301, "支付失败，未接收到预支付标识！", "", ""),
    PAY_PAY_PASSWORNULL_ERROR(301, "支付失败，钱包密码为空！", "", ""),
    PAY_PAY_PASSWORABSENCE_ERROR(300, "支付失败，订单信息无法获取，请刷新重试！", "", ""),
    PAY_PAY_USEROPENTID_ERROR(300, "支付失败，用户未识别，请刷新重试！", "", ""),
    PAY_PAY_PASSWORD_ERROR(300, "支付失败，密码错误，请重新输入！", "", ""),
    TRANSFER_PASSWORD_ERROR(300, "转账失败，密码错误！", "", ""),
    PAY_PAY_ETHAMOUNT_ERROR(300, "支付失败，数字货币USDT余额不足！", "", ""),
    PAY_PREPAYIDEXIST_ERROR(300, "支付失败，订单已支付！", "", ""),
    PAY_PAY_TOKENAMOUNT_ERROR(300, "支付失败，数字货币{tokenName}余额不足！", "", ""),
    PAY_INFORM_ERROR(300, "支付失败，服务器异常，请刷新重试！", "", ""),
    QUERYORDER_TRADENONULL_ERROR(300, "查询失败，未接收到商户单号！", "", ""),
    QUERYORDER_INPARAMS_APPLY_ERROR(300, "查询失败，应用信息未识别！", "", ""),
    QUERYORDER_TRADENO_ERROR(300, "查询失败，未找到该订单！", "", ""),
    PAY_PREPAYIDNULL_ERROR(301, "获取失败，预支付ID为空！", "", ""),
    PAY_PREPAYID_ERROR(300, "获取失败，预支付信息不存在！", "", ""),
    APPLY_APPIDNULL_ERROR(12500, "操作失败，应用标识不存在！", "", ""),
    APPLY_APPSECRETNULL_ERROR(12500, "操作失败，安全码缺失！", "", ""),
    NULL_USEROPENID(12500, "用户标识不能为空", "The user id cannot be empty", "用戶標識不能為空"),
    INEXISTENCE_TOKENADDR(12600, "该币种暂不支持此操作", "This operation is not currently supported in this currency", "該幣種暫不支持此操作"),
    NULL_HASH(12500, "记录重要标识不能为空", "", ""),
    NULL_TXTYPE(12500, "记录类型标识不能为空", "", ""),
    NULL_FREEBLANCE(12500, "可用余额不能为空", "The available balance cannot be empty", "可用余額不能為空"),
    NULL_FREEZEBLANCE(12500, "冻结余额不能为空", "The frozen balance cannot be empty", "凍結余額不能為空"),
    NUMBER_INSUFFICIENTZE_ERROR(12700, "操作失败，该数字货币可用余额不足", "Operation failed, the digital currency freeze balance is insufficient", "操作失敗，該數字貨幣凍結余額不足"),
    SERVER_IS_TOO_BUSY(15000, "服务器繁忙,请稍后重试", "The server is busy, please try again later", "服務器繁忙,請稍後重試"),
    INSERT_PAYINFORM(12800, "支付失败，服务器异常，请刷新重试！", "", ""),

    REFUND_INPARAMSNULL_ERROR(12500, "退款失败，未接收到订单信息！", "", ""),
    REFUND_INPARAMS_APPIDNULL_ERROR(12500, "退款失败，应用标识不存在！", "", ""),
    REFUND_INPARAMS_APPSECRETNULL_ERROR(12500, "退款失败，安全码缺失！", "", ""),
    REFUND_INPARAMS_AMOUNTNULL_ERROR(12500, "退款失败，未接收到支付金额！", "", ""),
    REFUND_INPARAMS_COINNAMENULL_ERROR(12500, "退款失败，未接收到数字货币名称！", "", ""),
    REFUND_INPARAMS_COINADDRESSNULL_ERROR(12500, "退款失败，未接收到币种类型", "", ""),
    REFUND_INPARAMS_TRADENONULL_ERROR(12500, "退款失败，未接收到商户单号！", "", ""),
    REFUND_INPARAMS_APPLY_ERROR(12600, "退款失败，应用信息未识别！", "", ""),
    REFUND_TRADENO_ERROR(12600, "退款失败，未找到该订单！", "", ""),
    REFUND_TOKENTYPE_ERROR(12600, "退款失败，退款数字货币不一致！", "", ""),
    REFUND_AMOUHNT_ZROE_ERROR(12600, "退款失败，退款额数需要大于0！", "", ""),
    REFUND_AMOUNT_ERROR(12600, "退款失败，退款余额已超过支付金额！", "", ""),
    REFUND_INSERTROW_ERROR(12600, "退款失败，服务器异常，请稍后重试！", "", ""),

    RECHARGE_INPARAMSNULL_ERROR(12500, "奖金发放失败，未接收到订单信息！", "", ""),
    RECHARGE_INPARAMS_APPIDNULL_ERROR(12500, "奖金发放失败，应用标识不存在！", "", ""),
    RECHARGE_INPARAMS_APPSECRETNULL_ERROR(12500, "奖金发放失败，安全码缺失！", "", ""),
    RECHARGE_INPARAMS_AMOUNTNULL_ERROR(12500, "奖金发放失败，未接收到支付金额！", "", ""),
    RECHARGE_INPARAMS_COINNAMENULL_ERROR(12500, "奖金发放失败，未接收到数字货币名称！", "", ""),
    RECHARGE_INPARAMS_COINADDRESSNULL_ERROR(12500, "奖金发放失败，未接收到数字货币合约地址！", "", ""),
    RECHARGE_INPARAMS_TRADENONULL_ERROR(12500, "奖金发放失败，未接收到商户单号！", "", ""),
    RECHARGE_INPARAMS_APPLY_ERROR(12500, "奖金发放失败，应用信息未识别！", "", ""),
    RECHARGE_INSERTROW_ERROR(12500, "奖金发放失败，服务器异常，请稍后重试！", "", ""),
    RECHARGE_INPARAMS_OPENID_ERROR(12600, "奖金发放失败，用户信息未识别！", "", ""),
    RECHARGE_ERROR(12600, "该奖金已发放！", "", ""),
    RECHARGE_AMOUHNT_ZROE_ERROR(12600, "奖金发放失败，发放额数需要大于0！", "", ""),
    RECHARGE_INPARAMS_COINADDRESS_ERROR(12600, "奖金发放失败，该数字货币钱包不存在，可能是代币地址有误或用户没有该代币钱包！", "", ""),
    RECHARGE_INPARAMS_COINNAME_ERROR(12600, "奖金发放失败，接收的数字货币名称不一致！", "", ""),
    ETHWALLET_TRANSFERTYPE_ERROR(1301, "操作失败，该操作类型不存在！", "", ""),
    ETHWALLET_GETWALLET_ERROR(1302, "该钱包不存在！", "", ""),
    INEXISTENCE_WALLETTYPE(7017, "该钱包类型不存在", "The wallet type does not exist.", "該錢包類型不存在"),
    NUMBER_INSUFFICIENT_ERROR(12700, "操作失败，该数字货币可用余额不足", "Operation failed, the available balance of the digital currency is insufficient", "操作失敗，該數字貨幣可用余額不足"),
    WALLET_CREATE_NOT_USEABLE(12701, "注册功能", "Operation failed, the available balance of the digital currency is insufficient", "操作失敗，該數字貨幣可用余額不足"),
    NO_AUTHENTICATION(12702, "提现请先实名认证！", "Please withdraw your real name certification!", "提現請先實名認證！"),
    WITHDRAW_CODE_NULL(7020, "请输入验证码", "Please enter verification code", "請輸入驗證碼"),

    ;


    private int code;
    private String hkmsg;
    private String enMsg;
    private String cnmsg;

    TronWalletEnums(int code, String cnmsg, String enMsg, String hkmsg) {
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
