package com.blockchain.server.cct.feign;

import com.blockchain.common.base.dto.ResultDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("dapp-databot-server")
public interface BotFeign {

    String CONTENT_PATH = "/bot/inner";

    /***
     * 根据交易对查询
     * @param coinName
     * @param unitName
     * @return
     */
    @GetMapping(CONTENT_PATH + "/getCurrencyConfigStatus")
    ResultDTO<Boolean> getCurrencyConfigStatus(@RequestParam("coinName") String coinName,
                                               @RequestParam("unitName") String unitName);
}
