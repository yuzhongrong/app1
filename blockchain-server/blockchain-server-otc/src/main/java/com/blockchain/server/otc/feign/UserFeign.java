package com.blockchain.server.otc.feign;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.server.otc.dto.user.UserBaseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "dapp-user-server", path = "/user/inner")
public interface UserFeign {
    /***
     * 查询用户主体信息
     * @return
     */
    @GetMapping("/selectUserInfoById")
    ResultDTO<UserBaseDTO> selectUserInfoById(@RequestParam("userId") String userId);

    /***
     * 检查用户初级认证和黑名单
     * @return
     */
    @GetMapping("/hasLowAuthAndUserList")
    ResultDTO hasLowAuthAndUserList(@RequestParam("userId") String userId);

    /***
     * 检查用户高级认证和黑名单
     * @return
     */
    @GetMapping("/hasHighAuthAndUserList")
    ResultDTO hasHighAuthAndUserList(@RequestParam("userId") String userId);

    /***
     * 免交易手续费
     * @param userId
     * @return
     */
    @PostMapping("/verifyFreeTransaction")
    ResultDTO<Boolean> verifyFreeTransaction(@RequestParam("userId") String userId);

    /***
     * 是否可以发布广告
     * true为可以
     * @param userId
     * @return
     */
    @PostMapping("/verifyFreePushAd")
    ResultDTO<Boolean> verifyFreePushAd(@RequestParam("userId") String userId);
}
