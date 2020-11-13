package com.blockchain.server.cct.controller;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.common.base.util.SSOHelper;
import com.blockchain.server.base.controller.BaseController;
import com.blockchain.server.cct.common.enums.CctDataEnums;
import com.blockchain.server.cct.common.enums.CctEnums;
import com.blockchain.server.cct.common.exception.CctException;
import com.blockchain.server.cct.common.redistool.RedisTool;
import com.blockchain.server.cct.controller.api.PublishOrderApi;
import com.blockchain.server.cct.dto.order.PublishOrderParamDTO;
import com.blockchain.server.cct.entity.PublishOrder;
import com.blockchain.server.cct.feign.QuantizedFeign;
import com.blockchain.server.cct.service.PublishOrderService;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(PublishOrderApi.ORDER_API)
@RestController
@RequestMapping("/order")
public class PublishOrderController extends BaseController {

    @Autowired
    private PublishOrderService publishOrderService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private QuantizedFeign quantizedFeign;
    @Autowired
    private RedisTool redisTool;

    @ApiOperation(value = PublishOrderApi.handleLimitBuyOrder.METHOD_TITLE_NAME,
            notes = PublishOrderApi.handleLimitBuyOrder.METHOD_TITLE_NOTE)
    @PostMapping("/limitBuy")
    public ResultDTO handleLimitBuyOrder(@ApiParam(PublishOrderApi.handleLimitBuyOrder.METHOD_API_PARAM_DTO) PublishOrderParamDTO paramDTO,
                                         HttpServletRequest request) {
        String userId = SSOHelper.getUserId(redisTemplate, request);
        paramDTO.setUserId(userId);
        //返回发布订单的id
        String orderId = publishOrderService.handleLimitBuy(paramDTO);
        //通知订阅者进行撮合，通知前端刷新行情
        convertAndSend(orderId, paramDTO.getCoinName(), paramDTO.getUnitName(), paramDTO.getUnitPrice().toString());
        return ResultDTO.requstSuccess();
    }

    @ApiOperation(value = PublishOrderApi.handleLimitSellOrder.METHOD_TITLE_NAME,
            notes = PublishOrderApi.handleLimitSellOrder.METHOD_TITLE_NOTE)
    @PostMapping("/limitSell")
    public ResultDTO handleLimitSellOrder(@ApiParam(PublishOrderApi.handleLimitSellOrder.METHOD_API_PARAM_DTO) PublishOrderParamDTO paramDTO,
                                          HttpServletRequest request) {
        String userId = SSOHelper.getUserId(redisTemplate, request);
        paramDTO.setUserId(userId);
        //返回发布订单的id
        String orderId = publishOrderService.handleLimitSell(paramDTO);
        //通知订阅者进行撮合，通知前端刷新行情
        convertAndSend(orderId, paramDTO.getCoinName(), paramDTO.getUnitName(), paramDTO.getUnitPrice().toString());
        return ResultDTO.requstSuccess();
    }

    @ApiOperation(value = PublishOrderApi.handleMarketBuyOrder.METHOD_TITLE_NAME,
            notes = PublishOrderApi.handleMarketBuyOrder.METHOD_TITLE_NOTE)
    @PostMapping("/marketBuy")
    public ResultDTO handleMarketBuyOrder(@ApiParam(PublishOrderApi.handleMarketBuyOrder.METHOD_API_PARAM_DTO) PublishOrderParamDTO paramDTO,
                                          HttpServletRequest request) {
        String userId = SSOHelper.getUserId(redisTemplate, request);
        paramDTO.setUserId(userId);
        //返回发布订单的id
        String orderId = publishOrderService.handleMarketBuy(paramDTO);
        //通知订阅者进行撮合，通知前端刷新行情
        convertAndSend(orderId, paramDTO.getCoinName(), paramDTO.getUnitName(), paramDTO.getTurnover().toString());
        return ResultDTO.requstSuccess();
    }

    @ApiOperation(value = PublishOrderApi.handleMarketSellOrder.METHOD_TITLE_NAME,
            notes = PublishOrderApi.handleMarketSellOrder.METHOD_TITLE_NOTE)
    @PostMapping("/marketSell")
    public ResultDTO handleMarketSellOrder(@ApiParam(PublishOrderApi.handleMarketSellOrder.METHOD_API_PARAM_DTO) PublishOrderParamDTO paramDTO,
                                           HttpServletRequest request) {
        String userId = SSOHelper.getUserId(redisTemplate, request);
        paramDTO.setUserId(userId);
        //返回发布订单的id
        String orderId = publishOrderService.handleMarketSell(paramDTO);
        //通知订阅者进行撮合，通知前端刷新行情
        convertAndSend(orderId, paramDTO.getCoinName(), paramDTO.getUnitName(), paramDTO.getTotalNum().toString());
        return ResultDTO.requstSuccess();
    }

    /**
     * 撤销委托单
     * @param orderId
     * @param request
     * @return
     */
    @ApiOperation(value = PublishOrderApi.cancel.METHOD_TITLE_NAME,
            notes = PublishOrderApi.cancel.METHOD_TITLE_NOTE)
    @PostMapping("/cancel")
    public ResultDTO handleCancelOrder(@ApiParam(PublishOrderApi.cancel.METHOD_API_ORDER_ID) String orderId,
                                       HttpServletRequest request) {
        String userId = SSOHelper.getUserId(redisTemplate, request);
        PublishOrder order = publishOrderService.selectById(orderId);
       //fk USDT
        //查询如果是量化交易，调用量化接口，由火币异步通知inner的撤单接口进行撤单
        if (publishOrderService.getTradingOn(order.getCoinName(), order.getUnitName())) {
            ResultDTO<String> result = quantizedFeign.cancellations(orderId);
            if (result.getData().equals("OVER")) {
                //如果是OVER,说明量化没有此订单信息，可以直接撤销完成
                //撤销订单
                publishOrderService.handleCancelOrder(orderId, userId);
                return ResultDTO.requstSuccess();
            } else if (result.getData().equals("SUCCESS")) {
                //如果是OVER,说明量化有此订单信息并且发起成功一个撤单请求，将订单改为撤单中状态，实际状态由火币推送处理
                publishOrderService.handleOrderToCanceling(orderId);
                return ResultDTO.requstSuccess();
            } else if (result.getData().equals("FAIL")) {
                throw new CctException(CctEnums.ORDER_CANCEL_ERROR);
            } else if (result.getData().equals("INVALID")){
               //不作处理，走下一步撤单
            }
        }
        //如果不是走量化交易，或者订单状态无效，按照正常流程走
        //撤销订单
        publishOrderService.handleCancelOrder(orderId, userId);
        //用户撤销订单，发送广播至前端刷新行情
        redisTool.send(order.getCoinName(), order.getUnitName());
        return ResultDTO.requstSuccess();
    }

    @ApiOperation(value = PublishOrderApi.listUserOrder.METHOD_TITLE_NAME,
            notes = PublishOrderApi.listUserOrder.METHOD_TITLE_NOTE)
    @GetMapping("/listUserOrder")
    public ResultDTO listUserOrder(
            @ApiParam(PublishOrderApi.listUserOrder.METHOD_API_COINNAME) @RequestParam(value = "coinName", required = false) String coinName,
            @ApiParam(PublishOrderApi.listUserOrder.METHOD_API_UNITNAME) @RequestParam(value = "unitName", required = false) String unitName,
            @ApiParam(PublishOrderApi.listUserOrder.METHOD_API_TYPE) @RequestParam(value = "type", required = false) String type,
            @ApiParam(PublishOrderApi.listUserOrder.METHOD_API_PAGENUM) @RequestParam(value = "pageNum", required = false, defaultValue = "0") Integer pageNum,
            @ApiParam(PublishOrderApi.listUserOrder.METHOD_API_PAGESIZE) @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
            HttpServletRequest request) {
        String userId = SSOHelper.getUserId(redisTemplate, request);
        String[] status = {CctDataEnums.ORDER_STATUS_NEW.getStrVlue(), CctDataEnums.ORDER_STATUS_MATCH.getStrVlue(), CctDataEnums.ORDER_STATUS_CANCELING.getStrVlue()};
        PageHelper.startPage(pageNum, pageSize);
        List<PublishOrder> publishOrders = publishOrderService.listUserOrder(
                userId, coinName, unitName, type, "", status);
        return ResultDTO.requstSuccess(publishOrders);
    }

    @ApiOperation(value = PublishOrderApi.listBuyOrder.METHOD_TITLE_NAME,
            notes = PublishOrderApi.listBuyOrder.METHOD_TITLE_NOTE)
    @GetMapping("/listBuyOrder")
    public ResultDTO listBuyOrder(
            @ApiParam(PublishOrderApi.listBuyOrder.METHOD_API_COINNAME) @RequestParam("coinName") String coinName,
            @ApiParam(PublishOrderApi.listBuyOrder.METHOD_API_UNITNAME) @RequestParam("unitName") String unitName,
            @ApiParam(PublishOrderApi.listBuyOrder.METHOD_API_PAGENUM) @RequestParam(value = "pageNum", required = false, defaultValue = "0") Integer pageNum,
            @ApiParam(PublishOrderApi.listBuyOrder.METHOD_API_PAGESIZE) @RequestParam(value = "pageSize", required = false, defaultValue = "5") Integer pageSize) {
        //查询买单盘口
        List publishOrders = redisTool.listBuyOrder(coinName, unitName);
        return ResultDTO.requstSuccess(publishOrders);
    }

    @ApiOperation(value = PublishOrderApi.listSellOrder.METHOD_TITLE_NAME,
            notes = PublishOrderApi.listSellOrder.METHOD_TITLE_NOTE)
    @GetMapping("/listSellOrder")
    public ResultDTO listSellOrder(
            @ApiParam(PublishOrderApi.listSellOrder.METHOD_API_COINNAME) @RequestParam("coinName") String coinName,
            @ApiParam(PublishOrderApi.listSellOrder.METHOD_API_UNITNAME) @RequestParam("unitName") String unitName,
            @ApiParam(PublishOrderApi.listSellOrder.METHOD_API_PAGENUM) @RequestParam(value = "pageNum", required = false, defaultValue = "0") Integer pageNum,
            @ApiParam(PublishOrderApi.listSellOrder.METHOD_API_PAGESIZE) @RequestParam(value = "pageSize", required = false, defaultValue = "5") Integer pageSize) {
        //查询卖单盘口
        List publishOrders = redisTool.listSellOrder(coinName, unitName);
        return ResultDTO.requstSuccess(publishOrders);
    }

    @ApiOperation(value = PublishOrderApi.listOrderByCoinAndUnit.METHOD_TITLE_NAME,
            notes = PublishOrderApi.listOrderByCoinAndUnit.METHOD_TITLE_NOTE)
    @GetMapping("/listOrderByCoinAndUnit")
    public ResultDTO listOrderByCoinAndUnit(@ApiParam(PublishOrderApi.listOrderByCoinAndUnit.METHOD_API_COINNAME) @RequestParam("coinName") String coinName,
                                            @ApiParam(PublishOrderApi.listOrderByCoinAndUnit.METHOD_API_UNITNAME) @RequestParam("unitName") String unitName) {
        //盘口买单
        List buyOrders = redisTool.listBuyOrder(coinName, unitName);
        //盘口卖单
        List sellOrders = redisTool.listSellOrder(coinName, unitName);
        //封装返回前端参数
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("BUY_ORDERS", buyOrders);
        resultMap.put("SELL_ORDERS", sellOrders);
        return ResultDTO.requstSuccess(resultMap);
    }

    @ApiOperation(value = PublishOrderApi.pcListUserOrder.METHOD_TITLE_NAME,
            notes = PublishOrderApi.pcListUserOrder.METHOD_TITLE_NOTE)
    @GetMapping("/pcListUserOrder")
    public ResultDTO pcListUserOrder(
            @ApiParam(PublishOrderApi.pcListUserOrder.METHOD_API_COINNAME) @RequestParam(value = "coinName", required = false) String coinName,
            @ApiParam(PublishOrderApi.pcListUserOrder.METHOD_API_UNITNAME) @RequestParam(value = "unitName", required = false) String unitName,
            @ApiParam(PublishOrderApi.pcListUserOrder.METHOD_API_TYPE) @RequestParam(value = "type", required = false) String type,
            @ApiParam(PublishOrderApi.pcListUserOrder.METHOD_API_PAGENUM) @RequestParam(value = "pageNum", required = false, defaultValue = "0") Integer pageNum,
            @ApiParam(PublishOrderApi.pcListUserOrder.METHOD_API_PAGESIZE) @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
            HttpServletRequest request) {
        String userId = SSOHelper.getUserId(redisTemplate, request);
        String[] status = {CctDataEnums.ORDER_STATUS_NEW.getStrVlue(), CctDataEnums.ORDER_STATUS_MATCH.getStrVlue(), CctDataEnums.ORDER_STATUS_CANCELING.getStrVlue()};
        PageHelper.startPage(pageNum, pageSize);
        List<PublishOrder> publishOrders = publishOrderService.listUserOrder(
                userId, coinName, unitName, type, "", status);
        return generatePage(publishOrders);
    }


    /***
     * 订单生成后
     * 通知订阅者程序进行撮合
     * 通知前端订阅者刷新页面
     *
     * @param orderId 订单id
     * @param coinName 基本货币
     * @param unitName 二级货币
     * @param publishNum 发布参数（单价或交易额或数量）
     */
    private void convertAndSend(String orderId, String coinName, String unitName, String publishNum) {
        // 如果走的是量化交易，不走本地撮合
        if (publishOrderService.getTradingOn(coinName, unitName)) return;
        //key字符串
        String trandingOn = coinName + "-" + unitName;
        String key = publishNum + ":" + trandingOn;
        //将撮合订单id和单价放进集合中
        //cct:list:order:num:1.00:BTC-USDT
        String listKey = CctDataEnums.REDIS_LIST_KEY.getStrVlue() + key;
        stringRedisTemplate.opsForList().leftPush(listKey, orderId);
        //通过StringRedisTemplate对象向redis消息队列频道发布消息，进行撮合订单
        stringRedisTemplate.convertAndSend(CctDataEnums.PUBLISH_ORDER.getStrVlue(), key);

        //用户发布订单，发送广播至前端刷新行情
        redisTool.send(coinName, unitName);
    }
}

