package com.blockchain.server.teth.feign;

import com.blockchain.common.base.dto.NotifyOutSMS;
import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.common.base.dto.UserInfoDTO;
import com.blockchain.server.teth.feign.api.UserInnerApi;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author huangxl
 * @create 2019-03-27 16:19
 */
@FeignClient("dapp-user-server")
public interface UserFeign {

    String CONTENT_PATH = "/user/inner";

    @ApiOperation(value = UserInnerApi.ValidateSmsg.METHOD_TITLE_NAME, notes = UserInnerApi.ValidateSmsg.METHOD_TITLE_NOTE)
    @PostMapping(CONTENT_PATH + "/validateSmsg")
    ResultDTO validateSmsg(@ApiParam(UserInnerApi.ValidateSmsg.METHOD_API_VERIFYCODE) @RequestParam("verifyCode") String verifyCode,
                           @ApiParam(UserInnerApi.ValidateSmsg.METHOD_API_PHONE) @RequestParam("phone") String phone);


    /**
     * 禁止提现
     *
     * @param userId
     * @return
     */
    @PostMapping(CONTENT_PATH+"/verifyBanWithdraw")
    ResultDTO verifyBanWithdraw(@RequestParam("userId") String userId);

    /**
     * 免提现手续费
     *
     * @param userOpenId
     * @return
     */
    @PostMapping(CONTENT_PATH+"/verifyFreeWithdraw")
    ResultDTO<Boolean> verifyFreeWithdraw(@RequestParam("userId") String userOpenId);

    /**
     * 提现短信通知
     */
    @PostMapping(CONTENT_PATH+"/notifyOut")
    ResultDTO notifyOut(@RequestBody NotifyOutSMS notifyOutSMS);

    /**
     * 验证提现短信
     */
    @GetMapping(CONTENT_PATH + "/verifyWithdrawSms")
    ResultDTO verifyWithdrawSms(@RequestParam("verifyCode") String verifyCode, @RequestParam("account") String account);

    @GetMapping(CONTENT_PATH + "/getUserInfo")
    ResultDTO<UserInfoDTO>  getUserInfo(@RequestParam(value = "telPhone", required = false) String telPhone, @RequestParam(value = "userId", required = false) String userId);
}