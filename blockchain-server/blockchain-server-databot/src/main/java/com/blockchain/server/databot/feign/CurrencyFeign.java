package com.blockchain.server.databot.feign;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.server.databot.dto.rpc.CurrencyMarketDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@FeignClient(name = "dapp-currency-server")
public interface CurrencyFeign {

    String INNER_PATH = "/currency/inner"; //inner内部接口访问前缀
    String MARKET_PATH = "/currency/market"; //market开放接口访问前缀

    /***
     * 保存最新行情信息
     * @param coinName
     * @param unitName
     * @param amount
     * @param total
     * @param timestamp
     * @return
     */
    @PostMapping(INNER_PATH + "/market/save")
    ResultDTO save(@RequestParam("coinName") String coinName,
                   @RequestParam("unitName") String unitName,
                   @RequestParam("amount") BigDecimal amount,
                   @RequestParam("total") BigDecimal total,
                   @RequestParam("timestamp") Long timestamp,
                   @RequestParam("tradingType") String tradingType);

    /***
     * 获取数字货币最新行情
     * @param currencyPair
     * @return
     */
    @GetMapping(MARKET_PATH + "/get")
    ResultDTO<CurrencyMarketDTO> get(@RequestParam("currencyPair") String currencyPair);
}
