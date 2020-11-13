package com.blockchain.server.otc.controller;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.server.otc.common.constant.CommonConstans;
import com.blockchain.server.otc.controller.api.CoinApi;
import com.blockchain.server.otc.dto.coin.CoinDTO;
import com.blockchain.server.otc.dto.coin.CoinServiceChargeDTO;
import com.blockchain.server.otc.service.CoinService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(CoinApi.COIN_API)
@RestController
@RequestMapping("/coin")
public class CoinController {

    @Autowired
    private CoinService coinService;

    @ApiOperation(value = CoinApi.listCoin.METHOD_TITLE_NAME,
            notes = CoinApi.listCoin.METHOD_TITLE_NOTE)
    @GetMapping("/listCoin")
    public ResultDTO listCoin() {
        List<CoinDTO> coinList = coinService.listCoin(CommonConstans.YES);
        return ResultDTO.requstSuccess(coinList);
    }

    @ApiOperation(value = CoinApi.listCoinServiceCharge.METHOD_TITLE_NAME,
            notes = CoinApi.listCoinServiceCharge.METHOD_TITLE_NOTE)
    @GetMapping("/listCoinServiceCharge")
    public ResultDTO listCoinServiceCharge() {
        List<CoinServiceChargeDTO> coinList = coinService.listCoinServiceCharge();
        return ResultDTO.requstSuccess(coinList);
    }

}
