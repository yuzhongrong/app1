package com.blockchain.server.tron.controller;

import com.blockchain.common.base.dto.GasDTO;
import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.server.tron.common.constant.TronConstant;
import com.blockchain.server.tron.controller.api.TronConfigWalletParamApi;
import com.blockchain.server.tron.service.TronConfigWalletParamService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Harvey
 * @date 2019/2/19 18:09
 * @user WIN10
 */
@Api(tags = TronConfigWalletParamApi.CONFIG_WALLET_PARAM_API)
@RestController
@RequestMapping("/configWalletParam")
public class TronConfigWalletParamController {

    @Autowired
    private TronConfigWalletParamService configWalletParamService;

    @ApiOperation(value = TronConfigWalletParamApi.SelectConfigWalletParam.MATHOD_API_NAME, notes = TronConfigWalletParamApi.SelectConfigWalletParam.MATHOD_API_NOTE)
    @GetMapping("/selectConfigWalletParam")
    public ResultDTO selectConfigWalletParam(@ApiParam(TronConfigWalletParamApi.SelectConfigWalletParam.MATHOD_API_TOKEN_TYPE) @RequestParam(name = "tokenSymbol", defaultValue = TronConstant.TRON_TOKEN_TYPE) String tokenType) {
        GasDTO gasDTO = configWalletParamService.selectConfigWalletParam(configWalletParamService.selectOne(tokenType.toLowerCase()));
        return ResultDTO.requstSuccess(gasDTO);
    }

}
