package com.blockchain.common.base.constant;

public class BaseConstant {
    public static final String REDIS_TOKEN_KEY = "user:token:";//redis 存储token的key值，用于获取用户信息
    public static final String REDIS_TOKEN_PC_KEY = "user:token:pc";//redis 存储token的key值，用于获取用户信息
    public static final String PARAMS_LOCALE = "locale";//中英文参数
    public static final String SSO_TOKEN_HEADER = "X-Requested-Token";//sso单点登录的header

    public static final String PAGE_DEFAULT_SIZE = "10";//分页默认查询条数
    public static final String PAGE_DEFAULT_INDEX = "1";//分页默认查询起始页

    public static final String USER_LOCALE_ZH_CN = "zh_CN";//中文
    public static final String USER_LOCALE_EN_US = "en_US";//英文
    public static final String USER_LOCALE_ZH_HK = "zh_HK";//繁体中文
    public static final String USER_LOCALE_DEFAULT = USER_LOCALE_ZH_CN;//默认国际化标识为繁体中文

    public static final int REQUEST_SUCCESS = 200;// 成功标识
}
