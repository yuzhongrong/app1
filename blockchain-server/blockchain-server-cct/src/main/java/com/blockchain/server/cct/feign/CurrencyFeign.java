package com.blockchain.server.cct.feign;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.server.cct.dto.rpc.currency.CurrencyMarketDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.Date;

@FeignClient("dapp-currency-server")
public interface CurrencyFeign {
    //请求路径
    String CONTENT_PATH = "/currency/inner/market";

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
                   @RequestParam("timestamp") Long timestamp,
                   @RequestParam("tradingType") String tradingType);

    /***
     * 查询最新行情
     * @param currencyPair
     * @return
     */
    @GetMapping("/currency/market/get")
    ResultDTO<CurrencyMarketDTO> get(@RequestParam("currencyPair") String currencyPair);

    /***
     * 查询今天之前最新的行情
     * @param currencyPair
     * @return
     */
    @GetMapping("/currency/market/getLast")
    ResultDTO<CurrencyMarketDTO> getLast(@RequestParam("currencyPair") String currencyPair);
}
