package com.blockchain.server.ltc.controller;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.common.base.util.SSOHelper;
import com.blockchain.server.ltc.common.constants.ApplicationConstans;
import com.blockchain.server.ltc.controller.api.WalletApi;
import com.blockchain.server.ltc.service.WalletService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

@Api(tags = WalletApi.MARKET_CONTROLLER_API)
@RestController
@RequestMapping("/wallet")
public class WalletController {
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private WalletService walletService;


    @ApiOperation(value = WalletApi.GetWallet.METHOD_API_NAME, notes = WalletApi.GetWallet.METHOD_API_NOTE)
    @GetMapping("/getWallet")
    public ResultDTO getWallet(HttpServletRequest request,
                               @ApiParam(WalletApi.GetWallet.WALLETTYPE) @RequestParam(value = "walletType", defaultValue = ApplicationConstans.TYPE_CCT) String walletType,
                               @ApiParam(WalletApi.GetWallet.TOKENID) @RequestParam("tokenId") Integer tokenId) {
        String userOpenId = SSOHelper.getUserId(redisTemplate, request);
        return ResultDTO.requstSuccess(walletService.selectByUserOpenId(userOpenId, tokenId, walletType));
    }

    @ApiOperation(value = WalletApi.GetWallets.METHOD_API_NAME, notes = WalletApi.GetWallets.METHOD_API_NOTE)
    @GetMapping("/getWallets")
    public ResultDTO getWallets(@ApiParam(WalletApi.GetWallets.WALLETTYPE) @RequestParam(name = "walletType", defaultValue = ApplicationConstans.TYPE_CCT) String walletType,
                                HttpServletRequest request) {
        String userId = SSOHelper.getUserId(redisTemplate, request);
        return ResultDTO.requstSuccess(walletService.selectAllByUserOpenId(userId, walletType));
    }

    @ApiOperation(value = WalletApi.Transfer.METHOD_API_NAME, notes = WalletApi.Transfer.METHOD_API_NOTE)
    @PostMapping("/transfer")
    public ResultDTO handleTransfer(HttpServletRequest request,
                                    @ApiParam(WalletApi.Transfer.METHOD_API_FROMTYPE) @RequestParam("fromType") String fromType,
                                    @ApiParam(WalletApi.Transfer.METHOD_API_TOTYPE) @RequestParam("toType") String toType,
                                    @ApiParam(WalletApi.Transfer.METHOD_API_COINNAME) @RequestParam("coinName") String coinName,
                                    @ApiParam(WalletApi.Transfer.METHOD_API_AMOUNT) @RequestParam("amount") BigDecimal amount) {
        String userOpenId = SSOHelper.getUserId(redisTemplate, request);
        return ResultDTO.requstSuccess(walletService.handleTransfer(userOpenId, fromType, toType, coinName, amount));
    }

}