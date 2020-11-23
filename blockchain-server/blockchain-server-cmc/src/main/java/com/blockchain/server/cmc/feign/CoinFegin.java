package com.blockchain.server.cmc.feign;

import com.blockchain.common.base.dto.ResultDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("dapp-ore-server")
public interface CoinFegin {


    /**
     * 挖矿激活
     *
     * @param inviteUserIdHex 邀请码
     * @return
     */
    @PostMapping("/orePower/insertPower")
    ResultDTO<Integer> insertPower(@RequestParam("inviteUserIdHex") String inviteUserIdHex);

    /**
     * 提取矿金交易状态回调
     *
     * @param hash   交易hash
     * @param status 交易状态
     * @return
     */
    @PostMapping("/oreDigedCoin/extractCoinCallBack")
    ResultDTO<String> extractCoinCallBack(@RequestParam("hash") String hash, @RequestParam("status") Boolean status);

}
