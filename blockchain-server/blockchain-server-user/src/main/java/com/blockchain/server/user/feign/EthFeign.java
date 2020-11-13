package com.blockchain.server.user.feign;

import com.blockchain.common.base.dto.ResultDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author huangxl
 * @create 2019-02-28 17:39
 */
@FeignClient("dapp-eth-server")
public interface EthFeign {
    @GetMapping("/eth/inner/wallet/initWallets")
    ResultDTO initWallets(@RequestParam("userOpenId") String userOpenId);
}
