package com.blockchain.server.teth.controller.api;

public class EthTokenApi {
    public static final String MARKET_CONTROLLER_API = "以太坊币种信息控制器";

    public static class FindToken {
        public static final String METHOD_API_NAME = "查询以太坊指定的币种信息";
        public static final String METHOD_API_NOTE = "根据币种地址获取以太坊的币种信息";
        public static final String METHOD_API_TOKENADDR = "币种地址";
    }

    public static class SelectTokenAll {
        public static final String METHOD_API_NAME = "查询以太坊所有币种信息";
        public static final String METHOD_API_NOTE = "查询以太坊所有币种信息";
    }
}
