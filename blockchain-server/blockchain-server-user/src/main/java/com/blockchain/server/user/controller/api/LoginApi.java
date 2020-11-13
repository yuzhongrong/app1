package com.blockchain.server.user.controller.api;

/**
 * @author huangxl
 * @create 2018-11-16 14:51
 */
public class LoginApi {
    public static final String CONTROLLER_API = "登陆注册控制器";

    public static class PassWorldLogin {
        public static final String METHOD_NAME = "密码登陆接口";
        public static final String METHOD_NOTE = "手机号+密码登陆接口";
        public static final String METHOD_API_TEL = "手机号";
        public static final String METHOD_API_PASS = "密码";
        public static final String METHOD_API_CLIENT_ID = "客户端标识";
    }

    public static class SmsCodeLogin {
        public static final String METHOD_NAME = "验证码登陆接口";
        public static final String METHOD_NOTE = "手机号+验证码登陆接口";
        public static final String METHOD_API_TEL = "手机号";
        public static final String METHOD_API_CODE = "验证码";
        public static final String METHOD_API_CLIENT_ID = "客户端标识";
    }

    public static class Register {
        public static final String METHOD_NAME = "注册接口";
        public static final String METHOD_NOTE = "注册接口";
        public static final String METHOD_API_TEL = "手机号";
        public static final String METHOD_API_CODE = "验证码";
        public static final String METHOD_API_PASSWORD = "密码";
        public static final String METHOD_API_INVITATION_CODE = "邀请码";
        public static final String METHOD_API_INTERNATIONAL_CODE = "国际标识号";
        public static final String METHOD_API_NICK_NAME = "昵称";
        public static final String METHOD_API_CLIENT_ID = "客户端标识";
        public static final String TICKET = "滑块验证码 ticket";
        public static final String RANDSTR = "滑块验证码 randstr";
    }

    public static class SendRegisterSmsCode {
        public static final String METHOD_NAME = "发送注册验证码";
        public static final String METHOD_NOTE = "发送注册验证码";
        public static final String METHOD_API_TEL = "手机号";
        public static final String METHOD_API_INTERNATIONAL_CODE = "国际标识号";
    }

    public static class SendLoginSmsCode {
        public static final String METHOD_NAME = "发送登录验证码";
        public static final String METHOD_NOTE = "发送登录验证码";
        public static final String METHOD_API_TEL = "手机号";
        public static final String METHOD_API_INTERNATIONAL_CODE = "国际标识号";
    }

    public static class Loginout {
        public static final String METHOD_NAME = "退出登录";
        public static final String METHOD_NOTE = "退出登录";
    }

    public static class PassWorldLoginPC {
        public static final String METHOD_NAME = "PC端密码登陆接口";
        public static final String METHOD_NOTE = "手机号+密码登陆接口";
        public static final String METHOD_API_TEL = "手机号";
        public static final String METHOD_API_PASS = "密码";
    }

    public static class SmsCodeLoginPC {
        public static final String METHOD_NAME = "PC端验证码登陆接口";
        public static final String METHOD_NOTE = "手机号+验证码登陆接口";
        public static final String METHOD_API_TEL = "手机号";
        public static final String METHOD_API_CODE = "验证码";
    }

    public static class RegisterPC {
        public static final String METHOD_NAME = "PC端注册接口";
        public static final String METHOD_NOTE = "PC端注册接口";
        public static final String METHOD_API_TEL = "手机号";
        public static final String METHOD_API_CODE = "验证码";
        public static final String METHOD_API_PASSWORD = "密码";
        public static final String METHOD_API_INVITATION_CODE = "邀请码";
        public static final String METHOD_API_INTERNATIONAL_CODE = "国际标识号";
        public static final String METHOD_API_NICK_NAME = "昵称";
    }

    public static class LoginoutPC {
        public static final String METHOD_NAME = "PC端退出登录";
        public static final String METHOD_NOTE = "PC端退出登录";
    }

    public static class SetForgetPasswordCode {
        public static final String METHOD_NAME = "忘记密码发送验证码接口";
        public static final String METHOD_NOTE = "忘记密码发送验证码";
        public static final String METHOD_API_CODE = "国际区号";
        public static final String METHOD_API_TEL = "手机号";
    }

    public static class ForgetPassword {
        public static final String METHOD_NAME = "忘记密码,修改密码接口";
        public static final String METHOD_NOTE = "忘记密码,修改密码接口";
        public static final String METHOD_API_PASSWORD = "密码";
        public static final String METHOD_API_CODE = "验证码";
        public static final String METHOD_API_TEL = "手机号";
    }

}
