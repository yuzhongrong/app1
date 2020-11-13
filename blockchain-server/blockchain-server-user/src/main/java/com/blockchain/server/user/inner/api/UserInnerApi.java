package com.blockchain.server.user.inner.api;

/**
 * @author huangxl
 * @create 2019-03-04 10:54
 */
public class UserInnerApi {
    public static class HasTransactionPermission {
        public static final String METHOD_NAME = "查询用户是否有交易权限接口";
        public static final String METHOD_NOTE = "查询用户是否有交易权限接口";
        public static final String METHOD_API_USER_ID = "用户id";
    }

    public static class IsHightAuth {
        public static final String METHOD_NAME = "查询用户是否有高级认证权限";
        public static final String METHOD_NOTE = "查询用户是否有高级认证权限";
        public static final String METHOD_API_USER_ID = "用户id";
    }

    public static class sendInform {
        public static final String METHOD_NAME = "发送短信推送信息";
        public static final String METHOD_NOTE = "发送短信推送信息";
        public static final String DESCRIBE = "信息内容 xxx";

    }


    public static class IsPrimarytAuth {
        public static final String METHOD_NAME = "查询用户是否有初级认证权限";
        public static final String METHOD_NOTE = "查询用户是否有初级认证权限";
        public static final String METHOD_API_USER_ID = "用户id";
    }

    public static class VerifyBanWithdraw {
        public static final String METHOD_NAME = "查询用户是否有提现权限接口";
        public static final String METHOD_NOTE = "查询用户是否有提现权限接口";
        public static final String METHOD_API_USER_ID = "用户id";
    }

    public static class VerifyFreeWithdraw {
        public static final String METHOD_NAME = "查询用户是否有免提现手续费权限接口";
        public static final String METHOD_NOTE = "查询用户是否有免提现手续费权限接口";
        public static final String METHOD_API_USER_ID = "用户id";
    }

    public static class VerifyFreeTransaction {
        public static final String METHOD_NAME = "查询用户是否有免交易手续费权限接口";
        public static final String METHOD_NOTE = "查询用户是否有免交易手续费权限接口";
        public static final String METHOD_API_USER_ID = "用户id";
    }

    public static class VerifyFreePushAd {
        public static final String METHOD_NAME = "查询用户是否有发布广告权限接口";
        public static final String METHOD_NOTE = "查询用户是否有发布广告权限接口";
        public static final String METHOD_API_USER_ID = "用户id";
    }

    public static class SendUpdateWalletPassword {
        public static final String METHOD_NAME = "发送重置、修改资金密码验证码接口";
        public static final String METHOD_NOTE = "发送重置、修改资金密码验证码接口";
    }

    public static class UpdateWalletPassword {
        public static final String METHOD_NAME = "重置、修改资金密码验证码";
        public static final String METHOD_NOTE = "重置、修改资金密码验证码";
        public static final String METHOD_API_CODE = "验证码";
    }

    public static class selectUserInfoById {
        public static final String METHOD_NAME = "查询用户主体信息";
        public static final String METHOD_NOTE = "查询用户主体信息";
        public static final String METHOD_API_USERID = "用户id";
    }

    public static class ValidateSmsg {
        public static final String METHOD_TITLE_NAME = "验证修改资金密码短信验证码";
        public static final String METHOD_TITLE_NOTE = "验证短信验证码";
        public static final String METHOD_API_PHONE = "手机号";
        public static final String METHOD_API_VERIFYCODE = "验证码";
    }

    public static class VerifyWithdrawSms {
        public static final String METHOD_TITLE_NAME = "验证提现短信";
        public static final String METHOD_TITLE_NOTE = "验证提现短信";
        public static final String METHOD_API_PHONE = "账户";
        public static final String METHOD_API_VERIFYCODE = "验证码";
    }

    public static class hasLowAuthAndUserList {
        public static final String METHOD_TITLE_NAME = "检查用户初级认证和黑名单";
        public static final String METHOD_TITLE_NOTE = "不通过认证或存在黑名单则抛出异常";
        public static final String METHOD_API_USERID = "用户id";
    }

    public static class hasHighAuthAndUserList {
        public static final String METHOD_TITLE_NAME = "检查用户高级认证和黑名单";
        public static final String METHOD_TITLE_NOTE = "不通过认证或存在黑名单则抛出异常";
        public static final String METHOD_API_USERID = "用户id";
    }

    public static class GetUserAuthentication {
        public static final String METHOD_TITLE_NAME = "获取用户认证状态";
        public static final String METHOD_TITLE_NOTE = "获取用户认证状态";
        public static final String METHOD_API_USERID = "用户id";
    }

    public class SelectRelationByUserId {
        public static final String METHOD_TITLE_NAME = "根据用户id查询推荐关系";
        public static final String METHOD_TITLE_NOTE = "根据用户id查询推荐关系";
        public static final String METHOD_API_USERID = "用户id";
    }

    public class GetDirects {
        public static final String METHOD_TITLE_NAME = "根据用户id查询直推下级";
        public static final String METHOD_TITLE_NOTE = "根据用户id查询直推下级";
        public static final String METHOD_API_USERID = "用户id";
    }

    public class GetAllSubordinate {
        public static final String METHOD_TITLE_NAME = "根据用户id查询整个下级团队";
        public static final String METHOD_TITLE_NOTE = "根据用户id查询整个下级团队";
        public static final String METHOD_API_USERID = "用户id";
    }

    public class ListUserDirectTeam {
        public static final String METHOD_TITLE_NAME = "根据用户id查询直推下级信息列表";
        public static final String METHOD_TITLE_NOTE = "根据用户id查询直推下级信息列表";
        public static final String METHOD_API_USERID = "用户id";
    }

    public class NotifyOut {
        public static final String METHOD_TITLE_NAME = "用户提现短信通知";
        public static final String METHOD_TITLE_NOTE = "用户提现短信通知";
        public static final String NotifyOutSMS = "通知参数";
    }

}
