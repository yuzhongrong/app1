package com.blockchain.server.user.common.enums;

/**
 * @author huangxl
 * @data 2019/2/21 20:53
 */
public enum UserEnums {
    USER_EXISTS(1100, "该用户已存在", "The user already exists", "該用戶已存在"),
    USER_NOT_EXISTS(1101, "不存在该用户", "The user does not exist", "不存在該用戶"),
    LOGIN_PASSWORD_ERROR(1102, "用户名密码错误", "Wrong username and password", "用戶名密碼錯誤"),
    LOGIN_FORBIDDEN(1103, "你被列入黑名单，禁止登录", "You are blacklisted from logging in", "你被列入黑名單，禁止登錄"),
    SMS_VERIFY_FAIL(1104, "手机号验证码错误", "Wrong verification code for mobile phone number", "手機號驗證碼錯誤"),
    SMS_CODE_NOT_EXIST(1105, "请先获取验证码信息", "Please get the captcha information first", "請先獲取驗證碼資訊"),
    PHONE_FORMAT_ERROR(1106, "手机号格式不正确", "The phone number format is not correct", "手機號格式不正確"),
    FORMAT_ERROR(1106, "手机号或邮箱格式不正确", "The phone number or email format is not correct", "手機號或郵箱格式不正確"),
    VERIFY_CODE_TYPE_ERROR(1107, "没有此类型短信", "There is no text message of this type", "沒有此類型短信"),
    VERIFY_CODE_OVER_COUNT(1108, "今日获取验证码到达上限", "The upper limit of the captcha is reached today", "今日獲取驗證碼到達上限"),
    USER_PHONE_EXISTS(1109, "该手机号或邮箱已存在", "The phone number or email address already exists", "該手機號或郵箱已存在"),
    USER_PASSWORD_ERROR_FORMAT(1110, "密码只能是6-16位数字、字母和特殊字符的组合", "Passwords can only be 6-16 digit combinations of Numbers, letters, and special characters", "密碼只能是6-16位數字、字母和特殊字元的組合"),
    INVALID_INVITATION_CODE(1111, "无效的邀请码信息", "Invalid invitation code information", "無效的邀請碼資訊"),
    INVALID_NICK_NAME(1112, "昵称不能包含特殊字符", "Nicknames cannot contain special characters", "昵稱不能包含特殊字元"),
    INVALID_STRING(1112, "输入字符非法", "Invalid input character", "輸入字符非法"),
    PHONE_NOT_CHANGE(1113, "手机号码与当前用户一致", "The phone number is the same as the current user", "手機號碼與當前用戶一致"),
    GOOGLE_SECRET_KEY_FAIL(1114, "获取谷歌安全码失败", "Failed to get Google security code", "獲取穀歌安全碼失敗"),
    GOOGLE_SECRET_KEY_EXIST(1115, "你已绑定谷歌验证器，请勿重复绑定", "You have bound the Google validator. Do not repeat the binding", "你已綁定穀歌驗證器，請勿重複綁定"),
    GOOGLE_AUTH_FAIL(1116, "谷歌验证器验证失败", "Google verifier validation failed", "穀歌驗證器驗證失敗"),
    FILE_UPLOAD_ERROR(1117, "文件上传失败", "File upload failed", "文檔上傳失敗"),
    FILE_UPLOAD_LIMIT(1118, "文件上传失败，上传文件次数过多！", "File upload failed, too many times!", "文檔上傳失敗，上傳檔次數過多！"),
    FILE_UPLOAD_FORMAT_ERROR(1119, "文件地址错误", "File address error", "文檔地址錯誤"),
    FILE_UPLOAD_DEFICIENCY(1120, "请先上传文件", "Please upload the file first", "請先上傳文檔"),
    AUTH_WAIT(1121, "认证审核中，请勿重复申请！", "Please do not repeat the application during the certification audit!", "認證審核中，請勿重複申請！"),
    AUTH_YES(1122, "你已完成认证！", "You have completed the certification!", "你已完成認證！"),
    AUTH_BASIC_BEFORE(1123, "请先完成初级认证！", "Please complete the primary certification first!", "請先完成初級認證！"),
    EMAIL_FORMAT_ERROR(1124, "无效的邮箱格式！", "Invalid mailbox format!", "無效的郵箱格式！"),
    EMAIL_VERIFY_FAIL(1125, "邮箱验证失败！", "Mailbox authentication failed!", "郵箱驗證失敗！"),
    EMAIL_BIND_REPEAT(1126, "绑定失败，你已绑定邮箱", "Failed to bind. You have bound your mailbox", "綁定失敗，你已綁定郵箱"),
    PASSWORD_EXIST(1127, "设置失败，你已经设置过密码了", "Setup failed. You have already set the password", "設置失敗，你已經設置過密碼了"),
    EMAIL_EXIST(1128, "绑定失败，该邮箱已被绑定！", "The mailbox has been bound!", "綁定失敗，該郵箱已被綁定！"),
    PASSWORD_NOT_MATCH(1129, "密码不匹配", "Password mismatch", "密碼不匹配"),
    TRANSACTION_FORBIDDEN(1130, "你被列入黑名单，禁止交易！", "You're blacklisted! No trading!", "你被列入黑名單，禁止交易！"),
    CANNOT_FOUND_INTERNATIONAL(1131, "找不到该国家信息", "Country information not available", "找不到該國家信息"),
    VERIFY_CODE_DID_NOT_FIND(1034, "验证码已过期或没有获取", "The captcha is expired or not available", "驗證碼已過期或沒有獲取"),
    VERIFY_CODE_DID_NOT_MATCH(1035, "您输入的验证码不匹配", "The verification code you entered does not match", "您輸入的驗證碼不匹配"),
    WITHDRAW_FORBIDDEN(1136, "你被列入黑名单，禁止提现！", "You are blacklisted and no withdrawal is allowed!", "你被列入黑名單，禁止提現！"),
    TRANSACTION_NOT_PASS_HIGH_AUTH(1137, "操作失败，您未通过高级认证！", "Operation failed. You did not pass advanced certification!", "操作失敗，您未通過高級認證！"),
    TRANSACTION_NOT_PASS_LOW_AUTH(1137, "操作失败，您未通过初级认证！", "Operation failed. You did not pass primary certification!", "操作失敗，您未通過初級認證！"),
    SEND_CODE_ERROR(1138, "验证码发送失败！", "Verification code failed to send!", "驗證碼發送失敗！"),
    TENCENT_CAPTCHA_ERROR(1138, "拼图验证码校验失败，请稍后重试", "Puzzle verification code check failed, please try again later", "拼圖驗證碼校驗失敗，請稍後重試"),
    SMS_IP_LIMIT(1144, "相同ip请求超过限制，请稍后重试", "The same IP request exceeded the limit. Please try again later.", "相同ip請求超過限制，請稍後重試"),
    REQUEST_TOO_BUSY(1143, "请求过于频繁，请稍后重试", "Requests are too frequent. Please try again later.", "請求過於頻繁，請稍後重試"),
    SMSG_BEHAVIOR_INSPECT(1145,"短信行为验证失败！请确保你的app版本为最新。","SMS behavior verification failed！","短信行為驗證失敗！"),
    BOSE64_ERROR(1146,"图片解析失败","Image parsing failed！","圖片解析失敗"),
    USER_LEVEL (1147,"请先完成个人一级认证!","Please complete individual level 1 certification first ！","請先完成個人一級認證！"),
    USER_NO_IDCARD (1149,"个人信息和身份证信息不符合!","The photo does not match the id information ！","照片和身份證信息不符合！"),
    FAILED (1150,"失败!","failed ！","失败！"),
    SEND_CODE_INTERVAL_ERROR(1151, "请间隔15分钟，再次获取邮件验证码！", "Please get the mail verification code again 15 minutes apart!", "請間隔15分鐘，再次獲取郵件驗證碼！"),

    ;


    private int code;
    private String hkmsg;
    private String enMsg;
    private String cnmsg;

    UserEnums(int code, String cnmsg, String enMsg, String hkmsg) {
        this.code = code;
        this.cnmsg = cnmsg;
        this.enMsg = enMsg;
        this.hkmsg = hkmsg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getHkmsg() {
        return hkmsg;
    }

    public void setHkmsg(String hkmsg) {
        this.hkmsg = hkmsg;
    }

    public String getEnMsg() {
        return enMsg;
    }

    public void setEnMsg(String enMsg) {
        this.enMsg = enMsg;
    }

    public String getCnmsg() {
        return cnmsg;
    }

    public void setCnmsg(String cnmsg) {
        this.cnmsg = cnmsg;
    }
}
