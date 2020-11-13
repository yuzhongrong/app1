package com.blockchain.server.otc.common.enums;

import lombok.Getter;

public enum OtcEnums {
    PASS_NULL(8701, "请输入密码！", "Please enter password!"),
    USERID_NULL(8702, "用户id为空！", "User id is empty!"),
    PUBLISH_AD_COIN_NULL(8703, "请选择交易货币！", "Please select the currency of exchange!"),
    PUBLISH_AD_UNIT_NULL(8704, "请选择交易单位！", "Please choose trading unit!"),
    PUBLISH_AD_MINLIMIT_NULL(8705, "请输入交易最小限额！", "Please enter minimum trading quota!"),
    PUBLISH_AD_MINLIMIT_ERROR(8706, "交易最小限额不合法！", "Trading the minimum limit is illegal!"),
    PUBLISH_AD_MINLIMIT_TOO_HIGH(8707, "交易最小限额过高！", "Trading minimum is too high!"),
    PUBLISH_AD_PRICE_NULL(8708, "请输入单价！", "Please enter unit price!"),
    PUBLISH_AD_PRICE_ERROR(8709, "单价小数长度不合法！", "Unit price decimal length illegal!"),
    PUBLISH_AD_TOTALNUM_ERROR(8710, "数量小数长度不合法！", "Illegal decimal length!"),
    PUBLISH_AD_TOTALNUM_NULL(8711, "请输入数量！", "Please enter quantity!"),
    PUBLISH_AD_PAYTYPE_NULL(8712, "请选择收款方式！", "Please choose payment method!"),
    PUBLISH_AD_PAY_ERROR(8713, "收款方式信息异常！", "Payment method information abnormal!"),
    PUBLISH_AD_PAY_BANK_NULL(8714, "您还未绑定银行卡收款信息！", "You have not bound the bank card collection information!"),
    PUBLISH_AD_PAY_WX_NULL(8715, "您还未绑定微信收款信息！", "You have not bound WeChat collection information!"),
    PUBLISH_AD_PAY_ZFB_NULL(8716, "您还未绑定支付宝收款信息！", "You have not bound alipay collection information!"),
    CANCEL_AD_ORDERS_UNFINISHED(8721, "操作失败，当前广告还有未完成订单！", "Operation failed, the current advertisement still has unfinished order!"),
    USER_PAY_INSERT_EXIST(8722, "操作失败，已存在相同收款信息！", "Operation failed, the same collection information already exists!"),
    ORDER_AD_NULL(8723, "操作失败，交易广告不存在！", "Operation failed, trade advertisement does not exist!"),
    ORDER_ID_NULL(8724, "操作失败，订单id为空！", "Operation failed, order id is empty!"),
    ORDER_USERID_EQUALS_AD_USERID(8725, "请勿与自己发布的广告交易！", "Do not trade with your own advertising!"),
    ORDER_NULL(8726, "操作失败，订单不存在！", "Operation failed, order does not exist!"),
    ORDER_BUY_STATUS_CHANGES(8727, "买家状态已修改，请刷新重试！", "Buyer status has been modified, please refresh and try again!"),
    ORDER_SELL_STATUS_CHANGES(8728, "卖家状态已修改，请刷新重试！", "Seller status has been modified, please refresh and try again!"),
    ORDER_STATUS_CHANGES(8729, "订单状态已修改，请刷新重试！", "Order status has been modified, please refresh and try again!"),
    ORDER_DEAL_ADID_NULL(8730, "操作失败，广告id为空！", "Operation failed, advertisement id is empty!"),
    ORDER_DEAL_AD_TYPE_ERROR(8731, "操作失败，广告类型异常！", "Operation failed, advertisement type is abnormal!"),
    ORDER_DEAL_AMOUNT_NULL(8732, "请输入交易数量！", "Please enter transaction quantity!"),
    ORDER_DEAL_PRICE_NULL(8733, "单价为空！", "Unit price is empty!"),
    ORDER_DEAL_TURNOVER_NULL(8734, "交易额为空！", "Trading volume is empty!"),
    ORDER_DEAL_AD_STATUS_ERROR(8735, "广告状态已改变，请刷新重试！", "The advertising status has changed, please refresh and try again!"),
    ORDER_DEAL_AD_NUM_ZERO(8736, "广告数量不足，请刷新重新！", "The advertisement quantity is insufficient, please refresh again!"),
    ORDER_DEAL_AMOUNT_DECIMAL_ERROR(8737, "交易数量小数长度不合法！", "Illegal to trade decimal length!"),
    ORDER_DEAL_TURNOVER_DECIMAL_ERROR(8738, "交易金额小数长度不合法！", "Transaction amount decimal length illegal!"),
    ORDER_DEAL_PRICE_NOT_REAL_TIME(8739, "广告单价已修改，请刷新重试！", "The advertising unit price has been modified, please refresh and try again!"),
    ORDER_DEAL_AD_PAY_NOT_BINGDING(8740, "您需激活至少一种买方的收款方式，才可进行出售！", "You need to activate at least one buyer's payment method before you can sell!"),
    ORDER_PAY_PAYTYPE_NULL(8741, "请选择其中一种支付方式！", "Please choose one of the payment methods!"),
    ORDER_PAY_USERID_NOT_EQUALS(8742, "操作失败，付款用户和下单用户id不一致！", "Operation failed, the id of the paying user and ordering user is inconsistent!"),
    ORDER_PAY_ERROR(8743, "支付方式非法，请选择广告设置的支付方式！", "Illegal payment method, please choose the payment method set by the advertisement!"),
    ORDER_CANCEL_USERID_NOT_EQUALS(8744, "操作失败，撤单用户和下单用户id不一致！", "Operation failed, the user id of reorder and reorder is inconsistent!"),
    ORDER_CANCEL_TYPE_NOT_BUY(8745, "操作失败，只有买单可以撤单！", "Operation failure, only the bill can be removed!"),
    ORDER_APPEAL_USERID_NOT_EQUALS(8746, "操作失败，申诉用户和订单用户id不一致！", "Operation failed, appeal user and order user id are not consistent!"),
    USER_PAY_ACCOUNTINFO_NULL(8747, "请输入收款账户！", "Please enter the collection account!"),
    USER_PAY_CODE_URL_NULL(8748, "请上传收款码！", "Please upload payment code!"),
    USER_PAY_BANK_NUMBER_NULL(8749, "请输入银行卡号！", "Please enter bank card number!"),
    USER_PAY_BANK_USER_NAME_NULL(8750, "请输入持卡人！", "Please enter cardholder!"),
    USER_PAY_BANK_TYPE_NULL(8751, "请输入开户银行！", "Please enter the bank of deposit!"),
    COIN_NULL(8752, "当前平台不支持该货币交易！", "The current platform does not support this currency transaction!"),
    WALLET_COIN_NET_ERROR(8753, "操作失败，钱包异常！", "Operation failed, wallet abnormal!"),
    UPLOAD_ERROR(8754, "图片上传失败，请刷新重试！", "Image upload failed, please refresh and try again!"),
    BUY_OR_SELL_TYPE_ERROR(8755, "买卖类型异常！", "Abnormal trading type!"),
    BUY_USER_OR_SELL_USER_ERROR(8756, "用户角色识别异常！", "User role recognition exception!"),
    TEMPORARILY_NOT_OPENED(8757, "暂停交易！", "Suspended trading"),
    AD_ID_NULL(8758, "操作失败，广告Id为空！", "Operation failed, advertisement Id is empty!"),
    AD_USERID_NOT_EQUALS(8759, "操作失败，操作用户和发布用户id不一致！", "Operation failed, operation user and published user id are inconsistent!"),
    AD_STATUS_ERROR(8760, "操作失败，广告状态以改变，请刷新重试!", "Operation failed, advertisement status changed, please refresh and try again!"),
    PUBLISH_AD_EXCEED_THE_LIMIT(8761, "发布失败，未完成广告超过发布数量限制！", "Launch failed, incomplete ads exceed release limit!"),
    AD_LAST_NUM_LESS_THAN_LIMIT(8762, "操作失败，剩余数量不足以进行交易！", "Operation failed, not enough left to trade!"),
    MARKET_APPLY_HAS_MORE(8763, "操作失败，已存在未处理的申请记录！", "Operation failed, there are already unprocessed application records!"),
    MARKET_FREEZE_NULL(8764, "您还不是商家，无法申请取消！", "You are not a marketer and cannot apply for cancellation!"),
    USER_NOT_MARKET(8765, "您还不是商家，无法发布广告！", "You are not a marketer and cannot advertise!"),
    MARKET_USER_NULL(8766, "您还不是商家，无法申请取消！", "You are not a marketer and cannot apply for cancellation!"),
    CANCEL_MARKET_USER_STATUS_ERROR(8767, "当前商家状态无法取消！", "The current market status cannot be cancelled!"),
    USER_IS_MRAKET(8768, "您已是商家，请勿重复操作！", "You are already a merchant, do not repeat the operation!"),
    ;

    @Getter
    private int code;
    @Getter
    private String cnmsg;
    @Getter
    private String enMsg;

    OtcEnums(int code, String cnmsg, String enMsg) {
        this.code = code;
        this.cnmsg = cnmsg;
        this.enMsg = enMsg;
    }
}
