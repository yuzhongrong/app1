package com.blockchain.server.otc.controller.api;

public class UserPayApi {
    public static final String USER_PAY_API = "用户支付信息控制器";

    public static class listUserPay {
        public static final String METHOD_TITLE_NAME = "查询用户支付信息";
        public static final String METHOD_TITLE_NOTE = "查询用户支付信息";
    }

    public static class selectByAdPayType {
        public static final String METHOD_TITLE_NAME = "根据广告支付类型查询用户支付信息";
        public static final String METHOD_TITLE_NOTE = "根据订单id查询广告，然后获取广告支付类型查询订单的卖家支付信息";
        public static final String METHOD_API_ORDER_ID = "订单id";
    }

    public static class selectByPayType {
        public static final String METHOD_TITLE_NAME = "根据支付类型查询用户支付信息";
        public static final String METHOD_TITLE_NOTE = "根据支付类型查询用户支付信息";
        public static final String METHOD_API_PAP_TYPE = "支付类型";
    }

    public static class insertWX {
        public static final String METHOD_TITLE_NAME = "新增微信支付信息";
        public static final String METHOD_TITLE_NOTE = "新增微信支付信息";
        public static final String METHOD_API_ACCOUNT_INFO = "账号";
        public static final String METHOD_API_CODE_URL = "图片路径";
        public static final String METHOD_API_PASS = "资金密码";
    }

    public static class insertZFB {
        public static final String METHOD_TITLE_NAME = "新增支付宝支付信息";
        public static final String METHOD_TITLE_NOTE = "新增支付宝支付信息";
        public static final String METHOD_API_ACCOUNT_INFO = "账号";
        public static final String METHOD_API_CODE_URL = "图片路径";
        public static final String METHOD_API_PASS = "资金密码";
    }

    public static class insertBank {
        public static final String METHOD_TITLE_NAME = "新增银行卡支付信息";
        public static final String METHOD_TITLE_NOTE = "新增银行卡支付信息";
        public static final String METHOD_API_BANK_NUMBER = "银行卡号";
        public static final String METHOD_API_BANK_USER_NAME = "持卡人姓名";
        public static final String METHOD_API_BANK_TYPE = "开户银行";
        public static final String METHOD_API_PASS = "资金密码";
    }

    public static class updateWX {
        public static final String METHOD_TITLE_NAME = "更新微信支付信息";
        public static final String METHOD_TITLE_NOTE = "更新微信支付信息";
        public static final String METHOD_API_ACCOUNT_INFO = "账号";
        public static final String METHOD_API_CODE_URL = "图片路径";
        public static final String METHOD_API_PASS = "资金密码";
    }

    public static class updateZFB {
        public static final String METHOD_TITLE_NAME = "更新支付宝支付信息";
        public static final String METHOD_TITLE_NOTE = "更新支付宝支付信息";
        public static final String METHOD_API_ACCOUNT_INFO = "账号";
        public static final String METHOD_API_CODE_URL = "图片路径";
        public static final String METHOD_API_PASS = "资金密码";
    }

    public static class updateBank {
        public static final String METHOD_TITLE_NAME = "更新银行卡支付信息";
        public static final String METHOD_TITLE_NOTE = "更新银行卡支付信息";
        public static final String METHOD_API_BANK_NUMBER = "银行卡号";
        public static final String METHOD_API_BANK_USER_NAME = "持卡人姓名";
        public static final String METHOD_API_BANK_TYPE = "开户银行";
        public static final String METHOD_API_PASS = "资金密码";
    }

    public static class uploadWX {
        public static final String METHOD_TITLE_NAME = "上传微信支付信息";
        public static final String METHOD_TITLE_NOTE = "上传微信支付信息";
        public static final String METHOD_API_PAY_FILE = "上传的文件";
    }

    public static class uploadZFB {
        public static final String METHOD_TITLE_NAME = "上传微信支付信息";
        public static final String METHOD_TITLE_NOTE = "上传微信支付信息";
        public static final String METHOD_API_PAY_FILE = "上传的文件";
    }
}
