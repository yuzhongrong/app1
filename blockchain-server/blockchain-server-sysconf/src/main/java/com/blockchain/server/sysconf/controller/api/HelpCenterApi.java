package com.blockchain.server.sysconf.controller.api;

public class HelpCenterApi {
    public static final String HELPCENTER_API = "帮助中心控制器";

    public static class SELECTHELPCENTERFORAPP{
        public static final String METHOD_TITLE_NAME = "查询帮助中心信息";
        public static final String METHOD_TITLE_NOTE = "查询帮助中心信息";
    }
    public static class SELECTCONTENTBYID{
        public static final String METHOD_TITLE_NAME = "查询帮助中心内容";
        public static final String METHOD_TITLE_NOTE = "根据id查询帮助中心内容";
        public static final String METHOD_API_ID="id";
    }
    public static class SELECTHELPCENTERFORPC{
        public static final String METHOD_TITLE_NAME = "查询帮助中心信息";
        public static final String METHOD_TITLE_NOTE = "根据标题查询帮助中心信息";
        public static final String METHOD_API_TITLE="标题";
    }

}
