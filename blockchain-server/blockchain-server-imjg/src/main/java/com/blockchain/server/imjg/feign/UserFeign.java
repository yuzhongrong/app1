package com.blockchain.server.imjg.feign;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.server.imjg.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("dapp-user-server")
public interface UserFeign {
    String CONTENT_PATH = "/user/inner";

    /***
     * 根据id查询用户信息
     * @param userId
     * @return
     */
    @GetMapping(CONTENT_PATH + "/selectUserInfoById")
    ResultDTO<UserDTO> getUserById(@RequestParam("userId") String userId);
}
