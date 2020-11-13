package com.blockchain.server.ltc.inner;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.common.base.dto.WalletBalanceBatchDTO;
import com.blockchain.server.ltc.inner.api.WalletInnerApi;
import com.blockchain.server.ltc.service.WalletService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(WalletInnerApi.MARKET_CONTROLLER_API)
@RestController
@RequestMapping("/inner/wallet")
public class WalletInner {

    @Autowired
    private WalletService walletService;

    @ApiOperation(value = WalletInnerApi.CreateWallet.METHOD_API_NAME, notes = WalletInnerApi.CreateWallet.METHOD_API_NOTE)
    @GetMapping("/createWallet")
    public ResultDTO createWallet(@ApiParam(WalletInnerApi.CreateWallet.USEROPENID) @RequestParam("userOpenId") String userOpenId) {
        return ResultDTO.requstSuccess(walletService.insertWallet(userOpenId));
    }

    @ApiOperation(value = WalletInnerApi.GetBalanceByIdAndTypes.METHOD_API_NAME, notes = WalletInnerApi.GetBalanceByIdAndTypes.METHOD_API_NOTE)
    @GetMapping("/getBalanceByIdAndTypes")
    public ResultDTO getBalanceByIdAndTypes(@ApiParam(WalletInnerApi.GetBalanceByIdAndTypes.USEROPENID) @RequestParam("userOpenId") String userOpenId,
                                            @ApiParam(WalletInnerApi.GetBalanceByIdAndTypes.WALLET_TYPE) @RequestParam(name = "walletTypes", required = false) String[] walletTypes) {
        return ResultDTO.requstSuccess(walletService.getBalanceByIdAndTypes(userOpenId, walletTypes));
    }

    @PostMapping("/getBalanceByIdAndTypesBatch")
    ResultDTO getBalanceByIdAndTypesBatch(@RequestParam(name = "walletTypes", required = false)String[] walletTypes){
        walletService.getBalanceByIdAndTypesBatch(walletTypes);
        return ResultDTO.requstSuccess();
    }

}