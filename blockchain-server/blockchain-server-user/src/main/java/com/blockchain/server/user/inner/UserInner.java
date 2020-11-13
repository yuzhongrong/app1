package com.blockchain.server.user.inner;

import com.blockchain.common.base.dto.NotifyOutSMS;
import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.common.base.dto.SessionUserDTO;
import com.blockchain.common.base.dto.UserInfoDTO;
import com.blockchain.common.base.util.HttpRequestUtil;
import com.blockchain.common.base.util.SSOHelper;
import com.blockchain.server.user.common.constants.sql.UserListConstant;
import com.blockchain.server.user.common.enums.SmsCountEnum;
import com.blockchain.server.user.common.enums.UserEnums;
import com.blockchain.server.user.common.exceprion.UserException;
import com.blockchain.server.user.common.utils.CheckUtils;
import com.blockchain.server.user.common.utils.EmailCodeUtils;
import com.blockchain.server.user.common.utils.SmsCodeUtils;
import com.blockchain.server.user.entity.UserMain;
import com.blockchain.server.user.entity.UserRelation;
import com.blockchain.server.user.inner.api.UserInnerApi;
import com.blockchain.server.user.service.*;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author huangxl
 * @create 2019-03-04 10:53
 */
@RestController
@RequestMapping("/inner")
public class UserInner {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private UserListService userListService;
    @Autowired
    private UserMainService userMainService;
    @Autowired
    private SmsCountService smsCountService;
    @Autowired
    private SmsCodeUtils smsCodeUtils;
    @Autowired
    private UserService userService;
    @Autowired
    private EmailCodeUtils emailCodeUtils;
    @Autowired
    private UserRelationService userRelationService;
    @Autowired
    private ConfigService configService;

    @Autowired
    AuthenticationApplyLogService authenticationApplyLogService;

    /**
     *
     * 短信通知号码
     */
    @Value("${user.error.phone}")
    private String errorPhone;
    /**
     * 是否通过一级（初级）认证
     * @param userId
     * @return
     */
    @GetMapping("/isPrimarytAuth")
    @ApiOperation(value = UserInnerApi.IsPrimarytAuth.METHOD_NAME,notes = UserInnerApi.IsPrimarytAuth.METHOD_NOTE)
    public void isPrimarytAuth(@ApiParam(UserInnerApi.IsPrimarytAuth.METHOD_API_USER_ID) @RequestParam("userId") String userId)  {
        authenticationApplyLogService.isPrimarytAuth(userId);

    }


    /**
     * 是否通过（高级）二级认证
     * @param userId
     * @return
     */
    @GetMapping("/isHightAuth")
    @ApiOperation(value = UserInnerApi.IsHightAuth.METHOD_NAME, notes = UserInnerApi.IsHightAuth.METHOD_NOTE)
    public void isHightAuth(@ApiParam(UserInnerApi.IsHightAuth.METHOD_API_USER_ID) @RequestParam("userId") String userId)  {
        authenticationApplyLogService.isHightAuth(userId);
    }


    /**
     * 短信提示
     * @param describe
     * @return
     */
    @GetMapping("/sendInform")
    @ApiOperation(value = UserInnerApi.sendInform.METHOD_NAME, notes = UserInnerApi.sendInform.METHOD_NOTE)
    public void sendInform(@ApiParam(UserInnerApi.sendInform.DESCRIBE) @RequestParam("describe") String describe)  {

        String [] phone = errorPhone.split(",");
        for (int i = 0 ; i < phone.length ;i ++){

            authenticationApplyLogService.sendInform(phone[i],describe);
        }

    }




    /**
     * 禁止交易
     *
     * @param userId
     * @param request
     * @return
     */
    @GetMapping("/hasTransactionPermission")
    @ApiOperation(value = UserInnerApi.HasTransactionPermission.METHOD_NAME,
            notes = UserInnerApi.HasTransactionPermission.METHOD_NOTE)
    public ResultDTO verifyEmailCode(@ApiParam(UserInnerApi.HasTransactionPermission.METHOD_API_USER_ID) @RequestParam("userId") String userId, HttpServletRequest request) {
//        String userId = SSOHelper.getUserId(redisTemplate, request);
        boolean exit = userListService.checkUserByUserIdAndType(UserListConstant.LIST_TYPE_BLACK, userId, UserListConstant.TYPE_BAN_TRANSACTION);

        //返回true,代表在黑名单中
        if (exit) {
            throw new UserException(UserEnums.TRANSACTION_FORBIDDEN);
        }
        return ResultDTO.requstSuccess();
    }

    /**
     * 禁止提现
     *
     * @param userId
     * @return
     */
    @PostMapping("/verifyBanWithdraw")
    @ApiOperation(value = UserInnerApi.VerifyBanWithdraw.METHOD_NAME,
            notes = UserInnerApi.VerifyBanWithdraw.METHOD_NOTE)
    public ResultDTO verifyBanWithdraw(@ApiParam(UserInnerApi.VerifyBanWithdraw.METHOD_API_USER_ID) @RequestParam("userId") String userId) {
        boolean exit = userListService.checkUserByUserIdAndType(UserListConstant.LIST_TYPE_BLACK, userId, UserListConstant.TYPE_BAN_OUT);
        //返回true,代表在黑名单中
        if (exit) {
            throw new UserException(UserEnums.WITHDRAW_FORBIDDEN);
        }
        return ResultDTO.requstSuccess();
    }

    /**
     * 免提现手续费
     *
     * @param userId
     * @return
     */
    @PostMapping("/verifyFreeWithdraw")
    @ApiOperation(value = UserInnerApi.VerifyFreeWithdraw.METHOD_NAME,
            notes = UserInnerApi.VerifyFreeWithdraw.METHOD_NOTE)
    public ResultDTO verifyFreeWithdraw(@ApiParam(UserInnerApi.VerifyFreeWithdraw.METHOD_API_USER_ID) @RequestParam("userId") String userId) {
        // 返回true表示用户免手续费
        boolean exit = userListService.checkUserByUserIdAndType(UserListConstant.LIST_TYPE_WHITE, userId, UserListConstant.TYPE_FREE_TX);
        return ResultDTO.requstSuccess(exit);
    }

    /**
     * 免交易手续费
     *
     * @param userId
     * @return
     */
    @PostMapping("/verifyFreeTransaction")
    @ApiOperation(value = UserInnerApi.VerifyFreeTransaction.METHOD_NAME,
            notes = UserInnerApi.VerifyFreeTransaction.METHOD_NOTE)
    public ResultDTO verifyFreeTransaction(@ApiParam(UserInnerApi.VerifyFreeTransaction.METHOD_API_USER_ID) @RequestParam("userId") String userId) {
        // 返回true表示用户免手续费
        boolean exit = userListService.checkUserByUserIdAndType(UserListConstant.LIST_TYPE_WHITE, userId, UserListConstant.TYPE_FREE_TRANSACTION);
        return ResultDTO.requstSuccess(exit);
    }

    /**
     * 发布广告
     *
     * @param userId
     * @return
     */
    @PostMapping("/verifyFreePushAd")
    @ApiOperation(value = UserInnerApi.VerifyFreePushAd.METHOD_NAME,
            notes = UserInnerApi.VerifyFreePushAd.METHOD_NOTE)
    public ResultDTO verifyFreePushAd(@ApiParam(UserInnerApi.VerifyFreePushAd.METHOD_API_USER_ID) @RequestParam("userId") String userId) {
        // 返回true表示用户允许发布广告
        boolean exit = userListService.checkUserByUserIdAndType(UserListConstant.LIST_TYPE_WHITE, userId, UserListConstant.TYPE_PUSH_AD);
        return ResultDTO.requstSuccess(exit);
    }

    @PostMapping("/sendWalletPwCode")
    @ApiOperation(value = UserInnerApi.SendUpdateWalletPassword.METHOD_NAME,
            notes = UserInnerApi.SendUpdateWalletPassword.METHOD_NOTE)
    public ResultDTO sendWalletPwCode(HttpServletRequest request) {
        String userId = SSOHelper.getUserId(redisTemplate, request);
        UserMain userMain = userMainService.selectById(userId);
        if (CheckUtils.checkMobilePhone(userMain.getMobilePhone())) {
            smsCountService.handleInsertSmsCode(userMain.getMobilePhone(), userMain.getInternationalCode(), SmsCountEnum.SMS_COUNT_UPDATE_WALLET_PASSWORD);
        } else if (CheckUtils.checkEmail(userMain.getMobilePhone())) {
            emailCodeUtils.sendSmsCodeAndStoreToCache(userMain.getMobilePhone(), HttpRequestUtil.getUserLocale(request), SmsCountEnum.SMS_COUNT_UPDATE_WALLET_PASSWORD);
        } else {
            throw new UserException(UserEnums.FORMAT_ERROR);
        }

        return ResultDTO.requstSuccess();
    }

    @GetMapping("/updateWalletPassword")
    @ApiOperation(value = UserInnerApi.UpdateWalletPassword.METHOD_NAME,
            notes = UserInnerApi.UpdateWalletPassword.METHOD_NOTE)
    public ResultDTO updateWalletPassword(@ApiParam(UserInnerApi.UpdateWalletPassword.METHOD_API_CODE) @RequestParam("code") String code,
                                          HttpServletRequest request) {
        SessionUserDTO user = SSOHelper.getUser(redisTemplate, request);
        smsCodeUtils.validateVerifyCode(code, user.getTel(), SmsCountEnum.SMS_COUNT_UPDATE_WALLET_PASSWORD);
        smsCodeUtils.removeKey(user.getTel(), SmsCountEnum.SMS_COUNT_UPDATE_WALLET_PASSWORD);
        return ResultDTO.requstSuccess();
    }

    @GetMapping("/selectUserInfoById")
    @ApiOperation(value = UserInnerApi.selectUserInfoById.METHOD_NAME,
            notes = UserInnerApi.selectUserInfoById.METHOD_NOTE)
    public ResultDTO selectUserInfoById(@ApiParam(UserInnerApi.selectUserInfoById.METHOD_API_USERID) @RequestParam("userId") String userId) {
        return ResultDTO.requstSuccess(userMainService.selectUserInfoById(userId));
    }

    /************* 验证码 *************/
    @ApiOperation(value = UserInnerApi.ValidateSmsg.METHOD_TITLE_NAME, notes = UserInnerApi.ValidateSmsg.METHOD_TITLE_NOTE)
    @PostMapping("/validateSmsg")
    public ResultDTO validateSmsg(HttpServletRequest request,
                                  @ApiParam(UserInnerApi.ValidateSmsg.METHOD_API_VERIFYCODE) @RequestParam("verifyCode") String verifyCode,
                                  @ApiParam(UserInnerApi.ValidateSmsg.METHOD_API_PHONE) @RequestParam("phone") String phone) {
        smsCodeUtils.validateVerifyCode(verifyCode, phone, SmsCountEnum.SMS_COUNT_UPDATE_WALLET_PASSWORD);
        return ResultDTO.requstSuccess();
    }

    @ApiOperation(value = UserInnerApi.VerifyWithdrawSms.METHOD_TITLE_NAME, notes = UserInnerApi.VerifyWithdrawSms.METHOD_TITLE_NOTE)
    @GetMapping("/verifyWithdrawSms")
    public ResultDTO verifyWithdrawSms(@ApiParam(UserInnerApi.VerifyWithdrawSms.METHOD_API_VERIFYCODE) @RequestParam("verifyCode") String verifyCode,
                                       @ApiParam(UserInnerApi.VerifyWithdrawSms.METHOD_API_PHONE) @RequestParam("account") String account) {
        smsCodeUtils.validateVerifyCode(verifyCode, account, SmsCountEnum.SMS_WITHDRAW);
        return ResultDTO.requstSuccess();
    }

    @ApiOperation(value = UserInnerApi.hasLowAuthAndUserList.METHOD_TITLE_NAME,
            notes = UserInnerApi.hasLowAuthAndUserList.METHOD_TITLE_NOTE)
    @GetMapping("/hasLowAuthAndUserList")
    public ResultDTO hasLowAuthAndUserList(@ApiParam(UserInnerApi.hasLowAuthAndUserList.METHOD_API_USERID) @RequestParam("userId") String userId) {
        userService.hasLowAuthAndUserList(userId);
        return ResultDTO.requstSuccess();
    }

    @ApiOperation(value = UserInnerApi.hasHighAuthAndUserList.METHOD_TITLE_NAME,
            notes = UserInnerApi.hasHighAuthAndUserList.METHOD_TITLE_NOTE)
    @GetMapping("/hasHighAuthAndUserList")
    public ResultDTO hasHighAuthAndUserList(@ApiParam(UserInnerApi.hasHighAuthAndUserList.METHOD_API_USERID) @RequestParam("userId") String userId) {
        userService.hasHighAuthAndUserList(userId);
        return ResultDTO.requstSuccess();
    }

    @ApiOperation(value = UserInnerApi.SelectRelationByUserId.METHOD_TITLE_NAME,
            notes = UserInnerApi.SelectRelationByUserId.METHOD_TITLE_NOTE)
    @GetMapping("/selectRelationByUserId")
    public ResultDTO selectRelationByUserId(@ApiParam(UserInnerApi.SelectRelationByUserId.METHOD_API_USERID) @RequestParam("userId") String userId) {
        UserRelation result = userRelationService.findByUserId(userId);
        return ResultDTO.requstSuccess(result);
    }

    @ApiOperation(value = UserInnerApi.GetUserAuthentication.METHOD_TITLE_NAME, notes = UserInnerApi.GetUserAuthentication.METHOD_TITLE_NOTE)
    @GetMapping("/getUserAuthentication")
    public ResultDTO getUserAuthentication(@ApiParam(UserInnerApi.GetUserAuthentication.METHOD_API_USERID) @RequestParam("userId") String userId) {
        return ResultDTO.requstSuccess(userService.getUserAuthentication(userId));
    }

    @ApiOperation(value = UserInnerApi.GetDirects.METHOD_TITLE_NAME, notes = UserInnerApi.GetDirects.METHOD_TITLE_NOTE)
    @GetMapping("/getDirects")
    public ResultDTO getDirects(@ApiParam(UserInnerApi.GetDirects.METHOD_API_USERID) @RequestParam("userId") String userId) {
        return ResultDTO.requstSuccess(userRelationService.getDirects(userId));
    }

    @GetMapping("/getAllUserRelation")
    ResultDTO getAllUserRelation(){
        userRelationService.getAllUserRelation();
        return ResultDTO.requstSuccess();
    }

    @ApiOperation(value = UserInnerApi.GetAllSubordinate.METHOD_TITLE_NAME, notes = UserInnerApi.GetAllSubordinate.METHOD_TITLE_NOTE)
    @GetMapping("/getAllSubordinate")
    public ResultDTO getAllSubordinate(@ApiParam(UserInnerApi.GetAllSubordinate.METHOD_API_USERID) @RequestParam("userId") String userId) {
        return ResultDTO.requstSuccess(userRelationService.getAllSubordinate(userId));
    }

    @ApiOperation(value = UserInnerApi.ListUserDirectTeam.METHOD_TITLE_NAME, notes = UserInnerApi.ListUserDirectTeam.METHOD_TITLE_NOTE)
    @GetMapping("/listUserDirectTeam")
    public ResultDTO listUserDirectTeam(@ApiParam(UserInnerApi.ListUserDirectTeam.METHOD_API_USERID) @RequestParam("userId") String userId) {
        return ResultDTO.requstSuccess(userRelationService.listUserDirectTeam(userId));
    }

    @ApiOperation(value = UserInnerApi.NotifyOut.METHOD_TITLE_NAME, notes = UserInnerApi.NotifyOut.METHOD_TITLE_NOTE)
    @PostMapping("/notifyOut")
    public ResultDTO notifyOut(@ApiParam(UserInnerApi.NotifyOut.NotifyOutSMS) @RequestBody NotifyOutSMS notifyOutSMS) {
        configService.notifyOut(notifyOutSMS);
        return ResultDTO.requstSuccess();
    }

    @GetMapping("/getUserInfo")
    ResultDTO<UserInfoDTO>  getUserInfo(@RequestParam(value = "telPhone",required = false)String telPhone,@RequestParam(value = "userId",required = false)String userId){
        UserMain userMain=null;
        if(telPhone!=null){
            userMain = userMainService.selectByMobilePhone(telPhone);
        }else if(userId!=null){
            userMain = userMainService.selectById(userId);
        }

        UserInfoDTO userInfo= new UserInfoDTO();
        userInfo.setTelPhone(userMain.getMobilePhone());
        userInfo.setUserId(userMain.getId());
        return ResultDTO.requstSuccess(userInfo);
    }

}
