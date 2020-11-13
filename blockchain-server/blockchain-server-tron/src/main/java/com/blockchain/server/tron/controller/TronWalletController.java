package com.blockchain.server.tron.controller;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.common.base.dto.SessionUserDTO;
import com.blockchain.common.base.util.SSOHelper;
import com.blockchain.server.tron.controller.api.TronWalletApi;
import com.blockchain.server.tron.entity.TronWallet;
import com.blockchain.server.tron.service.TronWalletService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author Harvey
 * @date 2019/2/19 18:09
 * @user WIN10
 */
@Api(tags = TronWalletApi.TRON_WALLET_API)
@RestController
@RequestMapping("/wallet")
public class TronWalletController {

    @Autowired
    private TronWalletService eosWalletService;
    @Autowired
    private RedisTemplate redisTemplate;


    @ApiOperation(value = TronWalletApi.WithdrawDeposit.MATHOD_API_NAME, notes = TronWalletApi.WithdrawDeposit.MATHOD_API_NOTE)
    @PostMapping("/withdrawDeposit")
    public ResultDTO withdrawDeposit(
            @ApiParam(TronWalletApi.WithdrawDeposit.MATHOD_API_PASSWORD) @RequestParam("password") String password,
            @ApiParam(TronWalletApi.WithdrawDeposit.MATHOD_API_ACCOUNT) @RequestParam("toAddr") String toAddr,
            @ApiParam(TronWalletApi.WithdrawDeposit.MATHOD_API_AMOUNT) @RequestParam("amount") BigDecimal amount,
            @ApiParam(TronWalletApi.WithdrawDeposit.MATHOD_API_TOKEN_ADDR) @RequestParam("tokenAddr") String tokenAddr,
            @ApiParam(TronWalletApi.WithdrawDeposit.MATHOD_API_WALLET_TYPE) @RequestParam("walletType") String walletType,
            @ApiParam(TronWalletApi.WithdrawDeposit.VERIFY_CODE) @RequestParam(name = "verifyCode", required = false) String verifyCode,
            HttpServletRequest request) {
        SessionUserDTO user = SSOHelper.getUser(redisTemplate, request);
        eosWalletService.handleWithdrawDeposit(user.getId(), password, toAddr, amount, tokenAddr, walletType, verifyCode, user.getTel());

        return ResultDTO.requstSuccess();
    }

    @ApiOperation(value = TronWalletApi.SelectWalletByWalletType.MATHOD_API_NAME, notes = TronWalletApi.SelectWalletByWalletType.MATHOD_API_NOTE)
    @GetMapping("/getWallets")
    public ResultDTO getWallets(@ApiParam(TronWalletApi.SelectWalletByWalletType.MATHOD_API_WALLET_TYPE) @RequestParam("walletType") String walletType, HttpServletRequest request) {
        String userId = SSOHelper.getUserId(redisTemplate, request);
        List<TronWallet> tronWallet = eosWalletService.listWalletByWalletType(walletType, userId);
        return ResultDTO.requstSuccess(tronWallet);
    }

    @ApiOperation(value = TronWalletApi.SelectWalletByTokenName.MATHOD_API_NAME, notes = TronWalletApi.SelectWalletByTokenName.MATHOD_API_NOTE)
    @GetMapping("/getWallet")
    public ResultDTO selectWalletByTokenName(@ApiParam(TronWalletApi.SelectWalletByTokenName.MATHOD_API_TOKEN_ADDR) @RequestParam("tokenAddr") String tokenAddr, @ApiParam(TronWalletApi.SelectWalletByTokenName.MATHOD_API_WALLET_TYPE) @RequestParam("walletType") String walletType, HttpServletRequest request) {
        String userId = SSOHelper.getUserId(redisTemplate, request);
        TronWallet tronWallet = eosWalletService.selectWallet(userId, tokenAddr, walletType);
        return ResultDTO.requstSuccess(tronWallet);
    }

    @ApiOperation(value = TronWalletApi.Transfer.METHOD_API_NAME, notes = TronWalletApi.Transfer.METHOD_API_NOTE)
    @PostMapping("/transfer")
    public ResultDTO handleTransfer(HttpServletRequest request,
                                    @ApiParam(TronWalletApi.Transfer.METHOD_API_FROMTYPE) @RequestParam("fromType") String fromType,
                                    @ApiParam(TronWalletApi.Transfer.METHOD_API_TOTYPE) @RequestParam("toType") String toType,
                                    @ApiParam(TronWalletApi.Transfer.METHOD_API_COINNAME) @RequestParam("coinName") String coinName,
                                    @ApiParam(TronWalletApi.Transfer.METHOD_API_AMOUNT) @RequestParam("amount") BigDecimal amount) {
        String userId = SSOHelper.getUserId(redisTemplate, request);
        return ResultDTO.requstSuccess(eosWalletService.handleTransfer(userId, fromType, toType, coinName, amount));
    }

}
