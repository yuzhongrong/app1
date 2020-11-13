package com.blockchain.server.user.controller.api;

/**
 * @author xusm
 * @data 2019/2/21 20:10
 */
public class UserApi {
    public static final String CONTROLLER_API = "登陆注册控制器";

    public static class UpdateNickName {
        public static final String METHOD_NAME = "修改昵称接口";
        public static final String METHOD_NOTE = "根据当前登录用户修改昵称信息";
        public static final String METHOD_API_NICKNAME = "昵称";
    }

    public static class UpdateTel {
        public static final String METHOD_NAME = "修改手机号接口";
        public static final String METHOD_NOTE = "根据当前登录用户修改手机号信息";
        public static final String METHOD_API_TEL = "昵称";
        public static final String METHOD_API_INTERNATIONAL_CODE = "国际标识号";
        public static final String METHOD_API_CODE = "验证码";
    }

    public static class SendChangePhoneCode {
        public static final String METHOD_NAME = "发送更换手机号验证码";
        public static final String METHOD_NOTE = "发送更换手机号验证码";
        public static final String METHOD_API_TEL = "手机号";
        public static final String METHOD_API_INTERNATIONAL_CODE = "国际标识号";
    }

    public static class GenerateGoogleKey {
        public static final String METHOD_NAME = "获取google验证码key";
        public static final String METHOD_NOTE = "获取google验证码key";
    }

    public static class BindGoogleKey {
        public static final String METHOD_NAME = "绑定谷歌验证器";
        public static final String METHOD_NOTE = "绑定谷歌验证器";
        public static final String METHOD_API_KEY = "验证器安全码";
        public static final String METHOD_API_CODE = "验证码";
    }

    public static class UploadIdentityImgFile {
        public static final String METHOD_NAME = "身份认证图片上传";
        public static final String METHOD_NOTE = "身份认证图片上传";
        public static final String METHOD_API_IMG = "上传身份证";
    }

    public static class HeadImgUploadFile {
        public static final String METHOD_NAME = "头像上传";
        public static final String METHOD_NOTE = "头像上传";
        public static final String METHOD_API_IMG = "上传头像";
    }

    public static class ReplaceHeadImg {
        public static final String METHOD_NAME = "更换头像";
        public static final String METHOD_NOTE = "更换头像";
        public static final String METHOD_API_IMG = "头像地址";
    }

    public static class Me {
        public static final String METHOD_NAME = "查询我的个人信息";
        public static final String METHOD_NOTE = "查询我的个人信息";
    }

    public static class CommitPrimaryAuthApply {
        public static final String METHOD_NAME = "提交初级认证申请接口";
        public static final String METHOD_NOTE = "提交初级认证申请接口";
        public static final String METHOD_API_REAL_NAME = "真实姓名";
        public static final String METHOD_API_ID_NUMBER = "身份证号码";
        public static final String METHOD_API_TYPE = "证件类型";
        public static final String METHOD_API_NATIONALITY = "国籍";
        public static final String METHOD_API_IMGS = "身份证保存地址，以「,」分隔";
    }

    public static class HighAuthApply {
        public static final String METHOD_NAME = "提交高级认证申请接口";
        public static final String METHOD_NOTE = "提交高级认证申请接口";
        public static final String METHOD_API_IMG = "文件保存地址";
    }

    public static class SendEmailCode {
        public static final String METHOD_NAME = "获取email验证码接口";
        public static final String METHOD_NOTE = "获取email验证码接口";
        public static final String METHOD_API_EMAIL = "email";
    }

    public static class BindEmail {
        public static final String METHOD_NAME = "获取email验证码接口";
        public static final String METHOD_NOTE = "获取email验证码接口";
        public static final String METHOD_API_EMAIL = "email";
        public static final String METHOD_API_CODE = "邮箱验证码";
    }

    public static class SendPasswordCode {
        public static final String METHOD_NAME = "首次设置密码发送验证码";
        public static final String METHOD_NOTE = "首次设置密码发送验证码";
    }

    public static class SetPassword {
        public static final String METHOD_NAME = "首次设置密码接口";
        public static final String METHOD_NOTE = "首次设置密码接口";
        public static final String METHOD_API_PASSWORD = "密码";
        public static final String METHOD_API_CODE = "验证码";
    }

    public static class UpdatePassword {
        public static final String METHOD_NAME = "修改密码接口";
        public static final String METHOD_NOTE = "修改密码接口";
        public static final String METHOD_API_PASSWORD = "密码";
        public static final String METHOD_API_OLD_PASSWORD = "旧密码";
    }

    public static class UpdateHighAuth {
        public static final String METHOD_NAME = "上传高级认证文件接口";
        public static final String METHOD_NOTE = "上传高级认证文件接口";
        public static final String METHOD_API_FILE = "文件";
    }

    public static class SendMoneyPasswordSmsg {
        public static final String METHOD_TITLE_NAME = "发送修改资金密码短信验证码接口";
        public static final String METHOD_TITLE_NOTE = "发送修改资金密码短信验证码接口";
    }

    public static class SendWithdrawSms {
        public static final String METHOD_TITLE_NAME = "发送提现短信验证码";
        public static final String METHOD_TITLE_NOTE = "发送提现短信验证码";
    }

    public static class SetForgetPasswordCode {
        public static final String METHOD_NAME = "忘记密码发送验证码接口";
        public static final String METHOD_NOTE = "忘记密码发送验证码";
    }

    public static class ForgetPassword {
        public static final String METHOD_NAME = "忘记密码,修改密码接口";
        public static final String METHOD_NOTE = "忘记密码,修改密码接口";
        public static final String METHOD_API_PASSWORD = "密码";
        public static final String METHOD_API_CODE = "验证码";
    }

    public static class JudgeAuthentication {
        public static final String METHOD_API_NAME = "判断用户是否认证";
        public static final String METHOD_API_NOTE = "判断用户是否认证";

        public static final String METHOD_API_USER_ID = "用户id";
        public static final String METHOD_API_AUTHENTICATION_TYPE = "审核标识符";
    }
}
