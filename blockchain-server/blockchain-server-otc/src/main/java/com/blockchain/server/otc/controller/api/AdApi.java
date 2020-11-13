package com.blockchain.server.otc.controller.api;

public class AdApi {
    public static final String AD_API = "广告控制器";
    public static final String METHOD_API_PAGE_NUM = "页码";
    public static final String METHOD_API_PAGE_SIZE = "页数";
    public static final String METHOD_API_COIN_NAME = "基本货币";
    public static final String METHOD_API_UNIT_NAME = "二级货币";
    public static final String METHOD_API_BEGIN_TIME = "开始时间";
    public static final String METHOD_API_END_TIME = "结束时间";

    public static class publishBuyAd {
        public static final String METHOD_TITLE_NAME = "发布买入广告";
        public static final String METHOD_TITLE_NOTE = "发布买入广告";
        public static final String METHOD_API_PARAM = "发布参数";
    }

    public static class publishSellAd {
        public static final String METHOD_TITLE_NAME = "发布卖出广告";
        public static final String METHOD_TITLE_NOTE = "发布卖出广告";
        public static final String METHOD_API_PARAM = "发布参数";
    }

    public static class cancelAd {
        public static final String METHOD_TITLE_NAME = "撤销广告";
        public static final String METHOD_TITLE_NOTE = "撤销广告";
        public static final String METHOD_API_ADID = "广告id";
    }

    public static class defaultAd {
        public static final String METHOD_TITLE_NAME = "上架广告";
        public static final String METHOD_TITLE_NOTE = "上架广告";
        public static final String METHOD_API_ADID = "广告id";
    }

    public static class pendingAd {
        public static final String METHOD_TITLE_NAME = "下架广告";
        public static final String METHOD_TITLE_NOTE = "下架广告";
        public static final String METHOD_API_ADID = "广告id";
    }

    public static class listBuyAd {
        public static final String METHOD_TITLE_NAME = "查询买入广告（交易大厅）";
        public static final String METHOD_TITLE_NOTE = "查询买入广告（交易大厅）";
    }

    public static class listSellAd {
        public static final String METHOD_TITLE_NAME = "查询卖出广告（交易大厅）";
        public static final String METHOD_TITLE_NOTE = "查询卖出广告（交易大厅）";
    }

    public static class listUserAd {
        public static final String METHOD_TITLE_NAME = "查询用户发布的广告";
        public static final String METHOD_TITLE_NOTE = "查询用户发布的广告";
        public static final String METHOD_API_AD_TYPE = "广告类型";
        public static final String METHOD_API_AD_STATUS = "广告类型";
    }

    public static class selectById {
        public static final String METHOD_TITLE_NAME = "根据id查询广告";
        public static final String METHOD_TITLE_NOTE = "根据id查询广告";
        public static final String METHOD_API_AD_ID = "广告ID";
    }

    public static class pcListBuyAd {
        public static final String METHOD_TITLE_NAME = "pc端查询买入广告（交易大厅）";
        public static final String METHOD_TITLE_NOTE = "pc端查询买入广告（交易大厅）";
    }

    public static class pcListSellAd {
        public static final String METHOD_TITLE_NAME = "pc端查询卖出广告（交易大厅）";
        public static final String METHOD_TITLE_NOTE = "pc端查询卖出广告（交易大厅）";
    }

    public static class pcListUserAd {
        public static final String METHOD_TITLE_NAME = "pc端查询用户发布的广告";
        public static final String METHOD_TITLE_NOTE = "pc端查询用户发布的广告";
        public static final String METHOD_API_AD_TYPE = "广告类型";
        public static final String METHOD_API_AD_STATUS = "广告类型";
    }
}
