package com.blockchain.server.tron.feign;

import com.blockchain.common.base.dto.NotifyOutSMS;
import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.server.tron.dto.UserBaseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name="dapp-user-server",path = "/user")
public interface UserServerFegin {

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
     * 通过手机号获取用户id
     *
     * @param phone
     * @return
     */
    @PostMapping("/inner/getUserIdByPhone")
    ResultDTO<String> getUserIdByPhone(@RequestParam("phone") String phone);

    @GetMapping("/inner/selectUserInfoById")
    ResultDTO<UserBaseDTO> getUserById(@RequestParam("userId") String userId);

    @GetMapping("/inner/listUserId")
    ResultDTO<List<String>> listUserId();

    /**
     * 查询用户是否实名审核
     *
     * @param userOpenId
     * @return
     */
    @GetMapping("/inner/determineIdentity")
    ResultDTO<Boolean> determineIdentity(@RequestParam("userId") String userOpenId);

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
