package com.blockchain.server.currency.inner;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.server.currency.inner.api.CurrencyMarketInnerApi;
import com.blockchain.server.currency.service.CurrencyMarketService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Date;

@Api(CurrencyMarketInnerApi.MARKET_CONTROLLER_API)
@RestController
@RequestMapping("/inner/market")
public class CurrencyMarketInnerController {

    @Autowired
    private CurrencyMarketService currencyMarketService;

    @ApiOperation(value = CurrencyMarketInnerApi.Save.METHOD_API_NAME,
            notes = CurrencyMarketInnerApi.Save.METHOD_API_NOTE)
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResultDTO save(
            @ApiParam(CurrencyMarketInnerApi.Save.METHOD_API_COIN_NAME) String coinName,
            @ApiParam(CurrencyMarketInnerApi.Save.METHOD_API_UNIT_NAME) String unitName,
            @ApiParam(CurrencyMarketInnerApi.Save.METHOD_API_AMOUNT) BigDecimal amount,
            @ApiParam(CurrencyMarketInnerApi.Save.METHOD_API_TOTAL) BigDecimal total,
            @ApiParam(CurrencyMarketInnerApi.Save.METHOD_API_TIMESTAMP) Long timestamp,
            @ApiParam(CurrencyMarketInnerApi.Save.METHOD_API_TRADINGTYPE) String tradingType){
        currencyMarketService.save(coinName, unitName, amount, total, timestamp,tradingType,null);
        return ResultDTO.requstSuccess();
    }

}
