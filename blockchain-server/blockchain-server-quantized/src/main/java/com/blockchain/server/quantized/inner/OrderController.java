package com.blockchain.server.quantized.inner;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.server.quantized.common.constant.DepthConstant;
import com.blockchain.server.quantized.inner.api.OrderApi;
import com.blockchain.server.quantized.service.OrderService;
import com.blockchain.server.quantized.service.TradingOnService;
import com.huobi.client.model.enums.AccountType;
import com.huobi.client.model.enums.OrderType;
import com.huobi.client.model.request.NewOrderRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * @author Harvey
 * @date 2019/3/9 11:57
 * @user WIN10
 */
@RestController
@Api(OrderApi.ORDER_API)
@RequestMapping("/inner/order")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private TradingOnService tradingOnService;

    /**
     * @Description: buy-market：市价买
     * @Param: [coinName, unitName, publishType, orderType, amount, price]
     * @return: com.blockchain.common.base.dto.ResultDTO
     * @Author: Liu.sd
     * @Date: 2019/4/18
     */
    @ApiOperation(value = OrderApi.Create.METHOD_TITLE_NAME, notes = OrderApi.Create.METHOD_TITLE_NOTE)
    @PostMapping("/createBuyMarket")
    public ResultDTO createBuyMarket(@ApiParam(OrderApi.Create.SYMBOL) @RequestParam("symbol") String symbol, @ApiParam(OrderApi.Create.AMOUNT) @RequestParam("amount") BigDecimal amount, @ApiParam(OrderApi.Create.USERID) @RequestParam("userId") String userId, @ApiParam(OrderApi.Create.USERID) @RequestParam("cctId") String cctId) {
        NewOrderRequest newOrderRequest = new NewOrderRequest(symbol,
                AccountType.SPOT,
                OrderType.BUY_MARKET,
                amount,
                null);
        return ResultDTO.requstSuccess(orderService.create(newOrderRequest, userId, cctId));
    }

    /**
     * @Description: buy-limit：限价买
     * @Param: [coinName, unitName, publishType, orderType, amount, price]
     * @return: com.blockchain.common.base.dto.ResultDTO
     * @Author: Liu.sd
     * @Date: 2019/4/18
     */
    @ApiOperation(value = OrderApi.Create.METHOD_TITLE_NAME, notes = OrderApi.Create.METHOD_TITLE_NOTE)
    @PostMapping("/createBuyLimit")
    public ResultDTO createBuyLimit(@ApiParam(OrderApi.Create.SYMBOL) @RequestParam("symbol") String symbol, @ApiParam(OrderApi.Create.AMOUNT) @RequestParam("amount") BigDecimal amount, @ApiParam(OrderApi.Create.PRICE) @RequestParam("price") BigDecimal price, @ApiParam(OrderApi.Create.USERID) @RequestParam("userId") String userId, @ApiParam(OrderApi.Create.USERID) @RequestParam("cctId") String cctId) {
        NewOrderRequest newOrderRequest = new NewOrderRequest(symbol,
                AccountType.SPOT,
                OrderType.BUY_LIMIT,
                amount,
                price);
        return ResultDTO.requstSuccess(orderService.create(newOrderRequest, userId, cctId));
    }

    /**
     * @Description: ell-market：市价卖
     * @Param: [coinName, unitName, publishType, orderType, amount, price]
     * @return: com.blockchain.common.base.dto.ResultDTO
     * @Author: Liu.sd
     * @Date: 2019/4/18
     */
    @ApiOperation(value = OrderApi.Create.METHOD_TITLE_NAME, notes = OrderApi.Create.METHOD_TITLE_NOTE)
    @PostMapping("/createSellMarket")
    public ResultDTO createSellMarket(@ApiParam(OrderApi.Create.SYMBOL) @RequestParam("symbol") String symbol, @ApiParam(OrderApi.Create.AMOUNT) @RequestParam("amount") BigDecimal amount, @ApiParam(OrderApi.Create.USERID) @RequestParam("userId") String userId, @ApiParam(OrderApi.Create.USERID) @RequestParam("cctId") String cctId) {
        NewOrderRequest newOrderRequest = new NewOrderRequest(symbol,
                AccountType.SPOT,
                OrderType.SELL_MARKET,
                amount,
                null);
        return ResultDTO.requstSuccess(orderService.create(newOrderRequest, userId, cctId));
    }

    /**
     * @Description: sell-limit：限价卖
     * @Param: [coinName, unitName, publishType, orderType, amount, price]
     * @return: com.blockchain.common.base.dto.ResultDTO
     * @Author: Liu.sd
     * @Date: 2019/4/18
     */
    @ApiOperation(value = OrderApi.Create.METHOD_TITLE_NAME, notes = OrderApi.Create.METHOD_TITLE_NOTE)
    @PostMapping("/createSellLimit")
    public ResultDTO createSellLimit(@ApiParam(OrderApi.Create.SYMBOL) @RequestParam("symbol") String symbol, @ApiParam(OrderApi.Create.AMOUNT) @RequestParam("amount") BigDecimal amount, @ApiParam(OrderApi.Create.PRICE) @RequestParam("price") BigDecimal price, @ApiParam(OrderApi.Create.USERID) @RequestParam("userId") String userId, @ApiParam(OrderApi.Create.USERID) @RequestParam("cctId") String cctId) {
        NewOrderRequest newOrderRequest = new NewOrderRequest(symbol,
                AccountType.SPOT,
                OrderType.SELL_LIMIT,
                amount,
                price);
        return ResultDTO.requstSuccess(orderService.create(newOrderRequest, userId, cctId));
    }

    /**
     * @Description: 撤单
     * @Param: [symbol, orderId]
     * @return: com.blockchain.common.base.dto.ResultDTO
     * @Author: Liu.sd
     * @Date: 2019/4/18
     */
    @ApiOperation(value = OrderApi.Cancel.METHOD_TITLE_NAME, notes = OrderApi.Cancel.METHOD_TITLE_NOTE)
    @PostMapping("/cancellations")
    public ResultDTO cancellations(@ApiParam(OrderApi.Cancel.ORDERID) @RequestParam("orderId") String orderId) {
        return ResultDTO.requstSuccess(orderService.cancel(orderId));
    }

    /**
     * @Description: 盘口买
     * @Param: [symbol, size]
     * @return: com.blockchain.common.base.dto.ResultDTO
     * @Author: Liu.sd
     * @Date: 2019/4/26
     */
    @ApiOperation(value = OrderApi.Cancel.METHOD_TITLE_NAME, notes = OrderApi.Cancel.METHOD_TITLE_NOTE)
    @PostMapping("/getBuyDepth")
    public ResultDTO getPriceDepth(@ApiParam(OrderApi.Cancel.SYMBOL) @RequestParam("symbol") String symbol, @ApiParam(OrderApi.Cancel.ORDERID) @RequestParam("size") Integer size) {
        return ResultDTO.requstSuccess(orderService.getPriceDepth(symbol, size, DepthConstant.BUY));
    }

    /**
     * @Description: 盘口卖
     * @Param: [symbol, size]
     * @return: com.blockchain.common.base.dto.ResultDTO
     * @Author: Liu.sd
     * @Date: 2019/4/26
     */
    @ApiOperation(value = OrderApi.Cancel.METHOD_TITLE_NAME, notes = OrderApi.Cancel.METHOD_TITLE_NOTE)
    @PostMapping("/getSellDepth")
    public ResultDTO getSellDepth(@ApiParam(OrderApi.Cancel.SYMBOL) @RequestParam("symbol") String symbol, @ApiParam(OrderApi.Cancel.ORDERID) @RequestParam("size") Integer size) {
        return ResultDTO.requstSuccess(orderService.getPriceDepth(symbol, size, DepthConstant.SELL));
    }


    /**
     * @Description: 查询交易对
     * @Param: [coinName, unitName]
     * @return: com.blockchain.common.base.dto.ResultDTO
     * @Author: Liu.sd
     * @Date: 2019/4/25
     */
    @ApiOperation(value = OrderApi.Cancel.METHOD_TITLE_NAME, notes = OrderApi.Cancel.METHOD_TITLE_NOTE)
    @PostMapping("/getTradingOnByCoinNameAndUnitName")
    public ResultDTO getTradingOn(@ApiParam(OrderApi.Cancel.SYMBOL) @RequestParam("coinName") String coinName, @ApiParam(OrderApi.Cancel.ORDERID) @RequestParam("unitName") String unitName) {
        return ResultDTO.requstSuccess(tradingOnService.selectOne(coinName, unitName));
    }

    @ApiOperation(value = OrderApi.Cancel.METHOD_TITLE_NAME, notes = OrderApi.Cancel.METHOD_TITLE_NOTE)
    @PostMapping("/getHistoricalTrade")
    public ResultDTO getHistoricalTrade(@ApiParam(OrderApi.Cancel.SYMBOL) @RequestParam("coinName") String coinName, @ApiParam(OrderApi.Cancel.ORDERID) @RequestParam("unitName") String unitName, @ApiParam(OrderApi.Cancel.ORDERID) @RequestParam("size") Integer size) {
        return ResultDTO.requstSuccess(orderService.getHistoricalTrade(coinName, unitName, size));
    }

    @ApiOperation(value = OrderApi.getMarketDTOHistoricalTrade.METHOD_TITLE_NAME,
            notes = OrderApi.getMarketDTOHistoricalTrade.METHOD_TITLE_NOTE)
    @PostMapping("/getMarketDTOHistoricalTrade")
    public ResultDTO getMarketDTOHistoricalTrade(@ApiParam(OrderApi.getMarketDTOHistoricalTrade.METHOD_API_COINNAME) @RequestParam("coinName") String coinName,
                                                 @ApiParam(OrderApi.getMarketDTOHistoricalTrade.METHOD_API_UNITNAME) @RequestParam("unitName") String unitName,
                                                 @ApiParam(OrderApi.getMarketDTOHistoricalTrade.METHOD_API_SIZE) @RequestParam("size") Integer size) {
        return ResultDTO.requstSuccess(orderService.getMarketDTOHistoricalTrade(coinName, unitName, size));
    }

    /****
     * lzf 2019.06.25
     */

    @ApiOperation(value = OrderApi.CheckTradingOnIsOpen.METHOD_TITLE_NAME,
            notes = OrderApi.CheckTradingOnIsOpen.METHOD_TITLE_NOTE)
    @PostMapping("/checkTradingOnIsOpen")
    public ResultDTO checkTradingOnIsOpen(@ApiParam(OrderApi.CheckTradingOnIsOpen.COINNAME) @RequestParam("coinName") String coinName,
                                          @ApiParam(OrderApi.CheckTradingOnIsOpen.UNITNAME) @RequestParam("unitName") String unitName) {
        return ResultDTO.requstSuccess(tradingOnService.checkTradingOnIsOpen(coinName, unitName));
    }
}
