package com.blockchain.server.eos.controller;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.server.eos.controller.api.EosTokenApi;
import com.blockchain.server.eos.controller.api.EosWalletInApi;
import com.blockchain.server.eos.service.EosTokenService;
import com.blockchain.server.eos.service.EosWalletInService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Harvey
 * @date 2019/2/19 18:09
 * @user WIN10
 */
@Api(EosTokenApi.EOS_TOKEN_API)
@RestController
@RequestMapping("/walletIn")
public class EosTokenController {

    @Autowired
    private EosTokenService eosTokenService;

    @ApiOperation(value = EosTokenApi.SelectEosToken.MATHOD_API_NAME, notes = EosTokenApi.SelectEosToken.MATHOD_API_NOTE)
    @PostMapping("/selectEosToken")
    public ResultDTO selectEosToken(@ApiParam(EosTokenApi.SelectEosToken.MATHOD_API_TOKEN_NAME) @RequestParam("tokenName") String tokenName) {
        return ResultDTO.requstSuccess(eosTokenService.selectEosTokenByTokenName(tokenName));
    }

}
