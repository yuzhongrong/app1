package com.blockchain.server.cct.feign;

import com.blockchain.common.base.dto.ResultDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "dapp-user-server", path = "/user/inner")
public interface UserFeign {

    /***
     * 检查用户初级认证和黑名单
     * @return
     */
    @GetMapping("/hasLowAuthAndUserList")
    ResultDTO hasLowAuthAndUserList(@RequestParam("userId") String userId);

    /***
     * 免交易手续费
     * @param userId
     * @return
     */
    @PostMapping("/verifyFreeTransaction")
    ResultDTO verifyFreeTransaction(@RequestParam("userId") String userId);
}
