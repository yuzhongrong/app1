package com.blockchain.server.user.controller;

import com.blockchain.common.base.constant.TokenTypeEnums;
import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.common.base.dto.SessionUserDTO;
import com.blockchain.common.base.dto.TokenDTO;
import com.blockchain.common.base.util.HttpRequestUtil;
import com.blockchain.common.base.util.RSACoderUtils;
import com.blockchain.common.base.util.SSOHelper;
import com.blockchain.server.user.common.constants.other.InternationalConstant;
import com.blockchain.server.user.common.enums.SmsCountEnum;
import com.blockchain.server.user.common.enums.UserEnums;
import com.blockchain.server.user.common.exceprion.UserException;
import com.blockchain.server.user.common.utils.*;
import com.blockchain.server.user.controller.api.LoginApi;
import com.blockchain.server.user.dto.UserBaseDTO;
import com.blockchain.server.user.entity.UserMain;
import com.blockchain.server.user.manager.TencentCaptcha;
import com.blockchain.server.user.service.PushUserService;
import com.blockchain.server.user.service.SmsCountService;
import com.blockchain.server.user.service.UserLoginService;
import com.blockchain.server.user.service.UserMainService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author huangxl
 * @data 2019/2/21 15:06
 * 用户注册, 登录控制器
 */
@RestController
@Api(LoginApi.CONTROLLER_API)
@RequestMapping("/login")
public class LoginController {

    private static final Logger LOG = LoggerFactory.getLogger(LoginController.class);
    @Value("${chuanglansms.status}")
    private String  smsStatus ;

    @Autowired
    private UserLoginService userLoginService;
    @Autowired
    private UserMainService userMainService;
    @Autowired
    private SmsCountService smsCountService;
    @Autowired
    private EmailCodeUtils emailCodeUtils;
    @Autowired
    private PushUserService pushUserService;
    @Autowired
    private SmsCodeUtils smsCodeUtils;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private TencentCaptcha tencentCaptcha;





    @PostMapping("/password")
    @ApiOperation(value = LoginApi.PassWorldLogin.METHOD_NAME,
            notes = LoginApi.PassWorldLogin.METHOD_NOTE)
    public ResultDTO loginByPassword(@ApiParam(LoginApi.PassWorldLogin.METHOD_API_TEL) @RequestParam(name = "tel") String tel,
                                     @ApiParam(LoginApi.PassWorldLogin.METHOD_API_PASS) @RequestParam(name = "password") String password,
                                     @ApiParam(LoginApi.PassWorldLogin.METHOD_API_CLIENT_ID) @RequestParam(name = "clientId", required = false) String clientId,
                                     @ApiParam(LoginApi.Register.TICKET) @RequestParam(name = "ticket", required = false) String ticket,
                                     @ApiParam(LoginApi.Register.RANDSTR) @RequestParam(name = "randstr", required = false) String randstr,
                                     HttpServletRequest request) {
//        //校验滑块拼图
//        tencentCaptcha.verifyTicket(ticket, randstr, request);

        UserMain userMain = userLoginService.handleLoginByPassword(tel, password);
        return handleAppAfterLogin(userMain, clientId, getUserLocale(request));
    }

    @PostMapping("/loginByCode")
    @ApiOperation(value = LoginApi.SmsCodeLogin.METHOD_NAME,
            notes = LoginApi.SmsCodeLogin.METHOD_NOTE)
    public ResultDTO loginBysmsCode(@ApiParam(LoginApi.SmsCodeLogin.METHOD_API_TEL) @RequestParam(name = "tel") String tel,
                                    @ApiParam(LoginApi.SmsCodeLogin.METHOD_API_CODE) @RequestParam(name = "code") String code,
                                    @ApiParam(LoginApi.SmsCodeLogin.METHOD_API_CLIENT_ID) @RequestParam(name = "clientId", required = false) String clientId,
                                    HttpServletRequest request) {
        smsCodeUtils.validateVerifyCode(code, tel, SmsCountEnum.SMS_COUNT_LOGIN);
        UserMain userMain = userLoginService.handleLoginByPhoneCode(tel);
//        userMainService.selectByMobilePhone(tel);
        smsCodeUtils.removeKey(tel, SmsCountEnum.SMS_COUNT_LOGIN);
        return handleAppAfterLogin(userMain, clientId, getUserLocale(request));
    }

    @PostMapping("/register")
    @ApiOperation(value = LoginApi.Register.METHOD_NAME,
            notes = LoginApi.Register.METHOD_NOTE)
    public ResultDTO register(@ApiParam(LoginApi.Register.METHOD_API_TEL) @RequestParam(name = "tel") String tel,
                              @ApiParam(LoginApi.Register.METHOD_API_CODE) @RequestParam(name = "code") String code,
                              @ApiParam(LoginApi.Register.METHOD_API_INVITATION_CODE) @RequestParam(value = "invitationCode") String invitationCode,
                              @ApiParam(LoginApi.Register.METHOD_API_PASSWORD) @RequestParam(value = "password", required = false) String password,
                              @ApiParam(LoginApi.Register.METHOD_API_INTERNATIONAL_CODE) @RequestParam(value = "internationalCode", required = false, defaultValue = InternationalConstant.DEFAULT_CODE) String internationalCode,
                              @ApiParam(LoginApi.Register.METHOD_API_NICK_NAME) @RequestParam(value = "nickName", required = false) String nickName,
                              @ApiParam(LoginApi.Register.METHOD_API_CLIENT_ID) @RequestParam(name = "clientId", required = false) String clientId,
                              @ApiParam(LoginApi.Register.TICKET) @RequestParam(name = "ticket", required = false) String ticket,
                              @ApiParam(LoginApi.Register.RANDSTR) @RequestParam(name = "randstr", required = false) String randstr,
                              HttpServletRequest request
    ) {
            //校验滑块拼图
            tencentCaptcha.verifyTicket(ticket, randstr, request);
            smsCodeUtils.validateVerifyCode(code, tel, SmsCountEnum.SMS_COUNT_REGISTER);
            //注册启动失败重试机制
            int count =5 ;
            UserMain userMain  = registers(  count, tel,    invitationCode,   password,   internationalCode,   nickName);
           // UserMain userMain = userMainService.handleRegister(tel, invitationCode, internationalCode, password, nickName);
            if(userMain == null){
                return ResultDTO.requestRejected(500,"注册失败，请重试!");
            }
            smsCodeUtils.removeKey(tel, SmsCountEnum.SMS_COUNT_REGISTER);
            return handleAppAfterLogin(userMain, clientId, getUserLocale(request));

    }

    private UserMain registers( int count ,String tel,  String invitationCode, String password, String internationalCode, String nickName)  {

        try {
            LOG.info(tel+"注册开始进入注册流程---"+count);
            UserMain userMain = userMainService.handleRegister(tel, invitationCode, internationalCode, password, nickName);
            return userMain;
        }catch ( UserException uEx){
             LOG.error(tel+"注册发生已知异常，退出重试",uEx);
            throw  new UserException(uEx.getCode(),uEx.getMsg());
        }catch (Exception ex){
            --count;
            LOG.error(tel+"注册发送异常",ex);
           try {
               //等待1s重试
               Thread.sleep(1000);
           }catch (InterruptedException e){
               LOG.error("线程睡眠异常",e);
           }
           if(count > 0){
               LOG.info(tel+"注册开始进入失败重试---"+count);
               registers(  count, tel,    invitationCode,   password,   internationalCode,   nickName);
           }

        }
        return null;
    }

    @PostMapping("/sendLoginCode")
    @ApiOperation(value = LoginApi.SendLoginSmsCode.METHOD_NAME,
            notes = LoginApi.SendLoginSmsCode.METHOD_NOTE)
    public ResultDTO sendLoginSmsCode(@ApiParam(LoginApi.SendLoginSmsCode.METHOD_API_TEL) @RequestParam(name = "tel") String tel,
                                      HttpServletRequest request, @ApiParam(LoginApi.SendLoginSmsCode.METHOD_API_INTERNATIONAL_CODE) @RequestParam(value = "internationalCode", required = false, defaultValue = InternationalConstant.DEFAULT_CODE) String internationalCode) {
        UserMain userMain = userMainService.selectByMobilePhone(tel);
        if (userMain == null) {
            throw new UserException(UserEnums.USER_NOT_EXISTS);
        }
        if (CheckUtils.checkMobilePhone(tel)) {
            smsCountService.handleInsertSmsCode(tel, internationalCode, SmsCountEnum.SMS_COUNT_LOGIN);
        } else
            if (CheckUtils.checkEmail(tel)) {
            emailCodeUtils.sendSmsCodeAndStoreToCache(tel, getUserLocale(request), SmsCountEnum.SMS_COUNT_LOGIN);
        } else {
            throw new UserException(UserEnums.FORMAT_ERROR);
        }
        smsCountService.handleInsertSmsCode(tel, internationalCode, SmsCountEnum.SMS_COUNT_LOGIN);
        return ResultDTO.requstSuccess();
    }

    @PostMapping("/sendRegisterCode")
    @ApiOperation(value = LoginApi.SendRegisterSmsCode.METHOD_NAME,
            notes = LoginApi.SendRegisterSmsCode.METHOD_NOTE)
    public ResultDTO sendRegisterSmsCode(@ApiParam(LoginApi.SendRegisterSmsCode.METHOD_API_TEL) @RequestParam(name = "tel") String tel,
                                         HttpServletRequest request, @ApiParam(LoginApi.SendRegisterSmsCode.METHOD_API_INTERNATIONAL_CODE) @RequestParam(value = "internationalCode", required = false, defaultValue = InternationalConstant.DEFAULT_CODE) String internationalCode,
                                         String token ,String authenticate) {
        UserMain userMain = userMainService.selectByMobilePhone(tel);
        if (userMain != null) {
            throw new UserException(UserEnums.USER_PHONE_EXISTS);
        }
        if (CheckUtils.checkMobilePhone(tel)) {
            //验证短信行为
             if(!AuthenticateSms.smsgBehaviorInspect(token, authenticate, tel)){
                 return ResultDTO.requestRejected(1145,"短信行为验证失败！请确保你的app版本为最新。");
             }
            smsCountService.handleInsertSmsCode(tel, internationalCode, SmsCountEnum.SMS_COUNT_REGISTER);
        } else if (CheckUtils.checkEmail(tel)) {
            emailCodeUtils.sendSmsCodeAndStoreToCache(tel, getUserLocale(request), SmsCountEnum.SMS_COUNT_REGISTER);
        } else {
            throw new UserException(UserEnums.FORMAT_ERROR);
        }
//        smsCountService.handleInsertSmsCode(tel, internationalCode, SmsCountEnum.SMS_COUNT_REGISTER);
        return ResultDTO.requstSuccess();
    }
    @PostMapping("/sendRegisterCodePC")
    @ApiOperation(value = LoginApi.SendRegisterSmsCode.METHOD_NAME,
            notes = LoginApi.SendRegisterSmsCode.METHOD_NOTE)
    public ResultDTO sendRegisterCodePC(@ApiParam(LoginApi.SendRegisterSmsCode.METHOD_API_TEL) @RequestParam(name = "tel") String tel,
                                         HttpServletRequest request, @ApiParam(LoginApi.SendRegisterSmsCode.METHOD_API_INTERNATIONAL_CODE) @RequestParam(value = "internationalCode", required = false, defaultValue = InternationalConstant.DEFAULT_CODE) String internationalCode
                                        ) {
        UserMain userMain = userMainService.selectByMobilePhone(tel);
        if (userMain != null) {
            throw new UserException(UserEnums.USER_PHONE_EXISTS);
        }
        if (CheckUtils.checkMobilePhone(tel)) {
            smsCountService.handleInsertSmsCode(tel, internationalCode, SmsCountEnum.SMS_COUNT_REGISTER);
        } else if (CheckUtils.checkEmail(tel)) {
            emailCodeUtils.sendSmsCodeAndStoreToCache(tel, getUserLocale(request), SmsCountEnum.SMS_COUNT_REGISTER);
        } else {
            throw new UserException(UserEnums.FORMAT_ERROR);
        }
//        smsCountService.handleInsertSmsCode(tel, internationalCode, SmsCountEnum.SMS_COUNT_REGISTER);
        return ResultDTO.requstSuccess();
    }

    @PostMapping("/loginout")
    @ApiOperation(value = LoginApi.Loginout.METHOD_NAME,
            notes = LoginApi.Loginout.METHOD_NOTE)
    public ResultDTO loginout(@RequestParam(value = "tokenType", required = false) String tokenType,
                              HttpServletRequest request) {
        //不为空的时候，代表APP调用，APP调用时清空用户客户端信息
        //PC不需要清空
        if (StringUtils.isNotBlank(tokenType)) {
            String userId = SSOHelper.getUserIdIsExits(redisTemplate, request);
            if (userId != null) {
                //退出登录时，清空用户客户端信息，不再推送
                pushUserService.insertOrUpadteUser(userId, null, null);
            }
        }
        SSOHelper.removeUser(request, redisTemplate);
        return ResultDTO.requstSuccess();
    }

    @PostMapping("/password2")
    @ApiOperation(value = LoginApi.PassWorldLoginPC.METHOD_NAME,
            notes = LoginApi.PassWorldLoginPC.METHOD_NOTE)
    public ResultDTO loginByPasswordPC(@ApiParam(LoginApi.PassWorldLoginPC.METHOD_API_TEL) @RequestParam(name = "tel") String tel,
                                       @ApiParam(LoginApi.PassWorldLoginPC.METHOD_API_PASS) @RequestParam(name = "password") String password) {
        UserMain userMain = userLoginService.handleLoginByPassword(tel, password);
        return handleAfterLogin(userMain, TokenTypeEnums.PC.getValue());
    }

    @PostMapping("/loginByCode2")
    @ApiOperation(value = LoginApi.SmsCodeLoginPC.METHOD_NAME,
            notes = LoginApi.SmsCodeLoginPC.METHOD_NOTE)
    public ResultDTO loginBysmsCodePC(@ApiParam(LoginApi.SmsCodeLoginPC.METHOD_API_TEL) @RequestParam(name = "tel") String tel,
                                      @ApiParam(LoginApi.SmsCodeLoginPC.METHOD_API_CODE) @RequestParam(name = "code") String code) {
        smsCodeUtils.validateVerifyCode(code, tel, SmsCountEnum.SMS_COUNT_LOGIN);
        UserMain userMain = userLoginService.handleLoginByPhoneCode(tel);
//        userMainService.selectByMobilePhone(tel);
        smsCodeUtils.removeKey(tel, SmsCountEnum.SMS_COUNT_LOGIN);
        return handleAfterLogin(userMain, TokenTypeEnums.PC.getValue());
    }

    @PostMapping("/register2")
    @ApiOperation(value = LoginApi.RegisterPC.METHOD_NAME,
            notes = LoginApi.RegisterPC.METHOD_NOTE)
    public ResultDTO registerPC(@ApiParam(LoginApi.RegisterPC.METHOD_API_TEL) @RequestParam(name = "tel") String tel,
                                @ApiParam(LoginApi.RegisterPC.METHOD_API_CODE) @RequestParam(name = "code") String code,
                                @ApiParam(LoginApi.RegisterPC.METHOD_API_INVITATION_CODE) @RequestParam(value = "invitationCode", required = false) String invitationCode,
                                @ApiParam(LoginApi.RegisterPC.METHOD_API_PASSWORD) @RequestParam(value = "password", required = false) String password,
                                @ApiParam(LoginApi.RegisterPC.METHOD_API_INTERNATIONAL_CODE) @RequestParam(value = "internationalCode", required = false, defaultValue = InternationalConstant.DEFAULT_CODE) String internationalCode,
                                @ApiParam(LoginApi.RegisterPC.METHOD_API_NICK_NAME) @RequestParam(value = "nickName", required = false) String nickName,
                                @ApiParam(LoginApi.Register.TICKET) @RequestParam(name = "ticket", required = false) String ticket,
                                @ApiParam(LoginApi.Register.RANDSTR) @RequestParam(name = "randstr", required = false) String randstr,
                                HttpServletRequest request
    ) {
        //校验滑块拼图
        tencentCaptcha.verifyTicket(ticket, randstr, request);

        smsCodeUtils.validateVerifyCode(code, tel, SmsCountEnum.SMS_COUNT_REGISTER);
        UserMain userMain = userMainService.handleRegister(tel, invitationCode, internationalCode, password, nickName);
        smsCodeUtils.removeKey(tel, SmsCountEnum.SMS_COUNT_REGISTER);
        return handleAfterLogin(userMain, TokenTypeEnums.PC.getValue());
    }

    @PostMapping("/loginout2")
    @ApiOperation(value = LoginApi.LoginoutPC.METHOD_NAME,
            notes = LoginApi.LoginoutPC.METHOD_NOTE)
    public ResultDTO loginout2(HttpServletRequest request) {
        SSOHelper.removeUser(request, redisTemplate, TokenTypeEnums.PC.getValue());
        return ResultDTO.requstSuccess();
    }

    /**
     * 设置用户信息到redis
     *
     * @param id        用户id
     * @param tel       手机号
     * @param timestamp 时间撮
     */
    private void setUserToRedis(String id, String tel, long timestamp, String tokenType) {
        SessionUserDTO userDTO = new SessionUserDTO();
        userDTO.setId(id);
        userDTO.setTel(tel);
        userDTO.setTimestamp(timestamp);
        SSOHelper.setUser(userDTO, redisTemplate, tokenType);
    }

    /**
     * 生成token返回前端
     *
     * @param tel       手机号
     * @param timestamp 时间撮
     * @return token
     */
    private String generateToken(String tel, long timestamp, String tokenType) {
        TokenDTO tokenDTO = new TokenDTO();
        tokenDTO.setTel(tel);
        tokenDTO.setTimestamp(timestamp);
        tokenDTO.setTokenType(tokenType);
        return RSACoderUtils.encryptToken(tokenDTO);
    }

    /**
     * App登录成功之后的处理
     */
    private ResultDTO handleAppAfterLogin(UserMain userMain, String clientId, String userLocale) {
        //保存用户客户端信息，用于消息通知
        handleAfterLoginToSavePushUser(userMain.getId(), clientId, userLocale);
        return handleAfterLogin(userMain, TokenTypeEnums.APP.getValue());
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

    /***
     * APP登录成功后保存用户客户端信息
     * 用于消息通知
     */
    private void handleAfterLoginToSavePushUser(String userId, String clientId, String userLocale) {
        pushUserService.insertOrUpadteUser(userId, clientId, userLocale);
    }

    /**
     * 登录成功之后的处理
     */
    private ResultDTO handleAfterLogin(UserMain userMain, String tokenType) {
        long timestamp = System.currentTimeMillis();
        setUserToRedis(userMain.getId(), userMain.getMobilePhone(), timestamp, tokenType);
        String token = generateToken(userMain.getMobilePhone(), timestamp, tokenType);
        UserBaseDTO userBaseDTO = userMainService.selectUserInfoById(userMain.getId());
        userBaseDTO.setToken(token);
        return ResultDTO.requstSuccess(userBaseDTO);
    }

    @GetMapping("/resetPassword")
    @ApiOperation(value = LoginApi.ForgetPassword.METHOD_NAME,
            notes = LoginApi.ForgetPassword.METHOD_NOTE)
    public ResultDTO resetPassword(
            @ApiParam(LoginApi.ForgetPassword.METHOD_API_TEL) @RequestParam("tel") String tel,
            @ApiParam(LoginApi.ForgetPassword.METHOD_API_PASSWORD) @RequestParam("password") String password,
            @ApiParam(LoginApi.ForgetPassword.METHOD_API_CODE) @RequestParam("code") String code) {
        smsCodeUtils.validateVerifyCode(code, tel, SmsCountEnum.SMS_COUNT_FORGET_PASSWORD);
        UserMain user = userMainService.selectByMobilePhone(tel);
        userLoginService.updatePassword(user.getId(), password);
        smsCodeUtils.removeKey(tel, SmsCountEnum.SMS_COUNT_FORGET_PASSWORD);
        return ResultDTO.requstSuccess();
    }

    @PostMapping("/setForgetCode")
    @ApiOperation(value = LoginApi.SetForgetPasswordCode.METHOD_NAME,
            notes = LoginApi.SetForgetPasswordCode.METHOD_NOTE)
    public ResultDTO sendForgetPwCode(@ApiParam(LoginApi.SetForgetPasswordCode.METHOD_API_CODE) @RequestParam(value = "internationalCode", required = false, defaultValue = InternationalConstant.DEFAULT_CODE) String internationalCode,
                                      HttpServletRequest request, @ApiParam(LoginApi.SetForgetPasswordCode.METHOD_API_TEL) @RequestParam("tel") String tel) {
        UserMain userMain = userMainService.selectByMobilePhone(tel);
        if (userMain == null) {
            throw new UserException(UserEnums.USER_NOT_EXISTS);
        }
        if (CheckUtils.checkMobilePhone(tel)) {
            smsCountService.handleInsertSmsCode(tel, internationalCode, SmsCountEnum.SMS_COUNT_FORGET_PASSWORD);
        } else if (CheckUtils.checkEmail(tel)) {
            emailCodeUtils.sendSmsCodeAndStoreToCache(tel, getUserLocale(request), SmsCountEnum.SMS_COUNT_FORGET_PASSWORD);
        } else {
            throw new UserException(UserEnums.FORMAT_ERROR);
        }
//        smsCountService.handleInsertSmsCode(tel, internationalCode, SmsCountEnum.SMS_COUNT_FORGET_PASSWORD);
        return ResultDTO.requstSuccess();
    }

    private boolean checkIsStatus(){
        LOG.info("smsStatus is:"+smsStatus);
        if(smsStatus==null ||smsStatus.equals("")||smsStatus.equals("false")){
            return false;
        }else{
            return true;
        }
    }

}
