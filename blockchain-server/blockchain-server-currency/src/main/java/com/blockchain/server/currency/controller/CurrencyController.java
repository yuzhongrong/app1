package com.blockchain.server.currency.controller;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.common.base.util.HttpRequestUtil;
import com.blockchain.server.currency.controller.api.CurrencyApi;
import com.blockchain.server.currency.dto.MyTokenDTO;
import com.blockchain.server.currency.service.CurrencyPairService;
import com.blockchain.server.currency.service.CurrencyService;
import com.blockchain.server.currency.service.MyTokenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Api(CurrencyApi.MARKET_CONTROLLER_API)
@RestController
@RequestMapping("/currency")
public class CurrencyController {

    @Autowired
    private CurrencyPairService currencyPairService;

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private com.blockchain.server.currency.service.MyTokenService myTokenService;

    @ApiOperation(value = CurrencyApi.GetUsableList.METHOD_API_NAME,
            notes = CurrencyApi.GetUsableList.METHOD_API_NOTE)
    @RequestMapping(value = "/getUsableList", method = RequestMethod.GET)
    public ResultDTO getUsableList() {

        return ResultDTO.requstSuccess(currencyPairService.getUsableList());
    }

    @ApiOperation(value = CurrencyApi.GetMyToken.METHOD_API_NAME,
            notes = CurrencyApi.GetMyToken.METHOD_API_NOTE)
    @RequestMapping(value = "/marketAll", method = RequestMethod.GET)
    @ResponseBody
    public List<MyTokenDTO> getMarketAll() {

        return  myTokenService.getMyToken();
    }


    @ApiOperation(value = CurrencyApi.GetCurrencyInfo.METHOD_API_NAME,
            notes = CurrencyApi.GetCurrencyInfo.METHOD_API_NOTE)
    @RequestMapping(value = "/getCurrencyInfo", method = RequestMethod.GET)
    public ResultDTO getCurrencyInfo(
            HttpServletRequest request,
            @ApiParam(CurrencyApi.GetCurrencyInfo.METHOD_API_CURRENCY_NAME) String currencyName) {

        return ResultDTO.requstSuccess(currencyService.getCurrencyInfo(currencyName, HttpRequestUtil.getUserLocale(request)));
    }

    @ApiOperation(value = CurrencyApi.GetQuoteCurrency.METHOD_API_NAME,
            notes = CurrencyApi.GetQuoteCurrency.METHOD_API_NOTE)
    @RequestMapping(value = "/getQuoteCurrency", method = RequestMethod.GET)
    public ResultDTO getQuoteCurrency() {

        return ResultDTO.requstSuccess(currencyService.getQuoteCurrency());
    }

}
