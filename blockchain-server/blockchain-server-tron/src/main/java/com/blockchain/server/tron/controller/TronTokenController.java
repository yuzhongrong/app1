package com.blockchain.server.tron.controller;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.server.tron.controller.api.TronTokenApi;
import com.blockchain.server.tron.service.TronTokenService;
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
//@Api(tags = TronTokenApi.TRON_TOKEN_API)
//@RestController
//@RequestMapping("/walletIn")
public class TronTokenController {

    @Autowired
    private TronTokenService tronTokenService;

    @ApiOperation(value = TronTokenApi.SelectTronToken.MATHOD_API_NAME, notes = TronTokenApi.SelectTronToken.MATHOD_API_NOTE)
    @PostMapping("/selectTronToken")
    public ResultDTO selectTronToken(@ApiParam(TronTokenApi.SelectTronToken.MATHOD_API_TOKEN_ADDR) @RequestParam("tokenAddr") String tokenAddr) {
        return ResultDTO.requstSuccess(tronTokenService.selectTronTokenByTokenName(tokenAddr));
    }

}
