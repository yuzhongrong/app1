package com.blockchain.server.otc.controller.api;

public class ConfigApi {
    public static final String CONFIG_API = "配置控制器";

    public static class SelectAutoCancelInterval {
        public static final String METHOD_TITLE_NAME = "查询自动撤单间隔时间";
        public static final String METHOD_TITLE_NOTE = "查询自动撤单间隔时间";
    }

    public class SelectMarketFreezeCoin {
        public static final String METHOD_TITLE_NOTE = "查询市商保证金代币";
        public static final String METHOD_TITLE_NAME = "查询市商保证金代币";
    }

    public class SelectMarketFreezeAmount {
        public static final String METHOD_TITLE_NAME = "查询市商保证金数量";
        public static final String METHOD_TITLE_NOTE = "查询市商保证金数量";
    }
}
