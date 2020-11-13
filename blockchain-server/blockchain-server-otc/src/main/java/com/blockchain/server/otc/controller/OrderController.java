package com.blockchain.server.otc.controller;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.common.base.util.SSOHelper;
import com.blockchain.server.base.controller.BaseController;
import com.blockchain.server.otc.controller.api.OrderApi;
import com.blockchain.server.otc.dto.order.OrderDTO;
import com.blockchain.server.otc.service.OrderService;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;

@Api(OrderApi.ORDER_API)
@RestController
@RequestMapping("/order")
public class OrderController extends BaseController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private RedisTemplate redisTemplate;

    @ApiOperation(value = OrderApi.buyOrder.METHOD_TITLE_NAME,
            notes = OrderApi.buyOrder.METHOD_TITLE_NOTE)
    @PostMapping("/buyOrder")
    public ResultDTO buyOrder(@ApiParam(OrderApi.buyOrder.METHOD_TITLE_AD_ID) @RequestParam("adId") String adId,
                              @ApiParam(OrderApi.buyOrder.METHOD_TITLE_AMOUNT) @RequestParam("amount") BigDecimal amount,
                              @ApiParam(OrderApi.buyOrder.METHOD_TITLE_PRICE) @RequestParam("price") BigDecimal price,
                              @ApiParam(OrderApi.buyOrder.METHOD_TITLE_TURNOVER) @RequestParam("turnover") BigDecimal turnover,
                              HttpServletRequest request) {
        String userId = SSOHelper.getUserId(redisTemplate, request);
        String orderId = orderService.buyOrder(userId, adId, amount, price, turnover);
        return ResultDTO.requstSuccess(orderId);
    }

    @ApiOperation(value = OrderApi.sellOrder.METHOD_TITLE_NAME,
            notes = OrderApi.sellOrder.METHOD_TITLE_NOTE)
    @PostMapping("/sellOrder")
    public ResultDTO sellOrder(@ApiParam(OrderApi.sellOrder.METHOD_TITLE_AD_ID) @RequestParam("adId") String adId,
                               @ApiParam(OrderApi.sellOrder.METHOD_TITLE_AMOUNT) @RequestParam("amount") BigDecimal amount,
                               @ApiParam(OrderApi.sellOrder.METHOD_TITLE_PRICE) @RequestParam("price") BigDecimal price,
                               @ApiParam(OrderApi.sellOrder.METHOD_TITLE_TURNOVER) @RequestParam("turnover") BigDecimal turnover,
                               @ApiParam(OrderApi.sellOrder.METHOD_TITLE_PASS) @RequestParam("pass") String pass,
                               HttpServletRequest request) {
        String userId = SSOHelper.getUserId(redisTemplate, request);
        String orderId = orderService.sellOrder(userId, adId, amount, price, turnover, pass);
        return ResultDTO.requstSuccess(orderId);
    }

    @ApiOperation(value = OrderApi.cancelBuyOrder.METHOD_TITLE_NAME,
            notes = OrderApi.cancelBuyOrder.METHOD_TITLE_NOTE)
    @PostMapping("/cancelBuyOrder")
    public ResultDTO cancelBuyOrder(@ApiParam(OrderApi.cancelBuyOrder.METHOD_TITLE_ORDER_ID) @RequestParam("orderId") String orderId,
                                    HttpServletRequest request) {
        String userId = SSOHelper.getUserId(redisTemplate, request);
        orderService.cancelBuyOrder(userId, orderId);
        return ResultDTO.requstSuccess();
    }

    @ApiOperation(value = OrderApi.pay.METHOD_TITLE_NAME,
            notes = OrderApi.pay.METHOD_TITLE_NOTE)
    @PostMapping("/pay")
    public ResultDTO pay(@ApiParam(OrderApi.pay.METHOD_TITLE_ORDER_ID) @RequestParam("orderId") String orderId,
                         @ApiParam(OrderApi.pay.METHOD_TITLE_PAY_TYPE) @RequestParam("payType") String payType,
                         HttpServletRequest request) {
        String userId = SSOHelper.getUserId(redisTemplate, request);
        orderService.pay(userId, orderId, payType);
        return ResultDTO.requstSuccess();
    }

    @ApiOperation(value = OrderApi.receipt.METHOD_TITLE_NAME,
            notes = OrderApi.receipt.METHOD_TITLE_NOTE)
    @PostMapping("/receipt")
    public ResultDTO receipt(@ApiParam(OrderApi.receipt.METHOD_TITLE_ORDER_ID) @RequestParam("orderId") String orderId,
                             @ApiParam(OrderApi.receipt.METHOD_TITLE_PASS) @RequestParam("pass") String pass,
                             HttpServletRequest request) {
        String userId = SSOHelper.getUserId(redisTemplate, request);
        orderService.receipt(userId, orderId, pass);
        return ResultDTO.requstSuccess();
    }

    @ApiOperation(value = OrderApi.listUserOrder.METHOD_TITLE_NAME,
            notes = OrderApi.listUserOrder.METHOD_TITLE_NOTE)
    @GetMapping("/listUserOrder")
    public ResultDTO listUserOrder(@ApiParam(OrderApi.listUserOrder.METHOD_TITLE_COIN_NAME) @RequestParam(value = "coinName", required = false) String coinName,
                                   @ApiParam(OrderApi.listUserOrder.METHOD_TITLE_UNIT_NAME) @RequestParam(value = "unitName", required = false) String unitName,
                                   @ApiParam(OrderApi.listUserOrder.METHOD_TITLE_ORDER_TYPE) @RequestParam(value = "orderType", required = false) String orderType,
                                   @ApiParam(OrderApi.listUserOrder.METHOD_TITLE_ORDER_STATUS) @RequestParam(value = "orderStatus", required = false) String orderStatus,
                                   @ApiParam(OrderApi.listUserOrder.METHOD_TITLE_PAY_TYPE) @RequestParam(value = "payType", required = false) String payType,
                                   @ApiParam(OrderApi.listUserOrder.METHOD_TITLE_PAGE_NUM) @RequestParam(value = "pageNum", defaultValue = "1", required = false) Integer pageNum,
                                   @ApiParam(OrderApi.listUserOrder.METHOD_TITLE_PAGE_SIZE) @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
                                   HttpServletRequest request) {
        String userId = SSOHelper.getUserId(redisTemplate, request);
        PageHelper.startPage(pageNum, pageSize);
        List<OrderDTO> userOrderlist = orderService.listUserOrder(userId, coinName, unitName, orderType, orderStatus, payType);
        return ResultDTO.requstSuccess(userOrderlist);
    }

    @ApiOperation(value = OrderApi.selectByUserIdAndId.METHOD_TITLE_NAME,
            notes = OrderApi.selectByUserIdAndId.METHOD_TITLE_NOTE)
    @GetMapping("/selectByUserIdAndId")
    public ResultDTO selectByUserIdAndId(@ApiParam(OrderApi.selectByUserIdAndId.METHOD_TITLE_ORDER_ID) @RequestParam("orderId") String orderId,
                                         HttpServletRequest request) {
        String userId = SSOHelper.getUserId(redisTemplate, request);
        OrderDTO order = orderService.selectByUserIdAndOrderId(userId, orderId);
        return ResultDTO.requstSuccess(order);
    }

    /****************************pc分页接口****************************/

    @ApiOperation(value = OrderApi.pcListUserOrder.METHOD_TITLE_NAME,
            notes = OrderApi.pcListUserOrder.METHOD_TITLE_NOTE)
    @GetMapping("/pcListUserOrder")
    public ResultDTO pcListUserOrder(@ApiParam(OrderApi.pcListUserOrder.METHOD_TITLE_COIN_NAME) @RequestParam(value = "coinName", required = false) String coinName,
                                     @ApiParam(OrderApi.pcListUserOrder.METHOD_TITLE_UNIT_NAME) @RequestParam(value = "unitName", required = false) String unitName,
                                     @ApiParam(OrderApi.pcListUserOrder.METHOD_TITLE_ORDER_TYPE) @RequestParam(value = "orderType", required = false) String orderType,
                                     @ApiParam(OrderApi.pcListUserOrder.METHOD_TITLE_ORDER_STATUS) @RequestParam(value = "orderStatus", required = false) String orderStatus,
                                     @ApiParam(OrderApi.pcListUserOrder.METHOD_TITLE_PAY_TYPE) @RequestParam(value = "payType", required = false) String payType,
                                     @ApiParam(OrderApi.pcListUserOrder.METHOD_TITLE_PAGE_NUM) @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                     @ApiParam(OrderApi.pcListUserOrder.METHOD_TITLE_PAGE_SIZE) @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                     HttpServletRequest request) {
        String userId = SSOHelper.getUserId(redisTemplate, request);
        PageHelper.startPage(pageNum, pageSize);
        List<OrderDTO> userOrderlist = orderService.listUserOrder(userId, coinName, unitName, orderType, orderStatus, payType);
        return generatePage(userOrderlist);
    }
}
