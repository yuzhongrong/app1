package com.blockchain.server.databot.inner;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.server.databot.service.CurrencyConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/inner")
public class BotInner {

    @Autowired
    private CurrencyConfigService currencyConfigService;

    /***
     * 根据交易对查询K线机器人状态
     * @param coinName
     * @param unitName
     * @return
     */
    @GetMapping("/getCurrencyConfigStatus")
    public ResultDTO<Boolean> getCurrencyConfigStatus(@RequestParam("coinName") String coinName,
                                                      @RequestParam("unitName") String unitName) {
        Boolean flag = currencyConfigService.checkCurrencyPairStatusIsY(coinName + "-" + unitName);
        return ResultDTO.requstSuccess(flag);
    }
}
