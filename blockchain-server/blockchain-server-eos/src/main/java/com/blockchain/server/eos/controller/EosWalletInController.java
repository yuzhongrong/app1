package com.blockchain.server.eos.controller;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.server.eos.controller.api.EosWalletApi;
import com.blockchain.server.eos.controller.api.EosWalletInApi;
import com.blockchain.server.eos.dto.WalletDTO;
import com.blockchain.server.eos.service.EosWalletInService;
import com.blockchain.server.eos.service.EosWalletService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author Harvey
 * @date 2019/2/19 18:09
 * @user WIN10
 */
@Api(EosWalletInApi.EOS_WALLET_IN_API)
@RestController
@RequestMapping("/walletIn")
public class EosWalletInController {

    @Autowired
    private EosWalletInService eosWalletInService;

    @ApiOperation(value = EosWalletInApi.ListWalletInAccount.MATHOD_API_NAME, notes = EosWalletInApi.ListWalletInAccount.MATHOD_API_NOTE)
    @PostMapping("/listWalletInAccount")
    public ResultDTO listWalletInAccount(@ApiParam(EosWalletInApi.ListWalletInAccount.MATHOD_API_TOKEN_NAME) @RequestParam("tokenName") String tokenName) {
        return ResultDTO.requstSuccess(eosWalletInService.selectWalletInAccount(tokenName));
    }

}
