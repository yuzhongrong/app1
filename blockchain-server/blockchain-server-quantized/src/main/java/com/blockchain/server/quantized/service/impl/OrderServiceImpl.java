package com.blockchain.server.quantized.service.impl;

import com.alibaba.fastjson.JSON;
import com.blockchain.common.base.dto.MarketDTO;
import com.blockchain.common.base.dto.TradingRecordDTO;
import com.blockchain.server.quantized.common.constant.DepthConstant;
import com.blockchain.server.quantized.common.constant.OrderCancelConstant;
import com.blockchain.server.quantized.common.constant.QuantizedOrderInfoConstant;
import com.blockchain.server.quantized.common.enums.QuantizedResultEnums;
import com.blockchain.server.quantized.common.exception.QuantizedException;
import com.blockchain.server.quantized.entity.OrderErr;
import com.blockchain.server.quantized.entity.QuantizedAccount;
import com.blockchain.server.quantized.entity.QuantizedOrder;
import com.blockchain.server.quantized.entity.QuantizedOrderInfo;
import com.blockchain.server.quantized.service.*;
import com.codingapi.tx.annotation.ITxTransaction;
import com.huobi.client.SyncRequestClient;
import com.huobi.client.exception.HuobiApiException;
import com.huobi.client.model.*;
import com.huobi.client.model.enums.OrderState;
import com.huobi.client.model.request.HistoricalOrdersRequest;
import com.huobi.client.model.request.NewOrderRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


/**
 * @author: Liusd
 * @create: 2019-04-18 19:34
 **/
@Service
public class OrderServiceImpl implements OrderService, ITxTransaction {

    private static final Logger LOG = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private TradingOnService tradingOnService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private OrderErrService orderErrService;

    @Autowired
    private QuantizedOrderService quantizedOrderService;

    @Autowired
    private QuantizedOrderInfoService quantizedOrderInfoService;

    @Override
    @Transactional(noRollbackFor = QuantizedException.class)
    public Order create(NewOrderRequest newOrderRequest, String userId, String cctId) {
        try {
            //下单
            //账号信息
            SyncRequestClient syncRequestClient = SyncRequestClient.create(getAccount().getApiKey(), getAccount().getSecretKey());
            LOG.info("******create-syncRequestClient******"+syncRequestClient);
            LOG.info("******create-syncRequestClient******"+newOrderRequest.toString());
            Long orderId = syncRequestClient.createOrder(newOrderRequest);
            //获取订单详情
//            Order order = details(newOrderRequest.getSymbol(), orderId);
            //保存在本地
            QuantizedOrder quantizedOrder = new QuantizedOrder();
            quantizedOrder.setId(orderId);
            quantizedOrder.setAmount(newOrderRequest.getAmount().toString());
            quantizedOrder.setCreatedAt(System.currentTimeMillis());
            quantizedOrder.setPrice(newOrderRequest.getPrice() + "");
            quantizedOrder.setState(OrderState.SUBMITTED.toString());
            quantizedOrder.setSymbol(newOrderRequest.getSymbol());
            quantizedOrder.setType(newOrderRequest.getType().toString());
            quantizedOrder.setUserId(userId);
            quantizedOrder.setCctId(cctId);
            //保存到本地
            quantizedOrderService.insert(quantizedOrder);
            return null;
        } catch (Exception e) {
            //出现异常保存异常订单
            OrderErr orderErr = new OrderErr();
            orderErr.setId(UUID.randomUUID().toString());
            orderErr.setSymbol(newOrderRequest.getSymbol());
            orderErr.setOrderType(newOrderRequest.getType().toString());
            orderErr.setAmount(newOrderRequest.getAmount());
            orderErr.setPrice(newOrderRequest.getPrice());
            orderErr.setMsg(e.getMessage());
            orderErr.setCreateTime(new Date());
            orderErr.setUserId(userId);
            orderErr.setCctId(cctId);
            orderErrService.insert(orderErr);
            LOG.info(new QuantizedException(QuantizedResultEnums.CREATE_ORDER_ERROR).getMsg() + ",详细参数查看 pc_quantized_order_err 表 id：{},异常原因为 ：{}", orderErr.getId(), e.getMessage());

            LOG.error("保存订单发生异常",e);
            e.printStackTrace();
            throw new QuantizedException(QuantizedResultEnums.CREATE_ORDER_ERROR);
        }
    }

    @Override
    public String cancellations(String symbol, Long orderId) {
        //账号信息
        SyncRequestClient syncRequestClient = SyncRequestClient.create(getAccount().getApiKey(), getAccount().getSecretKey());
        try {
            Boolean b= this.getOrder(symbol, orderId);
            if(!b){
                return OrderCancelConstant.INVALID;
            }
            syncRequestClient.cancelOrder(symbol, orderId);
            return OrderCancelConstant.SUCCESS;
        } catch (Exception e) {
            LOG.info(new QuantizedException(QuantizedResultEnums.CANCEL_ORDER_ERROR).getMsg() + ",参数为 ：symbol :{},orderId : {} ,异常原因为 ：{}", symbol, orderId, e.getMessage());
            return OrderCancelConstant.FAIL;
        }
    }
    public boolean getOrder(String symbol, long orderId) {
        SyncRequestClient syncRequestClient = SyncRequestClient.create(getAccount().getApiKey(), getAccount().getSecretKey());
        boolean b=true;
        try {
            Order order= syncRequestClient.getOrder(symbol, orderId);
            handleBySubscribe(symbol,orderId);
            LOG.info("order is:"+ JSON.toJSONString(order));
        }catch (HuobiApiException e){
            String mess=e.getMessage();
            LOG.info("mess is:"+mess);
            if(mess.contentEquals("base-record-invalid: record invalid")){
                LOG.info("订单无效，直接取消订单");
                b=false;
            }
        }
        return b;
    }
    @Override
    public List<MarketDTO> getPriceDepth(String symbol, int size, String type) {
        List<MarketDTO> marketDTOList = new ArrayList<>();
        PriceDepth priceDepth = SyncRequestClient.create().getPriceDepth(symbol, size);
        //买
        if (type.equals(DepthConstant.BUY)) {
            priceDepth.getBids().forEach(depth -> {
                MarketDTO marketDTO = new MarketDTO();
                marketDTO.setUnitPrice(depth.getPrice());
                marketDTO.setTotalNum(depth.getAmount());
                marketDTO.setTotalLastNum(depth.getAmount());
                marketDTOList.add(marketDTO);
            });
            //卖
        } else {
            priceDepth.getAsks().forEach(depth -> {
                MarketDTO marketDTO = new MarketDTO();
                marketDTO.setUnitPrice(depth.getPrice());
                marketDTO.setTotalNum(depth.getAmount());
                marketDTO.setTotalLastNum(depth.getAmount());
                marketDTOList.add(marketDTO);
            });
        }
        return marketDTOList;
    }

    @Override
    public String cancel(String orderId) {
        QuantizedOrder quantizedOrder = quantizedOrderService.selectByCctId(orderId);
        if (quantizedOrder == null) {
            return OrderCancelConstant.OVER;
        }
        return cancellations(quantizedOrder.getSymbol(), quantizedOrder.getId());
    }

    @Override
    public void handleBySubscribe(String symbol, long orderId) {
        QuantizedOrder quantizedOrder = quantizedOrderService.selectByPrimaryKey(orderId);
        //保存火币推送订单记录
        QuantizedOrderInfo quantizedOrderInfo = new QuantizedOrderInfo();
        quantizedOrderInfo.setId(UUID.randomUUID().toString());
        quantizedOrderInfo.setOrderId(orderId);
        quantizedOrderInfo.setSymbol(symbol);
        quantizedOrderInfo.setTimes(QuantizedOrderInfoConstant.DEFAULT_TIMES);
        quantizedOrderInfo.setDie(QuantizedOrderInfoConstant.DEFAULT_DIE);
        quantizedOrderInfo.setCreateTime(new Date());
        quantizedOrderInfo.setModifyTime(new Date());
        /**
         * 火币下单完成后，为了防止本地来的事务没提交就已经接收到火币推过订阅消息
         *
         *
         * 推送订单不存在，可能是本地订单未提交，火币已更新并推送。
         * 延时1s再次查询，如果存在就更新本地订单，不存在则记录该次推送。方便后续操作。
         */
        if (quantizedOrder == null) {
            int i = 0;
            while (i < 3) {
                try {
                    Thread.sleep(1000);
                    quantizedOrder = quantizedOrderService.selectByPrimaryKey(orderId);
                    if (quantizedOrder != null) {
                        break;
                    } else {
                        i++;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (quantizedOrder == null) {
                quantizedOrderInfo.setStatus(QuantizedOrderInfoConstant.STATUS_U);
                quantizedOrderInfoService.insert(quantizedOrderInfo);
                return;
            }
        }

        quantizedOrderInfo.setStatus(QuantizedOrderInfoConstant.STATUS_N);
        quantizedOrderInfo.setCctId(quantizedOrder.getCctId());
        quantizedOrderInfoService.insert(quantizedOrderInfo);
        try {
            quantizedOrderService.updateOrder(symbol, orderId);
            //update为成功
            quantizedOrderInfo.setStatus(QuantizedOrderInfoConstant.STATUS_Y);
            quantizedOrderInfoService.update(quantizedOrderInfo);
        }catch (Exception e){
            LOG.error(""+e.getMessage());
        }

    }

    @Override
    public void handleByInit(String symbol, Long orderId) {
        quantizedOrderService.updateOrder(symbol, orderId);
    }

    @Override
    public List<TradingRecordDTO> getHistoricalTrade(String coinName, String unitName, Integer size) {
        //火币查询订单信息
        SyncRequestClient syncRequestClient = SyncRequestClient.create(getAccount().getApiKey(), getAccount().getSecretKey());
        List<Trade> tradeList = syncRequestClient.getHistoricalTrade((coinName + unitName).toLowerCase(), size);
        List<TradingRecordDTO> tradingRecords = new ArrayList<>();
        for (Trade trade : tradeList) {
            TradingRecordDTO tradingRecord = new TradingRecordDTO();
            tradingRecord.setId(trade.getTradeId());
            tradingRecord.setMakerId(trade.getTradeId());
            tradingRecord.setTakerId(trade.getTradeId());
            tradingRecord.setMakerPrice(trade.getPrice());
            tradingRecord.setTakerPrice(trade.getPrice());
            tradingRecord.setTradingType(trade.getDirection().toString());
            tradingRecord.setTradingNum(trade.getAmount());
            tradingRecord.setCoinName(coinName);
            tradingRecord.setUnitName(unitName);
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(trade.getTimestamp());
            calendar.add(Calendar.HOUR, +8);
            tradingRecord.setCreateTime(calendar.getTime());
            tradingRecords.add(tradingRecord);
        }
        return tradingRecords;
    }

    @Override
    public List<MarketDTO> getMarketDTOHistoricalTrade(String coinName, String unitName, Integer size) {
        //火币查询订单信息
        SyncRequestClient syncRequestClient = SyncRequestClient.create(getAccount().getApiKey(), getAccount().getSecretKey());
        List<Trade> tradeList = syncRequestClient.getHistoricalTrade((coinName + unitName).toLowerCase(), size);
        List<MarketDTO> marketDTOS = new ArrayList<>();
        for (Trade trade : tradeList) {
            MarketDTO marketDTO = new MarketDTO();
            marketDTO.setCoinName(coinName);
            marketDTO.setUnitName(unitName);
            marketDTO.setTotalNum(trade.getAmount());
            marketDTO.setTotalLastNum(trade.getAmount());
            marketDTO.setUnitPrice(trade.getPrice());
            marketDTO.setTradingType(trade.getDirection().toString());
            marketDTOS.add(marketDTO);
        }
        return marketDTOS;
    }
    private HistoricalOrdersRequest cresteHistoricalOrdersRequest(String symbol, OrderState orderState) {
        return new HistoricalOrdersRequest(symbol, orderState);
    }

    private QuantizedAccount getAccount() {
        return accountService.findOne();
    }

}
