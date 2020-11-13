package com.blockchain.server.user.common.constants.sql;

/**
 * @author huangxl
 * @create 2019-02-23 14:39
 */
public class UserInfoConstant {
    //初级认证状态
    public static final String STATUS_LOW_AUTH_WAIT = GlobalConstant.STATUS_WAIT;//等待状态
    public static final String STATUS_LOW_AUTH_YES = GlobalConstant.STATUS_YES;//同意状态
    public static final String STATUS_LOW_AUTH_NO = GlobalConstant.STATUS_NO;//拒绝、新建状态
    //高级认证状态
    public static final String STATUS_HIGHT_AUTH_WAIT = GlobalConstant.STATUS_WAIT;//等待状态
    public static final String STATUS_HIGHT_AUTH_YES = GlobalConstant.STATUS_YES;//同意状态
    public static final String STATUS_HIGHT_AUTH_NO = GlobalConstant.STATUS_NO;//拒绝、新建状态
}
