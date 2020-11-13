package com.blockchain.server.otc.common.enums;

import lombok.Getter;

/**
 * @author ：zz
 * @date ：Created in 2019/4/25 15:32
 * @description：消息推送枚举
 * @modified By：
 * @version: 1.0$
 */
public enum JgMsgEnums {
    FIRSTINIT_BUY("FIRSTINIT_BUY", "订单已生成，请您及时付款", null),
    FIRSTINIT_SELL("FIRSTINIT_SELL", "订单已生成，请您耐心等待买家付款", null),
    CONFIRM_BUYER_BUY("CONFIRM_BUY", "您已确认付款，请等待卖家确认收款和放币", null),
    CONFIRM_BUYER_SELL("CONFIRM_SELL", "买家已确认付款，请您检查到账情况并确认付款", null),
    CONFIRM_SELLER_BUY("CONFIRM_SELLER_BUY", "交易成功", null),
    CONFIRM_SELLER_SELL("CONFIRM_SELLER_SELL", "交易成功", null),
    APPEAL("APPEAL", "订单已进行申诉状态，买卖双方可多次提交交易资料或凭证！", null),
    REPEAL_BUY("REPEAL_BUY", "订单已撤销", null),
    REPEAL_SELL("REPEAL_SELL", "买家已撤销订单，如有疑问请联系客服！", null),
    AUTO_REPEAL("AUTO_REPEAL", "买家超过规定时间未付款，订单自动撤销！", null),
    ;

    @Getter
    private String code;
    @Getter
    private String name;//值
    @Getter
    private String value;//值 string类型

    JgMsgEnums(String code, String name, String value) {
        this.code = code;
        this.name = name;
        this.value = value;
    }

    public String code() {
        return code;
    }


//    @Override
//    public String name(){
//        return name;
//    }

}
