package com.blockchain.server.btc.controller;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.common.base.util.SSOHelper;
import com.blockchain.server.btc.common.constants.BtcApplicationConstans;
import com.blockchain.server.btc.controller.api.BtcWalletApi;
import com.blockchain.server.btc.service.BtcWalletService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

@Api(BtcWalletApi.MARKET_CONTROLLER_API)
@RestController
@RequestMapping("/wallet")
public class BtcWalletController {
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private BtcWalletService btcWalletService;


    @ApiOperation(value = BtcWalletApi.GetWallet.METHOD_API_NAME, notes = BtcWalletApi.GetWallet.METHOD_API_NOTE)
    @GetMapping("/getWallet")
    public ResultDTO getWallet(HttpServletRequest request,
                               @ApiParam(BtcWalletApi.GetWallet.WALLETTYPE) @RequestParam(value = "walletType", defaultValue = BtcApplicationConstans.TYPE_CCT) String walletType,
                               @ApiParam(BtcWalletApi.GetWallet.TOKENID) @RequestParam("tokenId") Integer tokenId) {
        String userOpenId = SSOHelper.getUserId(redisTemplate, request);
        return ResultDTO.requstSuccess(btcWalletService.selectByUserOpenId(userOpenId, tokenId, walletType));
    }

    @ApiOperation(value = BtcWalletApi.GetWallets.METHOD_API_NAME, notes = BtcWalletApi.GetWallets.METHOD_API_NOTE)
    @GetMapping("/getWallets")
    public ResultDTO getWallets(@ApiParam(BtcWalletApi.GetWallets.WALLETTYPE) @RequestParam(name = "walletType", defaultValue = BtcApplicationConstans.TYPE_CCT) String walletType,
                                HttpServletRequest request) {
        String userId = SSOHelper.getUserId(redisTemplate, request);
        return ResultDTO.requstSuccess(btcWalletService.selectAllByUserOpenId(userId, walletType));
    }
    @ApiOperation(value = BtcWalletApi.Transfer.METHOD_API_NAME, notes = BtcWalletApi.Transfer.METHOD_API_NOTE)
    @PostMapping("/transfer")
    public ResultDTO handleTransfer(HttpServletRequest request,
                                    @ApiParam(BtcWalletApi.Transfer.METHOD_API_FROMTYPE) @RequestParam("fromType") String fromType,
                                    @ApiParam(BtcWalletApi.Transfer.METHOD_API_TOTYPE) @RequestParam("toType") String toType,
                                    @ApiParam(BtcWalletApi.Transfer.METHOD_API_COINNAME) @RequestParam("coinName") String coinName,
                                    @ApiParam(BtcWalletApi.Transfer.METHOD_API_AMOUNT) @RequestParam("amount") BigDecimal amount) {
        String userOpenId = SSOHelper.getUserId(redisTemplate, request);
        return btcWalletService.handleTransfer(userOpenId,fromType, toType, coinName, amount);
    }
}