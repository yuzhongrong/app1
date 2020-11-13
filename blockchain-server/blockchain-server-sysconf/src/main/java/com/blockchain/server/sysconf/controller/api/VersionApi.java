package com.blockchain.server.sysconf.controller.api;

public class VersionApi {
    public static final String VERSION_API = "版本控制器";

    public static class FINDNEWVERSION{
        public static final String METHOD_TITLE_NAME = "查询最新app版本信息";
        public static final String METHOD_TITLE_NOTE = "根据设备型号查询最新app版本信息";
        public static final String METHOD_API_DEVICE="设备型号";
    }
    public static class FINDNEWVERSIONALL{
        public static final String METHOD_TITLE_NAME = "查询所有系统最新app版本信息";
        public static final String METHOD_TITLE_NOTE = "查询所有系统最新app版本信息";
    }
}
