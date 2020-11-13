package com.blockchain.server.otc.feign;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.common.base.dto.WalletChangeDTO;
import com.blockchain.common.base.dto.WalletOrderDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("dapp-teth-server")
public interface TETHFeign {
    //钱包公共接口请求路径
    String WALLET_PATH = "/teth/inner/wallet";
    //钱包交易费用接口请求路径
    String TRANS_PATH = "/teth/inner/walletTx";

    /***
     * 冻结余额
     * @param orderDTO
     * @return
     */
    @PostMapping(TRANS_PATH + "/order")
    ResultDTO order(@RequestBody WalletOrderDTO orderDTO);

    /***
     * 扣减、增加总余额
     * @param changeDTO
     * @return
     */
    @PostMapping(TRANS_PATH + "/change")
    ResultDTO change(@RequestBody WalletChangeDTO changeDTO);

    /***
     * 校验密码
     * @param pass
     * @return
     */
    @GetMapping(WALLET_PATH + "/isPassword")
    ResultDTO isPassword(@RequestParam(name = "password") String pass);


}
