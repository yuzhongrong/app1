package com.blockchain.server.user.controller;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.common.base.dto.SessionUserDTO;
import com.blockchain.common.base.util.HttpRequestUtil;
import com.blockchain.common.base.util.SSOHelper;
import com.blockchain.server.user.common.constants.other.InternationalConstant;
import com.blockchain.server.user.common.constants.other.RedisConstant;
import com.blockchain.server.user.common.constants.other.UserFileUploadConstant;
import com.blockchain.server.user.common.constants.sql.ConfigConstant;
import com.blockchain.server.user.common.enums.SmsCountEnum;
import com.blockchain.server.user.common.enums.UserEnums;
import com.blockchain.server.user.common.exceprion.UserException;
import com.blockchain.server.user.common.utils.*;
import com.blockchain.server.user.controller.api.UserApi;
import com.blockchain.server.user.controller.api.UserAuthenticationApi;
import com.blockchain.server.user.dto.UserBaseDTO;
import com.blockchain.server.user.entity.UserMain;
import com.blockchain.server.user.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author huangxl
 * @create 2019-02-24 10:36
 */
@RestController
@Api(UserApi.CONTROLLER_API)
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private UserLoginService userLoginService;
    @Autowired
    private UserMainService userMainService;
    @Autowired
    private SmsCountService smsCountService;
    @Autowired
    private SmsCodeUtils smsCodeUtils;
    @Autowired
    private EmailCodeUtils emailCodeUtils;
    @Autowired
    private ConfigService configService;
    @Autowired
    private AuthenticationApplyService authenticationApplyService;

    @Autowired
    private EMailTransmitHelper eMailTransmitHelper;
    @Autowired
    private RedisTemplate redisTemplate;

    @Value("${FILES_DIR.ROOT}")
    private String FILE_ROOT_PATH;//文件上传根目录


    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);




    /**
     * 查询用户高级未认证原因
     *
     * @param userId
     * @return
     */
    // @RequiresPermissions("app:userAuthentication:selectLowAuth")
    @ApiOperation(value = UserAuthenticationApi.SelectLowAuth.METHOD_API_NAME, notes = UserAuthenticationApi.SelectLowAuth.METHOD_API_NOTE)
    @PostMapping("/selectHighRemark")
    public ResultDTO selectHighRemark(@ApiParam(UserAuthenticationApi.SelectHighAuth.METHOD_API_USER_ID) @RequestParam("userId") String userId) {
        return ResultDTO.requstSuccess(authenticationApplyService.selectHighRemarkByUserId(userId));
    }


    /**
     * 查询初级未认证原因
     *
     * @param userId
     * @return
     */
    // @RequiresPermissions("app:userAuthentication:selectLowAuth")
    @ApiOperation(value = UserAuthenticationApi.SelectLowAuth.METHOD_API_NAME, notes = UserAuthenticationApi.SelectLowAuth.METHOD_API_NOTE)
    @PostMapping("/selectLowRemark")
    public ResultDTO selectLowRemark(@ApiParam(UserAuthenticationApi.SelectLowAuth.METHOD_API_USER_ID) @RequestParam("userId") String userId) {
        return ResultDTO.requstSuccess(authenticationApplyService.selectLowRemarkByUserId(userId));
    }

    @PostMapping("/updateNickName")
    @ApiOperation(value = UserApi.UpdateNickName.METHOD_NAME,
            notes = UserApi.UpdateNickName.METHOD_NOTE)
    public ResultDTO updateNickName(@ApiParam(UserApi.UpdateNickName.METHOD_API_NICKNAME) @RequestParam(name = "nickName") String nickName,
                                    HttpServletRequest request) {
        String userId = SSOHelper.getUserId(redisTemplate, request);
        userMainService.updateNickName(userId, nickName);
        return ResultDTO.requstSuccess();
    }

    @PostMapping("/updateTel")
    @ApiOperation(value = UserApi.UpdateTel.METHOD_NAME,
            notes = UserApi.UpdateTel.METHOD_NOTE)
    public ResultDTO updateTel(@ApiParam(UserApi.UpdateTel.METHOD_API_TEL) @RequestParam(name = "tel") String tel,
                               @ApiParam(UserApi.UpdateTel.METHOD_API_CODE) @RequestParam(name = "code") String code,
                               @ApiParam(UserApi.UpdateTel.METHOD_API_INTERNATIONAL_CODE) @RequestParam(value = "internationalCode", required = false, defaultValue = InternationalConstant.DEFAULT_CODE) String internationalCode,
                               HttpServletRequest request) {
        smsCodeUtils.validateVerifyCode(code, tel, SmsCountEnum.SMS_COUNT_UPDATE_ACCOUNT);//校验验证码是否正确
        SessionUserDTO user = SSOHelper.getUser(redisTemplate, request);
        userMainService.updateTel(user.getId(), tel, internationalCode);
        smsCodeUtils.removeKey(tel, SmsCountEnum.SMS_COUNT_UPDATE_ACCOUNT);//删除验证码key
        //更新session信息
        user.setTel(tel);
        SSOHelper.setUser(user, redisTemplate);
        return ResultDTO.requstSuccess();
    }

    @PostMapping("/sendChangePhoneCode")
    @ApiOperation(value = UserApi.SendChangePhoneCode.METHOD_NAME,
            notes = UserApi.SendChangePhoneCode.METHOD_NOTE)
    public ResultDTO sendChangePhoneCode(@ApiParam(UserApi.SendChangePhoneCode.METHOD_API_TEL) @RequestParam(name = "tel") String tel,
                                         @ApiParam(UserApi.SendChangePhoneCode.METHOD_API_INTERNATIONAL_CODE) @RequestParam(value = "internationalCode", required = false, defaultValue = InternationalConstant.DEFAULT_CODE) String internationalCode,
                                         HttpServletRequest request) {
        String userId = SSOHelper.getUserId(redisTemplate, request);
        userMainService.handleSendChangePhoneCodeBefore(userId, tel);
        smsCountService.handleInsertSmsCode(tel, internationalCode, SmsCountEnum.SMS_COUNT_UPDATE_ACCOUNT);
        return ResultDTO.requstSuccess();
    }

    @PostMapping("/setPwCode")
    @ApiOperation(value = UserApi.SendPasswordCode.METHOD_NAME,
            notes = UserApi.SendPasswordCode.METHOD_NOTE)
    public ResultDTO sendFirstPwCode(HttpServletRequest request) {
        String userId = SSOHelper.getUserId(redisTemplate, request);
        UserMain userMain = userMainService.selectById(userId);
//        smsCountService.handleInsertSmsCode(userMain.getMobilePhone(), userMain.getInternationalCode(), SmsCountEnum.SMS_COUNT_SET_PASSWORD);

        if (CheckUtils.checkMobilePhone(userMain.getMobilePhone())) {
            smsCountService.handleInsertSmsCode(userMain.getMobilePhone(), userMain.getInternationalCode(), SmsCountEnum.SMS_COUNT_SET_PASSWORD);
        } else if (CheckUtils.checkEmail(userMain.getMobilePhone())) {
            emailCodeUtils.sendSmsCodeAndStoreToCache(userMain.getMobilePhone(), getUserLocale(request), SmsCountEnum.SMS_COUNT_SET_PASSWORD);
        } else {
            throw new UserException(UserEnums.FORMAT_ERROR);
        }
        return ResultDTO.requstSuccess();
    }

    @GetMapping("/getGoogleKey")
    @ApiOperation(value = UserApi.GenerateGoogleKey.METHOD_NAME,
            notes = UserApi.GenerateGoogleKey.METHOD_NOTE)
    public ResultDTO getGoogleKey() {
        String key = GoogleAuthenticatorUtils.generateSecretKey();
        if (key == null) {
            throw new UserException(UserEnums.GOOGLE_SECRET_KEY_FAIL);
        }
        return ResultDTO.requstSuccess(key);
    }

    @PostMapping("/bindGoogle")
    @ApiOperation(value = UserApi.BindGoogleKey.METHOD_NAME,
            notes = UserApi.BindGoogleKey.METHOD_NOTE)
    public ResultDTO bindGoogleKey(@ApiParam(UserApi.BindGoogleKey.METHOD_API_KEY) @RequestParam(name = "key") String key,
                                   @ApiParam(UserApi.BindGoogleKey.METHOD_API_CODE) @RequestParam(value = "code") Long code,
                                   HttpServletRequest request) {
        String userId = SSOHelper.getUserId(redisTemplate, request);
        userInfoService.handleBindGoogleAuthenticator(userId, key, code);
        return ResultDTO.requstSuccess();
    }

    @ApiOperation(value = UserApi.UploadIdentityImgFile.METHOD_NAME, notes = UserApi.UploadIdentityImgFile.METHOD_NOTE)
    @PostMapping(value = "/identityUploadFile")
    public ResultDTO identityUploadFile(@ApiParam(UserApi.UploadIdentityImgFile.METHOD_API_IMG) @RequestParam(value = "img") String img, HttpServletRequest request) {
        //身份认证文件保存到本地/服务器
        String fileNames = generateImage(img, UserFileUploadConstant.UPLOAD_IDENTITY, request);
        return ResultDTO.requstSuccess(fileNames);
    }

    @ApiOperation(value = UserApi.UploadIdentityImgFile.METHOD_NAME, notes = UserApi.UploadIdentityImgFile.METHOD_NOTE)
    @PostMapping(value = "/pcIdentityUploadFile")
    public ResultDTO pcIdentityUploadFile(@ApiParam(UserApi.UploadIdentityImgFile.METHOD_API_IMG) @RequestParam(value = "file") MultipartFile file, HttpServletRequest request) {
        //身份认证文件保存到本地/服务器
        String fileNames = uploadFile(file, UserFileUploadConstant.UPLOAD_IDENTITY, request);// generateImage(img, UserFileUploadConstant.UPLOAD_IDENTITY, request);
        return ResultDTO.requstSuccess(fileNames);
    }

    @ApiOperation(value = UserApi.HeadImgUploadFile.METHOD_NAME, notes = UserApi.HeadImgUploadFile.METHOD_NOTE)
    @RequestMapping(value = "/headImgUploadFile", method = RequestMethod.POST)
    public ResultDTO handleHeadImgUploadFile(@ApiParam(UserApi.HeadImgUploadFile.METHOD_API_IMG) @RequestParam(value = "img") String img, HttpServletRequest request) {
        //返回的状态信息
        //获取图片文件
        String imgName = generateImage(img, UserFileUploadConstant.UPLOAD_HEAD_IMG, request);
        return ResultDTO.requstSuccess(imgName);
    }

    @ApiOperation(value = UserApi.ReplaceHeadImg.METHOD_NAME, notes = UserApi.ReplaceHeadImg.METHOD_NOTE)
    @RequestMapping(value = "/replaceHeadImg", method = RequestMethod.POST)
    public ResultDTO replaceHeadImg(@ApiParam(UserApi.ReplaceHeadImg.METHOD_API_IMG) @RequestParam(value = "img") String img, HttpServletRequest request) {
        String userId = SSOHelper.getUserId(redisTemplate, request);
        userInfoService.updateUserHeadImg(userId, img);
        return ResultDTO.requstSuccess();
    }

    @ApiOperation(value = UserApi.CommitPrimaryAuthApply.METHOD_NAME, notes = UserApi.CommitPrimaryAuthApply.METHOD_NOTE)
    @RequestMapping(value = "/saveIdcardVali", method = RequestMethod.POST)
    public ResultDTO commitPrimaryAuthApply(@ApiParam(UserApi.CommitPrimaryAuthApply.METHOD_API_REAL_NAME) @RequestParam("realName") String realName,
                                            @ApiParam(UserApi.CommitPrimaryAuthApply.METHOD_API_ID_NUMBER) @RequestParam("idNumber") String idNumber,
                                            @ApiParam(UserApi.CommitPrimaryAuthApply.METHOD_API_TYPE) @RequestParam(value = "type", required = false) String type,
                                            @ApiParam(UserApi.CommitPrimaryAuthApply.METHOD_API_IMGS) @RequestParam("imgs") String imgs, HttpServletRequest request) {
        String userId = SSOHelper.getUserId(redisTemplate, request);
        authenticationApplyService.insertBasicAuth(userId, realName, idNumber, type, imgs,false);
        return ResultDTO.requstSuccess();
    }

    @ApiOperation(value = UserApi.HighAuthApply.METHOD_NAME, notes = UserApi.HighAuthApply.METHOD_NOTE)
    @RequestMapping(value = "/saveHighAuth", method = RequestMethod.POST)
    public ResultDTO commitHighAuthApply(@ApiParam(UserApi.HighAuthApply.METHOD_API_IMG) @RequestParam("img") String img, HttpServletRequest request) {
        String userId = SSOHelper.getUserId(redisTemplate, request);
        authenticationApplyService.insertHighAuth(userId, img,false);
        return ResultDTO.requstSuccess();
    }

    @ApiOperation(value = UserApi.Me.METHOD_NAME, notes = UserApi.Me.METHOD_NOTE)
    @RequestMapping(value = "/me", method = RequestMethod.POST)
    public ResultDTO me(HttpServletRequest request) {
        String userId = SSOHelper.getUserId(redisTemplate, request);
        UserBaseDTO userBaseDTO = userMainService.selectUserInfoById(userId);
        return ResultDTO.requstSuccess(userBaseDTO);
    }

    @GetMapping("/sendEmailCode")
    @ApiOperation(value = UserApi.SendEmailCode.METHOD_NAME,
            notes = UserApi.SendEmailCode.METHOD_NOTE)
    public ResultDTO sendEmailCode(@ApiParam(UserApi.SendEmailCode.METHOD_API_EMAIL) @RequestParam("email") String email, HttpServletRequest request) {
        boolean check = CheckUtils.checkEmail(email);
        if (!check) {
            throw new UserException(UserEnums.EMAIL_FORMAT_ERROR);
        }
        //检查是否有重复邮箱信息
        userInfoService.checkRepeatEmail(email);
        emailCodeUtils.sendSmsCodeAndStoreToCache(email, HttpRequestUtil.getUserLocale(request), SmsCountEnum.SMS_COUNT_BIND);
        return ResultDTO.requstSuccess();
    }

    @GetMapping("/bindEmail")
    @ApiOperation(value = UserApi.BindEmail.METHOD_NAME,
            notes = UserApi.BindEmail.METHOD_NOTE)
    public ResultDTO verifyEmailCode(@ApiParam(UserApi.BindEmail.METHOD_API_EMAIL) @RequestParam("email") String email,
                                     @ApiParam(UserApi.BindEmail.METHOD_API_CODE) @RequestParam("code") String code,
                                     HttpServletRequest request) {
        String userId = SSOHelper.getUserId(redisTemplate, request);
        userInfoService.handleBindEmail(userId, email, code);
        return ResultDTO.requstSuccess();
    }

    @GetMapping("/setPassword")
    @ApiOperation(value = UserApi.SetPassword.METHOD_NAME,
            notes = UserApi.SetPassword.METHOD_NOTE)
    public ResultDTO setPassword(@ApiParam(UserApi.SetPassword.METHOD_API_PASSWORD) @RequestParam("password") String password,
                                 @ApiParam(UserApi.SetPassword.METHOD_API_CODE) @RequestParam("code") String code,
                                 HttpServletRequest request) {
        SessionUserDTO user = SSOHelper.getUser(redisTemplate, request);
        smsCodeUtils.validateVerifyCode(code, user.getTel(), SmsCountEnum.SMS_COUNT_SET_PASSWORD);
        userLoginService.insertPassword(user.getId(), password);
        smsCodeUtils.removeKey(user.getTel(), SmsCountEnum.SMS_COUNT_SET_PASSWORD);
        return ResultDTO.requstSuccess();
    }

    @GetMapping("/updatePassword")
    @ApiOperation(value = UserApi.UpdatePassword.METHOD_NAME,
            notes = UserApi.UpdatePassword.METHOD_NOTE)
    public ResultDTO updatePassword(@ApiParam(UserApi.UpdatePassword.METHOD_API_PASSWORD) @RequestParam("password") String password,
                                    @ApiParam(UserApi.UpdatePassword.METHOD_API_OLD_PASSWORD) @RequestParam("oldPassword") String oldPassword,
                                    HttpServletRequest request) {
        String userId = SSOHelper.getUserId(redisTemplate, request);
        userLoginService.updatePassword(userId, password, oldPassword);
        return ResultDTO.requstSuccess();
    }

    @ApiOperation(value = UserApi.UpdateHighAuth.METHOD_NAME, notes = UserApi.UpdateHighAuth.METHOD_NOTE)
    @RequestMapping(value = "/uploadHighAuthFile", method = RequestMethod.POST)
    public ResultDTO uploadHighAuthFile(@ApiParam(value = UserApi.UpdateHighAuth.METHOD_API_FILE) @RequestParam("file") String img, HttpServletRequest request) {
        String fileNames = generateImage(img, UserFileUploadConstant.UPLOAD_HIGH_AUTH, request);
        return ResultDTO.requstSuccess(fileNames);
    }

    @ApiOperation(value = UserApi.UpdateHighAuth.METHOD_NAME, notes = UserApi.UpdateHighAuth.METHOD_NOTE)
    @RequestMapping(value = "/pcUploadHighAuthFile", method = RequestMethod.POST)
    public ResultDTO pcUploadHighAuthFile(@ApiParam(value = UserApi.UpdateHighAuth.METHOD_API_FILE) @RequestParam(value = "file") MultipartFile file, HttpServletRequest request) {
        String fileNames = uploadFile(file, UserFileUploadConstant.UPLOAD_HIGH_AUTH, request);
        return ResultDTO.requstSuccess(fileNames);
    }

    @ApiOperation(value = UserApi.SendMoneyPasswordSmsg.METHOD_TITLE_NAME, notes = UserApi.SendMoneyPasswordSmsg.METHOD_TITLE_NOTE)
    @GetMapping("/sendMoneyPasswordSmsg")
    public ResultDTO sendMoneyPasswordSmsg(HttpServletRequest request) {
        String userId = SSOHelper.getUserId(redisTemplate, request);
        UserMain userMain = userMainService.selectById(userId);
        if (CheckUtils.checkMobilePhone(userMain.getMobilePhone())) {
            smsCountService.handleInsertSmsCode(userMain.getMobilePhone(), userMain.getInternationalCode(), SmsCountEnum.SMS_COUNT_UPDATE_WALLET_PASSWORD);
        } else if (CheckUtils.checkEmail(userMain.getMobilePhone())) {
            emailCodeUtils.sendSmsCodeAndStoreToCache(userMain.getMobilePhone(), getUserLocale(request), SmsCountEnum.SMS_COUNT_UPDATE_WALLET_PASSWORD);
        } else {
            throw new UserException(UserEnums.FORMAT_ERROR);
        }
//        smsCodeUtils.sendSmsCodeAndStoreToCache(userMain.getMobilePhone(), userMain.getInternationalCode(), SmsCountEnum.SMS_COUNT_UPDATE_WALLET_PASSWORD);
        return ResultDTO.requstSuccess();
    }

    @ApiOperation(value = UserApi.SendWithdrawSms.METHOD_TITLE_NAME, notes = UserApi.SendWithdrawSms.METHOD_TITLE_NOTE)
    @GetMapping("/sendWithdrawSms")
    public ResultDTO sendWithdrawSms(HttpServletRequest request) {
        String userId = SSOHelper.getUserId(redisTemplate, request);
        UserMain userMain = userMainService.selectById(userId);
        if (CheckUtils.checkMobilePhone(userMain.getMobilePhone())) {
            smsCountService.handleInsertSmsCode(userMain.getMobilePhone(), userMain.getInternationalCode(), SmsCountEnum.SMS_WITHDRAW);
        } else if (CheckUtils.checkEmail(userMain.getMobilePhone())) {
            emailCodeUtils.sendSmsCodeAndStoreToCache(userMain.getMobilePhone(), getUserLocale(request), SmsCountEnum.SMS_WITHDRAW);
        } else {
            throw new UserException(UserEnums.FORMAT_ERROR);
        }
        return ResultDTO.requstSuccess();
    }

    /**
     * 登陆后的忘记密码，可以直接从redis拿到用户手机信息
     */
    @GetMapping("/resetPassword")
    @ApiOperation(value = UserApi.ForgetPassword.METHOD_NAME,
            notes = UserApi.ForgetPassword.METHOD_NOTE)
    public ResultDTO resetPassword(@ApiParam(UserApi.ForgetPassword.METHOD_API_PASSWORD) @RequestParam("password") String password,
                                   @ApiParam(UserApi.ForgetPassword.METHOD_API_CODE) @RequestParam("code") String code,
                                   HttpServletRequest request) {
        SessionUserDTO user = SSOHelper.getUser(redisTemplate, request);
        smsCodeUtils.validateVerifyCode(code, user.getTel(), SmsCountEnum.SMS_COUNT_FORGET_PASSWORD);
        userLoginService.updatePassword(user.getId(), password);
        smsCodeUtils.removeKey(user.getTel(), SmsCountEnum.SMS_COUNT_FORGET_PASSWORD);
        return ResultDTO.requstSuccess();
    }

    /**
     * 登陆后的忘记密码，可以直接从redis拿到用户手机信息和国际区号
     */
    @PostMapping("/setForgetCode")
    @ApiOperation(value = UserApi.SetForgetPasswordCode.METHOD_NAME,
            notes = UserApi.SetForgetPasswordCode.METHOD_NOTE)
    public ResultDTO sendForgetPwCode(HttpServletRequest request) {
        String userId = SSOHelper.getUserId(redisTemplate, request);
        UserMain userMain = userMainService.selectById(userId);
        if (CheckUtils.checkMobilePhone(userMain.getMobilePhone())) {
            smsCountService.handleInsertSmsCode(userMain.getMobilePhone(), userMain.getInternationalCode(), SmsCountEnum.SMS_COUNT_FORGET_PASSWORD);
        } else if (CheckUtils.checkEmail(userMain.getMobilePhone())) {
            emailCodeUtils.sendSmsCodeAndStoreToCache(userMain.getMobilePhone(), getUserLocale(request), SmsCountEnum.SMS_COUNT_FORGET_PASSWORD);
        } else {
            throw new UserException(UserEnums.FORMAT_ERROR);
        }
//        smsCountService.handleInsertSmsCode(userMain.getMobilePhone(), userMain.getInternationalCode(), SmsCountEnum.SMS_COUNT_FORGET_PASSWORD);
        return ResultDTO.requstSuccess();
    }

    /**
     * 判断用户认证状态接口
     *
     * @param userId
     * @param authenticationType
     * @return
     */
    @ApiOperation(value = UserApi.JudgeAuthentication.METHOD_API_NAME, notes = UserApi.JudgeAuthentication.METHOD_API_NOTE)
    @PostMapping("/judgeAuthentication")
    public ResultDTO judgeAuthentication(@ApiParam(UserApi.JudgeAuthentication.METHOD_API_USER_ID) @RequestParam("userId") String userId, @ApiParam(UserApi.JudgeAuthentication.METHOD_API_AUTHENTICATION_TYPE) @RequestParam("authenticationType") String authenticationType) {
        String authenticationStatus = authenticationApplyService.judgeAuthentication(userId, authenticationType);
        return ResultDTO.requstSuccess(authenticationStatus);
    }

    private String uploadFile(MultipartFile file, String relativePath, HttpServletRequest request) {
        checkFileUploadLimit(request);
        String fileNames = null;
        try {
            fileNames = FileUploadHelper.saveFile(file, FILE_ROOT_PATH, relativePath);
            incrUploadTimes(request);
            return fileNames;
        } catch (IOException e) {
            LOG.info("**********uploadFile*******"+e.getMessage());
            throw new UserException(UserEnums.FILE_UPLOAD_ERROR);
        }
    }

    /**
     * 保存base64字符串
     *
     * @param imgBase64   base64字符串
     * @param relativeDir 相对目录
     * @return 文件相对路径
     * @throws IOException
     */
    private String generateImage(String imgBase64, String relativeDir, HttpServletRequest request) {
        checkFileUploadLimit(request);
        try {
            String filePath = FileUploadHelper.generateImage(imgBase64, FILE_ROOT_PATH, relativeDir);
            incrUploadTimes(request);
            return filePath;
        } catch (IOException e) {
            throw new UserException(UserEnums.FILE_UPLOAD_ERROR);
        }
    }

    /**
     * 检查上传文件次数限制
     */
    private void checkFileUploadLimit(HttpServletRequest request) {
        String userId = SSOHelper.getUserId(redisTemplate, request);
        String key = RedisConstant.getFileUploadTimesKey(userId);
        if (redisTemplate.hasKey(key)) {
            Integer times = (Integer) redisTemplate.opsForValue().get(key);
            //如果上传文件超过限制次数，报错
            String limitTimes = configService.getValidValueByKey(ConfigConstant.KEY_UPLOAD_LIMIT);
            if (limitTimes != null) {
                if (Integer.parseInt(limitTimes) < times) {
                    throw new UserException(UserEnums.FILE_UPLOAD_LIMIT);
                }
            }
        }
    }

    /**
     * 上传文件次数+1
     */
    private void incrUploadTimes(HttpServletRequest request) {
        String userId = SSOHelper.getUserId(redisTemplate, request);
        String key = RedisConstant.getFileUploadTimesKey(userId);
        redisTemplate.opsForValue().increment(key, 1);//自增+1
        redisTemplate.expire(key, 1, TimeUnit.DAYS);//设置超时时间为一天
    }

    /***
     * 获取用户语种
     * @param request
     * @return
     */
    private String getUserLocale(HttpServletRequest request) {
        String userLocale = HttpRequestUtil.getUserLocale(request);
        return userLocale;
    }
}
