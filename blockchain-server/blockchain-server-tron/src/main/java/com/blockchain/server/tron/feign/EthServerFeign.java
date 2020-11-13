package com.blockchain.server.tron.feign;

import com.blockchain.common.base.dto.ResultDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("dapp-eth-server")
public interface EthServerFeign {

    /**
     * 验证钱包密码 调用eth
     *
     * @param password 加密密码
     * @return
     */
    @GetMapping("/eth/inner/wallet/isPassword")
    ResultDTO isPassword(@RequestParam("openId") String openId, @RequestParam(name = "password", required = false) String password);

}
