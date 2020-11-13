package com.blockchain.server.btc.controller.api;

public class BtcTokenApi {
    public static final String MARKET_CONTROLLER_API = "比特币币种控制器";

    public static class ListToken {
        public static final String METHOD_API_NAME = "获取币种列表信息";
        public static final String METHOD_API_NOTE = "获取币种列表信息";
    }

    public static class GetToken {
        public static final String METHOD_API_NAME = "获取币种，根据tokenid";
        public static final String METHOD_API_NOTE = "获取币种，根据tokenid";
        public static final String TOKENID = "币种id";
    }

}
