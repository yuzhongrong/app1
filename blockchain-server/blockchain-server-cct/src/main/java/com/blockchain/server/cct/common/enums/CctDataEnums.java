package com.blockchain.server.cct.common.enums;

import lombok.Getter;

public enum CctDataEnums {

    //币币交易调用钱包Feign调用应用标识
    CCT_APP("币币交易调用钱包Feign调用应用标识", 0, "CCT"),

    //发布订阅相关
    PUBLISH_ORDER("发布的频道名字", 0, "publishOrder"),
    REDIS_LIST_KEY("自动撮合数据在list中的key", 0, "cct:list:order:num:"),
    REDIS_LOCK_NUM_KEY("自动撮合分布式锁单价的key", 0, "cct:distributed:lock:num:"),
    REDIS_LOCK_ORDERID_KEY("自动撮合分布式锁订单id的key", 0, "cct:distributed:lock:orderid:"),
    REDIS_MATCH_COUNT_KEY("自动撮合订单撮合次数key", 0, "cct:match:count:orderid:"),
    REDIS_SEND_INTERVAL("发送到前端的广播时间间隔keyvalue", 100, "cct:send:time"),
    REDIS_HUOBI_ORDER("量化系统火币盘口数据key", 0, "cct:huobi:order:"),
    REDIS_HUOBI_RECORD("量化系统火币历史成交数据key", 0, "cct:huobi:record:"),
    SUBSCRIPTION("webSocket发布订阅主题", 0, "/topic/subscription"),
    USER_SUBSCRIPTION("用户一对一发布订阅主题", 0, "/topic/user"),

    //配置相关
    CONFIG_MAKER_CHARGE("挂单手续费配置名字", 0, "maker_charge"),
    CONFIG_TAKER_CHARGE("吃单手续费配置名字", 0, "taker_charge"),
    CONFIG_SERVICE_REST("休市配置名字", 0, "closed"),
    CONFIG_AUTO_REPEAL("自动撤单配置名字", 0, "auto_repeal"),

    //公共状态
    COMMON_STATUS_YES("可用状态", 0, "Y"),

    COMMON_STATUS_NO("禁用状态", 0, "N"),

    //订单状态
    ORDER_STATUS_NEW("新建", 0, "NEW"),

    ORDER_STATUS_MATCH("已撮合", 0, "MATCH"),

    ORDER_STATUS_FINISH("已完成", 0, "FINISH"),

    //由于量化交易撤单会有一个时间差，所以需要一个中间状态
    ORDER_STATUS_CANCELING("撤单中", 0, "CANCELING"),

    ORDER_STATUS_CANCEL("撤单", 0, "CANCEL"),


    //订单类型
    ORDER_TYPE_BUY("买单", 0, "BUY"),

    ORDER_TYPE_SELL("卖单", 0, "SELL"),

    //发布类型
    PUBLISH_TYPE_MARKET("市价交易", 0, "MARKET"),

    PUBLISH_TYPE_LIMIT("限价交易", 0, "LIMIT"),

    //成交记录订单类型
    DETAIL_TYPE_MAKER("成交记录挂单类型", 0, "MAKER"),

    DETAIL_TYPE_TAKER("成交记录吃单类型", 0, "TAKER"),

    //公共
    DECIMAL_LENGTH("数据库小数位长度", 8, ""),

    //接口排序
    SORT_DESC("排序倒序", 0, "DESC"),
    SORT_ASC("排序升序", 0, "ASC"),
    ;

    @Getter
    private String note;//注释
    @Getter
    private int intValue;//值 int类型，默认0
    @Getter
    private String strVlue;//值 string类型

    CctDataEnums(String note, int intValue, String strVlue) {
        this.note = note;
        this.intValue = intValue;
        this.strVlue = strVlue;
    }
}
