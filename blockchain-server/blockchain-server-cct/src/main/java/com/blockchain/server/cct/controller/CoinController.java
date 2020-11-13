package com.blockchain.server.cct.controller;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.server.cct.common.enums.CctDataEnums;
import com.blockchain.server.cct.controller.api.CoinApi;
import com.blockchain.server.cct.dto.coin.TradingOnDTO;
import com.blockchain.server.cct.entity.Coin;
import com.blockchain.server.cct.service.CoinService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(CoinApi.COIN_API)
@RestController
@RequestMapping("/coin")
public class CoinController {

    @Autowired
    private CoinService coinService;

    @ApiOperation(value = CoinApi.listTradingOn.METHOD_TITLE_NAME,
            notes = CoinApi.listTradingOn.METHOD_TITLE_NOTE)
    @GetMapping("/listTradingOn")
    public ResultDTO listTradingOn(
            @ApiParam(CoinApi.listTradingOn.METHOD_API_UNITNAME) @RequestParam("unitName") String unitName) {
        List<TradingOnDTO> tradingOns = coinService.listCoinByUnit(unitName, CctDataEnums.COMMON_STATUS_YES.getStrVlue());
        return ResultDTO.requstSuccess(tradingOns);
    }

    @ApiOperation(value = CoinApi.listCoin.METHOD_TITLE_NAME,
            notes = CoinApi.listCoin.METHOD_TITLE_NOTE)
    @GetMapping("/listCoin")
    public ResultDTO listCoin() {
        List<String> coins = coinService.listCoinGroupByUnit();
        return ResultDTO.requstSuccess(coins);
    }
}
