package com.blockchain.server.teth.controller;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.server.teth.common.constants.EthConfigConstants;
import com.blockchain.server.teth.controller.api.ConfigWalletParamApi;
import com.blockchain.server.teth.service.IConfigWalletParamService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(ConfigWalletParamApi.MARKET_CONTROLLER_API)
@RestController
@RequestMapping("/walletParam")
public class ConfigWalletParamController {
    @Autowired
    private IConfigWalletParamService walletParamService;

    @ApiOperation(value = ConfigWalletParamApi.GetGasConfig.METHOD_API_NAME, notes = ConfigWalletParamApi.GetGasConfig.METHOD_API_NOTE)
    @GetMapping("/getGasConfig")
    public ResultDTO getGasConfig(@ApiParam(ConfigWalletParamApi.GetGasConfig.TOKENSYMBOL) @RequestParam(value = "tokenSymbol", defaultValue = EthConfigConstants.MODULE_TYPE) String tokenSymbol) {
        return ResultDTO.requstSuccess(walletParamService.getGasConfig(tokenSymbol));
    }

}