package com.blockchain.server.cmc.feign;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.common.base.dto.WalletBalanceBatchDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author qinhui
 * @version 1.0
 * @date 2020/6/3 11:10
 */
@FeignClient("dapp-ore-server")
public interface OreFegin {

    @PostMapping("/inner/pushUserWallet")
    ResultDTO pushUserWallet(@RequestParam("type") String type, @RequestBody List<WalletBalanceBatchDTO> userWalletList);
}
