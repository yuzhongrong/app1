package com.blockchain.server.tron.inner;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.server.tron.dto.*;
import com.blockchain.server.tron.inner.api.WalletPayApi;
import com.blockchain.server.tron.service.TronWalletPayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author YH
 * @date 2018年11月5日19:22:49
 */
//@Api(WalletPayApi.MARKET_CONTROLLER_API)
//@RestController("walletPayInnerController")
//@RequestMapping("/inner")
public class WalletPayController {

    @Autowired
    private TronWalletPayService tronWalletPayService;
    @Autowired
    private RedisTemplate redisTemplate;

    @ApiOperation(value = WalletPayApi.Rsacode.METHOD_API_NAME, notes = WalletPayApi.Rsacode.METHOD_API_NOTE)
    @GetMapping("/rsacode")
    public ResultDTO rsacode(
            @ApiParam(WalletPayApi.Rsacode.METHOD_API_PREPAYID) @RequestParam("prePayId") String prePayId
    ) {
        String publicKey = tronWalletPayService.getPublicById(prePayId);
        return ResultDTO.requstSuccess(publicKey);
    }

    @ApiOperation(value = WalletPayApi.Order.METHOD_API_NAME, notes = WalletPayApi.Order.METHOD_API_NOTE)
    @PostMapping("/order")
    public ResultDTO order(
            @ApiParam(WalletPayApi.Order.METHOD_API_INPARAMS) PrepaymentInsertInDTO inParams
    ) {
        PrepaymentInsertOutDTO outParams = tronWalletPayService.insertOrder(inParams);
        return ResultDTO.requstSuccess(outParams);
    }


    @ApiOperation(value = WalletPayApi.Refund.METHOD_API_NAME, notes = WalletPayApi.Refund.METHOD_API_NOTE)
    @PostMapping("/refund")
    public ResultDTO refund(
            @ApiParam(WalletPayApi.Refund.METHOD_API_INPARAMS) RefundInDTO inParams
    ) {
        RefundOutDTO refundOutDTO = tronWalletPayService.insertRefund(inParams);
        return ResultDTO.requstSuccess(refundOutDTO);
    }

    @ApiOperation(value = WalletPayApi.QueryOrder.METHOD_API_NAME, notes = WalletPayApi.QueryOrder.METHOD_API_NOTE)
    @PostMapping("/query/order")
    public ResultDTO queryOrder(
            @ApiParam(WalletPayApi.QueryOrder.METHOD_API_APPID) @RequestParam("appId") String appId,
            @ApiParam(WalletPayApi.QueryOrder.METHOD_API_APPSECRET) @RequestParam("appSecret") String appSecret,
            @ApiParam(WalletPayApi.QueryOrder.METHOD_API_TRADENO) @RequestParam("tradeNo") String tradeNo
    ) {
        PayQueryOrderDTO payQueryOrderDTO = tronWalletPayService.queryOrder(appId, appSecret, tradeNo);
        return ResultDTO.requstSuccess(payQueryOrderDTO);
    }

    @ApiOperation(value = WalletPayApi.QueryRefund.METHOD_API_NAME, notes = WalletPayApi.QueryRefund.METHOD_API_NOTE)
    @PostMapping("/query/refund")
    public ResultDTO queryRefund(
            @ApiParam(WalletPayApi.QueryRefund.METHOD_API_APPID) @RequestParam("appId") String appId,
            @ApiParam(WalletPayApi.QueryRefund.METHOD_API_APPSECRET) @RequestParam("appSecret") String appSecret,
            @ApiParam(WalletPayApi.QueryRefund.METHOD_API_TRADENO) @RequestParam("tradeNo") String tradeNo
    ) {
        List<RefundOutDTO> list = tronWalletPayService.queryRefund(appId, appSecret, tradeNo);
        return ResultDTO.requstSuccess(list);
    }

    @ApiOperation(value = WalletPayApi.Recharge.METHOD_API_NAME, notes = WalletPayApi.Recharge.METHOD_API_NOTE)
    @PostMapping("/recharge")
    public ResultDTO recharge(
            @ApiParam(WalletPayApi.Recharge.METHOD_API_INPARAMS) RechargeInDTO inParams
    ) {
        RechargeOutDTO refundOutDTO = tronWalletPayService.insertRecharge(inParams);
        return ResultDTO.requstSuccess(refundOutDTO);
    }

    @ApiOperation(value = WalletPayApi.QueryRecharge.METHOD_API_NAME, notes = WalletPayApi.QueryRecharge.METHOD_API_NOTE)
    @PostMapping("/query/recharge")
    public ResultDTO queryRecharge(
            @ApiParam(WalletPayApi.QueryRecharge.METHOD_API_APPID) @RequestParam("appId") String appId,
            @ApiParam(WalletPayApi.QueryRecharge.METHOD_API_APPSECRET) @RequestParam("appSecret") String appSecret,
            @ApiParam(WalletPayApi.QueryRecharge.METHOD_API_TRADENO) @RequestParam("tradeNo") String tradeNo) {
        RechargeOutDTO refundOutDTO = tronWalletPayService.queryRecharge(appId, appSecret, tradeNo);
        return ResultDTO.requstSuccess(refundOutDTO);
    }
}
