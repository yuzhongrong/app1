package com.blockchain.common.base.enums;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

public enum PushEnums {
    OTC_ORDER_DEAL_BUY("OTC_ORDER_DEAL_BUY", "法币交易", "已有用户下单向您购买代币，请尽快处理！", "Fiat", "There are already users under one-way you buy token, please deal with it as soon as possible!"),
    OTC_ORDER_DEAL_SELL("OTC_ORDER_DEAL_SELL", "法币交易", "已有用户下单向您出售代币，请尽快处理！", "Fiat", "Existing users under one-way you sell tokens, please deal with as soon as possible!"),
    OTC_ORDER_PAY("OTC_ORDER_PAY", "法币交易", "买家已确认付款请尽快处理！", "Fiat", "The buyer has confirmed the payment, please deal with it as soon as possible!"),
    OTC_ORDER_RECEIPT("OTC_ORDER_RECEIPT", "法币交易", "卖家已确认收款，交易完成！", "Fiat", "The seller has confirmed the payment, the transaction is completed!"),
    OTC_ORDER_APPEAL("OTC_ORDER_APPEAL", "法币交易", "订单已进入申诉流程，请尽快处理！", "Fiat", "The order has entered the complaint process, please deal with it as soon as possible!"),
    OTC_ORDER_APPEAL_FINISH("OTC_ORDER_APPEAL_FINISH", "法币交易", "订单申诉已结束，请前往查看>>", "Fiat", "The appeal of order is over, please go to see >>"),
    OTC_ORDER_CANCEL("OTC_ORDER_CANCEL", "法币交易", "订单已取消，买家已取消订单！", "Fiat", "Order cancelled, buyer cancelled order!"),
    OTC_ORDER_AUTO_CANCEL("OTC_ORDER_AUTO_CANCEL", "法币交易", "买家付款超时，订单已自动取消！", "Fiat", "Buyer payment timeout, the order has been automatically cancelled!"),
    OTC_AD_ADMIND_CANCEL("OTC_AD_ADMIND_CANCEL", "法币交易", "后台管理员已经您的广告取消，请前往查看>>", "Fiat", "Background administrator has cancelled your advertisement, please go to see >>"),
    OTC_MARKET_AGREE("OTC_MARKET_AGREE", "焰火网", "您提交的市商申请已通过！", "FLAME", "Your marketing application has been approved!"),
    OTC_MARKET_REJECT("OTC_MARKET_REJECT", "焰火网", "您提交的市商申请已被驳回！", "FLAME", "Your marketing application has been rejected!"),
    OTC_MARKET_CANCEL_AGREE("OTC_MARKET_CANCEL_AGREE", "焰火网", "您提交的取消市商申请已通过！", "FLAME", "Your application for cancellation has been approved!"),
    OTC_MARKET_CANCEL_REJECT("OTC_MARKET_CANCEL_REJECT", "焰火网", "您提交的取消市商申请已驳回！", "FLAME", "Your application for cancellation has been rejected!"),

    CCT_HISTORY_MARK("CCT_HISTORY_MARK", "币币交易", "您的订单已成交，请前往查看>>", "Exchange", "Your order has been completed, please go to >>"),
    CCT_HISTORY_AUTO_CANCEL("CCT_HISTORY_AUTO_CANCEL", "币币交易", "找不到匹配的撮合订单，订单已自动撤销", "Exchange", "Matching matching order cannot be found, the order has been automatically cancelled"),
    CCT_HISTORY_ADMIN_CANCEL("CCT_HISTORY_ADMIN_CANCEL", "币币交易", "后台管理员已将您的订单取消，请前往查看>>", "Exchange", "The background administrator has cancelled your order. Please check >>"),
    ;

    private String pushType;
    private String cnTitle;
    private String cnContent;
    private String enTitle;
    private String enContent;

    private static Map<String, PushEnums> enumsMap = new HashMap<>();

    static {
        PushEnums[] pushEnums = PushEnums.values();
        for (PushEnums pushEnum : pushEnums) {
            enumsMap.put(pushEnum.getPushType(), pushEnum);
        }
    }

    public static PushEnums getEnumByPushType(String pushType) {
        return enumsMap.get(pushType);
    }

    PushEnums(String pushType, String cnTitle, String cnContent, String enTitle, String enContent) {
        this.pushType = pushType;
        this.cnTitle = cnTitle;
        this.cnContent = cnContent;
        this.enTitle = enTitle;
        this.enContent = enContent;
    }

    public String getPushType() {
        return pushType;
    }

    public void setPushType(String pushType) {
        this.pushType = pushType;
    }

    public String getCnTitle() {
        return cnTitle;
    }

    public void setCnTitle(String cnTitle) {
        this.cnTitle = cnTitle;
    }

    public String getCnContent() {
        return cnContent;
    }

    public void setCnContent(String cnContent) {
        this.cnContent = cnContent;
    }

    public String getEnTitle() {
        return enTitle;
    }

    public void setEnTitle(String enTitle) {
        this.enTitle = enTitle;
    }

    public String getEnContent() {
        return enContent;
    }

    public void setEnContent(String enContent) {
        this.enContent = enContent;
    }
}
