package com.blockchain.server.sysconf.common.constants;

public class UserConstant {

    public static final int ENABLE_GP = 1;//开启手势密码
    public static final int DISABLE_GP = 0;//关闭手势密码

    public static final int USER_SEX_MALE = 1;  //男性

    public static final int USER_SEX_FEMALE = 0;  //女性

    public static final int USER_NICKNAME_MAX_LENGTH = 30; //昵称默认长度

    public static final String USER_LOCAL_CHINA = "zh_CN"; //中文语种

    public static final String USER_LOCAL_ENG = "en_US";   //英文语种

    public static final int STATUS_DISABLE = 0;  //禁用
    public static final int STATUS_DEFAULT = 1;  //默认
    public static final int STATUS_AUTHENTICATED = 2;//已认证
    public static final int STATUS_MAKER = 3;//市商认证

}
