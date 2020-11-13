package com.blockchain.server.eos.controller;

import com.blockchain.common.base.dto.GasDTO;
import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.server.eos.controller.api.ConfigWalletParamApi;
import com.blockchain.server.eos.controller.api.EosTokenApi;
import com.blockchain.server.eos.entity.ConfigWalletParam;
import com.blockchain.server.eos.service.ConfigWalletParamService;
import com.blockchain.server.eos.service.EosTokenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Harvey
 * @date 2019/2/19 18:09
 * @user WIN10
 */
@Api(ConfigWalletParamApi.CONFIG_WALLET_PARAM_API)
@RestController
@RequestMapping("/configWalletParam")
public class ConfigWalletParamController {

    @Autowired
    private ConfigWalletParamService configWalletParamService;

    @ApiOperation(value = ConfigWalletParamApi.SelectConfigWalletParam.MATHOD_API_NAME, notes = ConfigWalletParamApi.SelectConfigWalletParam.MATHOD_API_NOTE)
    @GetMapping("/selectConfigWalletParam")
    public ResultDTO selectConfigWalletParam(@ApiParam(ConfigWalletParamApi.SelectConfigWalletParam.MATHOD_API_TOKEN_TYPE) @RequestParam("tokenSymbol") String tokenType) {
        GasDTO gasDTO = configWalletParamService.selectConfigWalletParam(configWalletParamService.selectOne(tokenType.toLowerCase()));
        return ResultDTO.requstSuccess(gasDTO);
    }

}
