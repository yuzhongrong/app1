package com.blockchain.server.user.feign;

import com.blockchain.common.base.dto.ResultDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author huangxl
 * @create 2019-02-28 17:40
 */
@FeignClient("dapp-ltc-server")
public interface LtcFeign {
    @GetMapping("/ltc/inner/wallet/createWallet")
    ResultDTO createWallet(@RequestParam("userOpenId") String userOpenId);
}
