package com.blockchain.server.btc.inner;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.common.base.dto.WalletBalanceBatchDTO;
import com.blockchain.server.btc.inner.api.BtcWalletInnerApi;
import com.blockchain.server.btc.service.BtcWalletService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(BtcWalletInnerApi.MARKET_CONTROLLER_API)
@RestController
@RequestMapping("/inner/wallet")
public class BtcWalletInner {

    @Autowired
    private BtcWalletService btcWalletService;

    @ApiOperation(value = BtcWalletInnerApi.CreateWallet.METHOD_API_NAME, notes = BtcWalletInnerApi.CreateWallet.METHOD_API_NOTE)
    @GetMapping("/createWallet")
    public ResultDTO createWallet(@ApiParam(BtcWalletInnerApi.CreateWallet.USEROPENID) @RequestParam("userOpenId") String userOpenId) {
        return ResultDTO.requstSuccess(btcWalletService.insertWallet(userOpenId));
    }

    @ApiOperation(value = BtcWalletInnerApi.GetBalanceByIdAndTypes.METHOD_API_NAME, notes = BtcWalletInnerApi.GetBalanceByIdAndTypes.METHOD_API_NOTE)
    @GetMapping("/getBalanceByIdAndTypes")
    public ResultDTO getBalanceByIdAndTypes(@ApiParam(BtcWalletInnerApi.GetBalanceByIdAndTypes.USEROPENID) @RequestParam("userOpenId") String userOpenId,
                                            @ApiParam(BtcWalletInnerApi.GetBalanceByIdAndTypes.WALLET_TYPE) @RequestParam(name = "walletTypes", required = false) String[] walletTypes) {
        return ResultDTO.requstSuccess(btcWalletService.getBalanceByIdAndTypes(userOpenId, walletTypes));
    }

    @PostMapping("/getBalanceByIdAndTypesBatch")
    ResultDTO getBalanceByIdAndTypesBatch(@RequestParam(name = "walletTypes")String[] walletTypes){
        btcWalletService.getBalanceByIdAndTypesBatch(walletTypes);
        return ResultDTO.requstSuccess();
    }

}