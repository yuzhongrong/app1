package com.blockchain.server.user.feign;

import com.blockchain.common.base.dto.ResultDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author huangxl
 * @create 2019-02-28 17:39
 */
@FeignClient("dapp-eos-server")
public interface EosFeign {
    @GetMapping("/eos/inner/walletTx/initEosWallet")
    ResultDTO initEosWallet(@RequestParam("userOpenId") String userOpenId);
}
