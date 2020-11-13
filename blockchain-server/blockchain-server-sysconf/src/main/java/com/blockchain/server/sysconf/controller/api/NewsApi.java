package com.blockchain.server.sysconf.controller.api;

public class NewsApi {

    public static final String NEWS_API = "文章控制器";

    public static class LISTNEWS{
        public static final String METHOD_TITLE_NAME = "查询文章列表";
        public static final String METHOD_TITLE_NOTE = "根据文章类型、国际化标识查询文章列表";
        public static final String METHOD_API_TYPE="文章类型";
        public static final String METHOD_API_LANGUAGES="国际化标识";
        public static final String METHOD_API_PAGENUM="查询页码";
        public static final String METHOD_API_PAGESIZE="每页记录数";
    }
    public static class NEWDETAIL{
        public static final String METHOD_TITLE_NAME = "获取文章详细信息";
        public static final String METHOD_TITLE_NOTE = "根据id获取文章详细信息";
        public static final String METHOD_API_ID="id";
    }
}
