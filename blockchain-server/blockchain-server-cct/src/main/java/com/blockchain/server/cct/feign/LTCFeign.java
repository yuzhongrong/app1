package com.blockchain.server.cct.feign;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.common.base.dto.WalletChangeDTO;
import com.blockchain.common.base.dto.WalletOrderDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "dapp-ltc-server", path = "/ltc/inner")
public interface LTCFeign {

    String TRANS_PATH = "/walletTx";

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
}
