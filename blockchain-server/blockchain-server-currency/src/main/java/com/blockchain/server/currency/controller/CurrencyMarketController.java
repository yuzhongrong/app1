package com.blockchain.server.currency.controller;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.server.currency.controller.api.CurrencyMarketApi;
import com.blockchain.server.currency.service.CurrencyMarketService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(CurrencyMarketApi.MARKET_CONTROLLER_API)
@RestController
@RequestMapping("/market")
public class CurrencyMarketController {

    @Autowired
    private CurrencyMarketService currencyMarketService;

    @ApiOperation(value = CurrencyMarketApi.Get.METHOD_API_NAME,
            notes = CurrencyMarketApi.Get.METHOD_API_NOTE)
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ResultDTO get(
            @ApiParam(CurrencyMarketApi.Get.METHOD_API_CURRENCY_PAIR) String currencyPair) {

        return ResultDTO.requstSuccess(currencyMarketService.get(currencyPair));
    }

    @ApiOperation(value = CurrencyMarketApi.GetLast.METHOD_API_NAME,
            notes = CurrencyMarketApi.GetLast.METHOD_API_NOTE)
    @RequestMapping(value = "/getLast", method = RequestMethod.GET)
    public ResultDTO getLast(
            @ApiParam(CurrencyMarketApi.GetLast.METHOD_API_CURRENCY_PAIR) String currencyPair) {

        return ResultDTO.requstSuccess(currencyMarketService.getLast(currencyPair));
    }

    @ApiOperation(value = CurrencyMarketApi.GetRates.METHOD_API_NAME,
            notes = CurrencyMarketApi.GetRates.METHOD_API_NOTE)
    @RequestMapping(value = "/getRates", method = RequestMethod.GET)
    public ResultDTO getRates(@ApiParam(CurrencyMarketApi.GetRates.METHOD_API_COIN) String coin) {

        return ResultDTO.requstSuccess(currencyMarketService.getRates(coin));
    }

    @ApiOperation(value = CurrencyMarketApi.GetList.METHOD_API_NAME,
            notes = CurrencyMarketApi.GetList.METHOD_API_NOTE)
    @RequestMapping(value = "/getList", method = RequestMethod.GET)
    public ResultDTO getList() {

        return ResultDTO.requstSuccess(currencyMarketService.getList());
    }

    @ApiOperation(value = CurrencyMarketApi.GetHomeList.METHOD_API_NAME,
            notes = CurrencyMarketApi.GetHomeList.METHOD_API_NOTE)
    @RequestMapping(value = "/getHomeList", method = RequestMethod.GET)
    public ResultDTO getHomeList() {

        return ResultDTO.requstSuccess(currencyMarketService.getHomeList());
    }

    @ApiOperation(value = CurrencyMarketApi.GetTopList.METHOD_API_NAME,
            notes = CurrencyMarketApi.GetTopList.METHOD_API_NOTE)
    @RequestMapping(value = "/getTopList", method = RequestMethod.GET)
    public ResultDTO getTopList() {

        return ResultDTO.requstSuccess(currencyMarketService.getTopList());
    }

    @ApiOperation(value = CurrencyMarketApi.GetHistoryList.METHOD_API_NAME,
            notes = CurrencyMarketApi.GetHistoryList.METHOD_API_NOTE)
    @RequestMapping(value = "/getHistoryList", method = RequestMethod.GET)
    public ResultDTO getHistoryList(@ApiParam(CurrencyMarketApi.GetHistoryList.METHOD_API_CURRENCY_PAIR) String currencyPair) {

        return ResultDTO.requstSuccess(currencyMarketService.getHistoryList(currencyPair));
    }

    @ApiOperation(value = CurrencyMarketApi.GetQuoteList.METHOD_API_NAME,
            notes = CurrencyMarketApi.GetQuoteList.METHOD_API_NOTE)
    @RequestMapping(value = "/getQuoteList", method = RequestMethod.GET)
    public ResultDTO getQuoteList(
            @ApiParam(CurrencyMarketApi.GetQuoteList.METHOD_API_CURRENCY_NAME) String currencyName) {

        return ResultDTO.requstSuccess(currencyMarketService.getQuoteList(currencyName));
    }

    @ApiOperation(value = CurrencyMarketApi.Query.METHOD_API_NAME,
            notes = CurrencyMarketApi.Query.METHOD_API_NOTE)
    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public ResultDTO query(
            @ApiParam(CurrencyMarketApi.Query.METHOD_API_CURRENCY_PAIR) String currencyPair,
            @RequestParam(defaultValue = "MINUTE")
            @ApiParam(CurrencyMarketApi.Query.METHOD_API_TIME_TYPE) String timeType,
            @RequestParam(defaultValue = "15")
            @ApiParam(CurrencyMarketApi.Query.METHOD_API_TIME_NUMBER) Integer timeNumber,
            @RequestParam(defaultValue = "0")
            @ApiParam(CurrencyMarketApi.Query.METHOD_API_START) Integer start,
            @RequestParam(defaultValue = "1000")
            @ApiParam(CurrencyMarketApi.Query.METHOD_API_STOP) Integer stop) {

        return ResultDTO.requstSuccess(currencyMarketService.queryForK(currencyPair, timeType, timeNumber, start, stop));
    }

    @ApiOperation(value = CurrencyMarketApi.QuerySortDesc.METHOD_API_NAME,
            notes = CurrencyMarketApi.QuerySortDesc.METHOD_API_NOTE)
    @RequestMapping(value = "/querySortDesc", method = RequestMethod.GET)
    public ResultDTO querySortDesc(
            @ApiParam(CurrencyMarketApi.QuerySortDesc.METHOD_API_CURRENCY_PAIR) String currencyPair,
            @RequestParam(defaultValue = "MINUTE")
            @ApiParam(CurrencyMarketApi.QuerySortDesc.METHOD_API_TIME_TYPE) String timeType,
            @RequestParam(defaultValue = "15")
            @ApiParam(CurrencyMarketApi.QuerySortDesc.METHOD_API_TIME_NUMBER) Integer timeNumber,
            @RequestParam(defaultValue = "0")
            @ApiParam(CurrencyMarketApi.QuerySortDesc.METHOD_API_START) Integer start,
            @RequestParam(defaultValue = "1000")
            @ApiParam(CurrencyMarketApi.QuerySortDesc.METHOD_API_STOP) Integer stop) {

        return ResultDTO.requstSuccess(currencyMarketService.queryForKSortDESC(currencyPair, timeType, timeNumber, start, stop));
    }

}
