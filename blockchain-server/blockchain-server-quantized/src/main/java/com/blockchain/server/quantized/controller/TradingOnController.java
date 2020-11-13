package com.blockchain.server.quantized.controller;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.server.quantized.common.constant.TradingOnConstant;
import com.blockchain.server.quantized.controller.api.TradingOnApi;
import com.blockchain.server.quantized.entity.TradingOn;
import com.blockchain.server.quantized.service.OrderService;
import com.blockchain.server.quantized.service.SubscriptionService;
import com.blockchain.server.quantized.service.TradingOnService;
import com.blockchain.server.quantized.service.impl.SubscriptionServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author: Liusd
 * @create: 2019-04-30 09:56
 **/
@RestController
@Api(TradingOnApi.CONTROLLER_API)
@RequestMapping("/tradingOn")
public class TradingOnController {


    private static final Logger LOG = LoggerFactory.getLogger(TradingOnController.class);

    @Autowired
    private SubscriptionService subscriptionService;
    @Autowired
    private TradingOnService tradingOnService;

    @ApiOperation(value = TradingOnApi.Add.METHOD_TITLE_NAME, notes = TradingOnApi.Add.METHOD_TITLE_NOTE)
    @PostMapping("/addSubscribe")
    public ResultDTO addSubscribe(@RequestParam String id) {
        LOG.info("rpc添加订阅请求成功，交易对id为:{}",id);
        TradingOn trading = tradingOnService.selectByKey(id);
        if (trading == null || trading.getState().equals(TradingOnConstant.STATE_SUBSCRIBE)){
            return ResultDTO.requstSuccess();
        }else {
            subscriptionService.subscribeTradingOn(trading);
        }
        return ResultDTO.requstSuccess();
    }
    @ApiOperation(value = TradingOnApi.Add.METHOD_TITLE_NAME, notes = TradingOnApi.Add.METHOD_TITLE_NOTE)
    @PostMapping("/unSubscribe")
    public ResultDTO unSubscribe(@RequestParam String id) {
        LOG.info("rpc取消订阅请求成功，交易对id为:{}",id);
        TradingOn trading = tradingOnService.selectByKey(id);
        //如果是取消中的状态，取消订阅
        if(trading != null && trading.getState().equals(TradingOnConstant.STATE_CANCELING)){
            subscriptionService.unSubscribe(trading);
        }
        return ResultDTO.requstSuccess();
    }
}
