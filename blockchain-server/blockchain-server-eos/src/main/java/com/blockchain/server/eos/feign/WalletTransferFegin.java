package com.blockchain.server.eos.feign;

import com.blockchain.common.base.dto.ResultDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Harvey
 * @date 2019/2/26 18:02
 * @user WIN10
 */
@FeignClient("dapp-eth-server")
public interface WalletTransferFegin {

    /**
     * 验证密码是否正确
     * @param password
     * @return
     */
    @GetMapping("/eth/inner/wallet/isPassword")
    ResultDTO isPassword(@RequestParam("password") String password);

}
