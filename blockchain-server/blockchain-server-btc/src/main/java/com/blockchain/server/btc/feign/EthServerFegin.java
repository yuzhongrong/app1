package com.blockchain.server.btc.feign;

import com.blockchain.common.base.dto.ResultDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@FeignClient("dapp-eth-server")
public interface EthServerFegin {

    /**
     * 验证钱包密码 调用eth
     *
     * @param password 加密密码
     * @return
     */
    @GetMapping("/eth/inner/wallet/isPassword")
    ResultDTO isPassword(@RequestParam(name = "password", required = false) String password);

    @PostMapping("/eth/inner/walletTx/transfer")
    ResultDTO transfer(@RequestParam(name = "userOpenId") String userOpenId,
                       @RequestParam(name = "fromType") String fromType,
                       @RequestParam(name = "toType") String toType,
                       @RequestParam(name = "coinName") String coinName,
                       @RequestParam(name = "amount") BigDecimal amount);
}
