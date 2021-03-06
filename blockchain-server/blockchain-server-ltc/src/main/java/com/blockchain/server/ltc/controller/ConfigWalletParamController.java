package com.blockchain.server.ltc.controller;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.server.ltc.common.constants.TokenConstans;
import com.blockchain.server.ltc.controller.api.ConfigWalletParamApi;
import com.blockchain.server.ltc.service.ConfigWalletParamService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = ConfigWalletParamApi.MARKET_CONTROLLER_API)
@RestController
@RequestMapping("/walletParam")
public class ConfigWalletParamController {
    @Autowired
    private ConfigWalletParamService walletParamService;

    @ApiOperation(value = ConfigWalletParamApi.GetGasConfig.METHOD_API_NAME, notes = ConfigWalletParamApi.GetGasConfig.METHOD_API_NOTE)
    @GetMapping("/getGasConfig")
    public ResultDTO getGasConfig(@ApiParam(ConfigWalletParamApi.GetGasConfig.TOKENSYMBOL) @RequestParam(value = "tokenSymbol", defaultValue = TokenConstans.TOKEN_SYMBOL) String tokenSymbol) {
        return ResultDTO.requstSuccess(walletParamService.getGasConfig(tokenSymbol));
    }

}