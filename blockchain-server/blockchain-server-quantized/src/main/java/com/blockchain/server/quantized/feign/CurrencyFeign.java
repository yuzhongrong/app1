package com.blockchain.server.quantized.feign;

import com.blockchain.common.base.dto.ResultDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@FeignClient(value="dapp-currency-server",path = "currency")
public interface CurrencyFeign {
    //请求路径
    String CONTENT_PATH = "/inner/market";

    /***
     * 保存最新行情信息
     * @param coinName
     * @param unitName
     * @param amount
     * @param total
     * @param timestamp
     * @return
     */
    @PostMapping(CONTENT_PATH + "/save")
    ResultDTO save(@RequestParam("coinName") String coinName,
                   @RequestParam("unitName") String unitName,
                   @RequestParam("amount") BigDecimal amount,
                   @RequestParam("total") BigDecimal total,
                   @RequestParam("timestamp") Long timestamp);
}
