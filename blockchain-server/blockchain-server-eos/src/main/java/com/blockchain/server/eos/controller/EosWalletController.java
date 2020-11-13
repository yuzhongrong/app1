package com.blockchain.server.eos.controller;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.common.base.dto.SessionUserDTO;
import com.blockchain.common.base.util.SSOHelper;
import com.blockchain.server.eos.controller.api.EosWalletApi;
import com.blockchain.server.eos.dto.WalletDTO;
import com.blockchain.server.eos.service.EosWalletService;
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
@Api(EosWalletApi.EOS_WALLET_API)
@RestController
@RequestMapping("/wallet")
public class EosWalletController {

    @Autowired
    private EosWalletService eosWalletService;
    @Autowired
    private RedisTemplate redisTemplate;

    @ApiOperation(value = EosWalletApi.WithdrawDeposit.MATHOD_API_NAME, notes = EosWalletApi.WithdrawDeposit.MATHOD_API_NOTE)
    @PostMapping("/withdrawDeposit")
    public ResultDTO withdrawDeposit(
            @ApiParam(EosWalletApi.WithdrawDeposit.MATHOD_API_PASSWORD) @RequestParam("password") String password,
            @ApiParam(EosWalletApi.WithdrawDeposit.MATHOD_API_ACCOUNT) @RequestParam("toAddr") String account,
            @ApiParam(EosWalletApi.WithdrawDeposit.MATHOD_API_AMOUNT) @RequestParam("amount") BigDecimal amount,
            @ApiParam(EosWalletApi.WithdrawDeposit.MATHOD_API_TOKEN_NAME) @RequestParam("tokenName") String tokenName,
            @ApiParam(EosWalletApi.WithdrawDeposit.MATHOD_API_WALLET_TYPE) @RequestParam("walletType") String walletType,
            @ApiParam(EosWalletApi.WithdrawDeposit.MATHOD_API_REMARK) @RequestParam(value = "remark", required = false) String remark,
            @ApiParam(EosWalletApi.WithdrawDeposit.VERIFY_CODE) @RequestParam(value = "verifyCode", required = false) String verifyCode,
            HttpServletRequest request) {
        SessionUserDTO user = SSOHelper.getUser(redisTemplate, request);
        eosWalletService.handleWithdrawDeposit(user.getId(), password, account, amount, tokenName, walletType, remark, verifyCode, user.getTel());

        return ResultDTO.requstSuccess();
    }

    @ApiOperation(value = EosWalletApi.SelectWalletByWalletType.MATHOD_API_NAME, notes = EosWalletApi.SelectWalletByWalletType.MATHOD_API_NOTE)
    @GetMapping("/selectWalletByWalletType")
    public ResultDTO selectWalletByWalletType(@ApiParam(EosWalletApi.SelectWalletByWalletType.MATHOD_API_WALLET_TYPE) @RequestParam("walletType") String walletType, HttpServletRequest request) {
        String userOpenId = SSOHelper.getUserId(redisTemplate, request);
        List<WalletDTO> walletDTOS = eosWalletService.listWalletByWalletType(walletType, userOpenId);
        return ResultDTO.requstSuccess(walletDTOS);
    }

    @ApiOperation(value = EosWalletApi.SelectWalletByTokenName.MATHOD_API_NAME, notes = EosWalletApi.SelectWalletByTokenName.MATHOD_API_NOTE)
    @GetMapping("/selectWalletByTokenName")
    public ResultDTO selectWalletByTokenName(@ApiParam(EosWalletApi.SelectWalletByTokenName.MATHOD_API_TOKEN_NAME) @RequestParam("tokenName") String tokenName, @ApiParam(EosWalletApi.SelectWalletByTokenName.MATHOD_API_WALLET_TYPE) @RequestParam("walletType") String walletType, HttpServletRequest request) {
        String userOpenId = SSOHelper.getUserId(redisTemplate, request);
        WalletDTO walletDTO = eosWalletService.selectWallet(userOpenId, tokenName, walletType);
        return ResultDTO.requstSuccess(walletDTO);
    }

    @ApiOperation(value = EosWalletApi.Transfer.METHOD_API_NAME, notes = EosWalletApi.Transfer.METHOD_API_NOTE)
    @PostMapping("/transfer")
    public ResultDTO handleTransfer(HttpServletRequest request,
                                    @ApiParam(EosWalletApi.Transfer.METHOD_API_FROMTYPE) @RequestParam("fromType") String fromType,
                                    @ApiParam(EosWalletApi.Transfer.METHOD_API_TOTYPE) @RequestParam("toType") String toType,
                                    @ApiParam(EosWalletApi.Transfer.METHOD_API_COINNAME) @RequestParam("coinName") String coinName,
                                    @ApiParam(EosWalletApi.Transfer.METHOD_API_AMOUNT) @RequestParam("amount") BigDecimal amount) {
        String userOpenId = SSOHelper.getUserId(redisTemplate, request);
        return ResultDTO.requstSuccess(eosWalletService.handleTransfer(userOpenId, fromType, toType, coinName, amount));
    }

}
