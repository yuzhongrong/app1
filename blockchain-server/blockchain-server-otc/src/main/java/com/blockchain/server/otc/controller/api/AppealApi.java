package com.blockchain.server.otc.controller.api;

public class AppealApi {
    public static final String APPEAL_API = "申诉控制器";

    public static class handleAppeal {
        public static final String METHOD_TITLE_NAME = "申诉";
        public static final String METHOD_TITLE_NOTE = "申诉";
        public static final String METHOD_API_ORDER_ID = "订单id";
        public static final String METHOD_API_URLS = "上传图片";
        public static final String METHOD_API_REMARK = "申诉说明";
    }

    public static class uploadAppealFile {
        public static final String METHOD_TITLE_NAME = "上传申诉图片";
        public static final String METHOD_TITLE_NOTE = "上传申诉图片";
        public static final String METHOD_API_APPEAL_FILE = "申诉图片文件";
    }

    public static class selectAppealHandleLog {
        public static final String METHOD_TITLE_NAME = "查询申诉处理日志";
        public static final String METHOD_TITLE_NOTE = "查询申诉处理日志";
        public static final String METHOD_API_ORDER_NUMBER = "申诉的订单流水";
    }
}