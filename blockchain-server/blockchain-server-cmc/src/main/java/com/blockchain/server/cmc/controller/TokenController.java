package com.blockchain.server.cmc.controller;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.server.cmc.common.constants.TokenConstans;
import com.blockchain.server.cmc.controller.api.TokenApi;
import com.blockchain.server.cmc.service.TokenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = TokenApi.MARKET_CONTROLLER_API)
@RestController
@RequestMapping("/walletToken")
public class TokenController {
    @Autowired
    private TokenService tokenService;

    @ApiOperation(value = TokenApi.ListToken.METHOD_API_NAME, notes = TokenApi.ListToken.METHOD_API_NOTE)
    @GetMapping("/listToken")
    public ResultDTO listToken() {
        return ResultDTO.requstSuccess(tokenService.listToken());
    }

    @ApiOperation(value = TokenApi.GetToken.METHOD_API_NAME, notes = TokenApi.GetToken.METHOD_API_NOTE)
    @GetMapping("/getToken")
    public ResultDTO getToken(@ApiParam(TokenApi.GetToken.TOKENID) @RequestParam(value = "tokenId", defaultValue = TokenConstans.TOKEN_PROPERTY_ID + "") Integer tokenId) {
        return ResultDTO.requstSuccess(tokenService.selectTokenById(tokenId));
    }

}