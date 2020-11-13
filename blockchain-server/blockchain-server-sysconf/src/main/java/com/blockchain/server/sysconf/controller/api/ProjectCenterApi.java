package com.blockchain.server.sysconf.controller.api;

/**
 * @author: Liusd
 * @create: 2019-03-25 13:51
 **/
public class ProjectCenterApi {

    public static final String CONTROLLER_API = "轮播图管理控制器";

    public static class ProjectCenterList {
        public static final String METHOD_TITLE_NAME = "项目列表";
        public static final String METHOD_TITLE_NOTE = "项目列表";
        public static final String METHOD_API_CLASSIFYID = "分类id";
        public static final String METHOD_API_DESCR = "简介";
        public static final String METHOD_API_PAGENUM="查询页码";
        public static final String METHOD_API_PAGESIZE="每页记录数";
    }

    public static class ProjectCenterSelectById {
        public static final String METHOD_TITLE_NAME = "项目基础信息";
        public static final String METHOD_TITLE_NOTE = "项目基础信息";
        public static final String METHOD_API_ID = "id";
    }

    public static class ReportList {
        public static final String METHOD_TITLE_NAME = "报告列表";
        public static final String METHOD_TITLE_NOTE = "报告列表";
        public static final String METHOD_API_PROJECTID = "项目id";
        public static final String METHOD_API_DATETYPE = "时间范围";
        public static final String METHOD_API_REPORTTYPE = "报告类型";
        public static final String METHOD_API_PAGENUM="查询页码";
        public static final String METHOD_API_PAGESIZE="每页记录数";
    }

    public static class Star {
        public static final String METHOD_TITLE_NAME = "点赞";
        public static final String METHOD_TITLE_NOTE = "点赞";
        public static final String METHOD_API_PROJECTID = "项目id";
        public static final String METHOD_API_USERID = "用户id";
    }

    public static class ClassifyList {
        public static final String METHOD_TITLE_NAME = "分类列表";
        public static final String METHOD_TITLE_NOTE = "分类列表";
    }

}
