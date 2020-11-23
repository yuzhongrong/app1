package com.blockchain.server.cmc.controller;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.server.cmc.common.constants.BtcConstans;
import com.blockchain.server.cmc.controller.api.BtcTokenApi;
import com.blockchain.server.cmc.service.BtcTokenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(BtcTokenApi.MARKET_CONTROLLER_API)
@RestController
@RequestMapping("/walletToken")
public class BtcTokenController {
    @Autowired
    private BtcTokenService btcTokenService;


    @ApiOperation(value = BtcTokenApi.ListToken.METHOD_API_NAME, notes = BtcTokenApi.ListToken.METHOD_API_NOTE)
    @GetMapping("/listToken")
    public ResultDTO listToken() {
        return ResultDTO.requstSuccess(btcTokenService.listToken());
    }

    @ApiOperation(value = BtcTokenApi.GetToken.METHOD_API_NAME, notes = BtcTokenApi.GetToken.METHOD_API_NOTE)
    @GetMapping("/getToken")
    public ResultDTO getToken(@ApiParam(BtcTokenApi.GetToken.TOKENID) @RequestParam(value = "tokenId", defaultValue = BtcConstans.BTC_PROPERTY_ID + "") Integer tokenId) {
        return ResultDTO.requstSuccess(btcTokenService.selectTokenById(tokenId));
    }

}