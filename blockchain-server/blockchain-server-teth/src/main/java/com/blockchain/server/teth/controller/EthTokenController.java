package com.blockchain.server.teth.controller;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.server.teth.controller.api.EthTokenApi;
import com.blockchain.server.teth.service.IEthTokenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author YH
 * @date 2018年11月6日15:51:56
 */
@Api(EthTokenApi.MARKET_CONTROLLER_API)
@RestController
@RequestMapping("/token")
public class EthTokenController {
    @Autowired
    IEthTokenService ethTokenService;

    @ApiOperation(value = EthTokenApi.FindToken.METHOD_API_NAME, notes = EthTokenApi.FindToken.METHOD_API_NOTE)
    @GetMapping("/findToken")
    public ResultDTO findToken(@ApiParam(EthTokenApi.FindToken.METHOD_API_TOKENADDR) @RequestParam(name = "tokenAddr") String tokenAddr) {
        return ResultDTO.requstSuccess(ethTokenService.findByTokenAddr(tokenAddr));
    }

    @ApiOperation(value = EthTokenApi.SelectTokenAll.METHOD_API_NAME, notes = EthTokenApi.SelectTokenAll.METHOD_API_NOTE)
    @GetMapping("/selectTokenAll")
    public ResultDTO selectTokenAll() {
        return ResultDTO.requstSuccess(ethTokenService.selectAll());
    }
}
