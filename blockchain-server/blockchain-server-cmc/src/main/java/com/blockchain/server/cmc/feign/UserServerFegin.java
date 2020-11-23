package com.blockchain.server.cmc.feign;

import com.blockchain.common.base.dto.NotifyOutSMS;
import com.blockchain.common.base.dto.ResultDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="dapp-user-server",path = "/user")
public interface UserServerFegin {

    /**
     * 通过手机号获取用户id
     *
     * @param phone
     * @return
     */
    @PostMapping("/login/getUserIdByPhone")
    ResultDTO<String> getUserIdByPhone(@RequestParam("phone") String phone);


    /**
     * 校验验证码
     *
     * @param phone
     * @param verifyCode
     * @return
     */
    @PostMapping("/inner/validateSmsg")
    ResultDTO validateSmsg(@RequestParam("phone") String phone, @RequestParam("verifyCode") String verifyCode);

    /**
     * 禁止提现
     *
     * @param userId
     * @return
     */
    @PostMapping("/inner/verifyBanWithdraw")
    ResultDTO verifyBanWithdraw(@RequestParam("userId") String userId);

    /**
     * 免提现手续费
     *
     * @param userOpenId
     * @return
     */
    @PostMapping("/inner/verifyFreeWithdraw")
    ResultDTO<Boolean> verifyFreeWithdraw(@RequestParam("userId") String userOpenId);

    /**
     * 提现短信通知
     */
    @PostMapping("/inner/notifyOut")
    ResultDTO notifyOut(@RequestBody NotifyOutSMS notifyOutSMS);

    /**
     * 验证提现短信
     */
    @GetMapping("/inner/verifyWithdrawSms")
    ResultDTO verifyWithdrawSms(@RequestParam("verifyCode") String verifyCode, @RequestParam("account") String account);

}
