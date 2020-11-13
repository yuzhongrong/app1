package com.blockchain.server.quantized.service.impl;

import com.blockchain.common.base.dto.MarketDTO;
import com.blockchain.server.quantized.common.constant.DepthConstant;
import com.blockchain.server.quantized.common.constant.RedisKeyConstant;
import com.blockchain.server.quantized.common.constant.TradingOnConstant;
import com.blockchain.server.quantized.entity.QuantizedAccount;
import com.blockchain.server.quantized.entity.TradingOn;
import com.blockchain.server.quantized.feign.CctFeign;
import com.blockchain.server.quantized.service.*;
import com.huobi.client.SubscriptionClient;
import com.huobi.client.SubscriptionOptions;
import com.huobi.client.constants.UrlConstant;
import com.huobi.client.model.enums.OrderState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: Liusd
 * @create: 2019-05-07 10:23
 **/
@Service
public class SubscriptionServiceImpl implements SubscriptionService {


    private static final Logger LOG = LoggerFactory.getLogger(SubscriptionServiceImpl.class);

    @Autowired
    private OrderService orderService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private QuantizedOrderService quantizedOrderService;
    @Autowired
    private TradingOnService tradingOnService;
    @Autowired
    private CctFeign cctFeign;
    @Autowired
    private RedisTemplate redisTemplate;

    private SubscriptionClient subscriptionClient;

    private Map<String, String> symbolMap = new ConcurrentHashMap<>();

    @Override
    public void initOrder() {
        //遍历本地未完结的订单、
        quantizedOrderService.listByNoFinished().forEach(quantizedOrderDto -> {
            LOG.debug("交易对：{} , 订单id 为 ：{}", quantizedOrderDto.getSymbol(), quantizedOrderDto.getId());
            System.out.println("--------订单id " + quantizedOrderDto.getId());
            //获取历史订单保存到本地
            try {
                orderService.handleByInit(quantizedOrderDto.getSymbol(), quantizedOrderDto.getId());
            } catch (Exception e) {
                LOG.error("同步历史订单出错,订单id：" + quantizedOrderDto.getId() + "==>" + e.getMessage());
                e.printStackTrace();
            }
        });
    }

    @Override
    public void subscribeAll() {
        tradingOnService.list(TradingOnConstant.STATE_SUBSCRIBE).forEach(symbol -> {
            subscribeTradingOn(symbol);
        });
        LOG.info("subOrder 结束 ：{}", new Date());
    }

    /**
     * 账号信息
     */
    private QuantizedAccount getAccount() {
        return accountService.findOne();
    }

    /**
     * 订阅请求头,使用synchronized，单例
     *
     * @return
     */
    private SubscriptionClient cresteSubscriptionClient() {
        if (subscriptionClient == null) {
            synchronized (this) {
                if (subscriptionClient == null) {
                    SubscriptionOptions option = new SubscriptionOptions();
                    option.setUri(UrlConstant.ORDERURL);
                    subscriptionClient = SubscriptionClient.create(getAccount().getApiKey(), getAccount().getSecretKey(), option);
                    LOG.info("创建火币客户端成功:"+subscriptionClient);
                }
            }
        }
        return subscriptionClient;
    }

    /**
     * @Description: 订阅交易对
     * @Param: [symbol]
     * @return: void
     * @Author: Liu.sd
     * @Date: 2019/5/13
     */
    @Override
    public void subscribeTradingOn(TradingOn symbol) {
        SubscriptionClient subscriptionClient = cresteSubscriptionClient();
        String symbols = symbol.getCoinName() + symbol.getUnitName();
        String coinName = symbol.getCoinName().toUpperCase();
        String unitName = symbol.getUnitName().toUpperCase();
        //订阅订单
        subscriptionClient.subscribeOrderUpdateEvent(symbols, (orderEvent) -> {
            //存入数据库中
            LOG.info("币对{}订单发生变化，订单id：{}，时间为：{}", symbols, orderEvent.getData().getOrderId(), new Date());
            if (orderEvent.getData().getState().equals(OrderState.SUBMITTED)) {
                return;
            } else {
                orderService.handleBySubscribe(orderEvent.getData().getSymbol(), orderEvent.getData().getOrderId());
            }
        });
        //最新成交订单
        //深度
        subscriptionClient.subscribePriceDepthEvent(symbols, (priceDepthEvent) -> {
            LOG.info("订阅{}的深度，时间为：{},数量为{}", symbols, new Date(), priceDepthEvent.getData().getBids().size());
            String orderBuyKey = RedisKeyConstant.getOrderList(DepthConstant.BUY, coinName, unitName);
            String orderSellKey = RedisKeyConstant.getOrderList(DepthConstant.SELL, coinName, unitName);

            List<MarketDTO> buyMarkets = new ArrayList<>();
            priceDepthEvent.getData().getBids().forEach(depthEntry -> {
                MarketDTO marketDTO = new MarketDTO();
                marketDTO.setCoinName(coinName);
                marketDTO.setUnitName(unitName);
                marketDTO.setTotalNum(depthEntry.getAmount());
                marketDTO.setTotalLastNum(depthEntry.getAmount());
                marketDTO.setUnitPrice(depthEntry.getPrice());
                marketDTO.setTradingType(DepthConstant.BUY);
                buyMarkets.add(marketDTO);

                /*redisTemplate.opsForList().leftPush(orderBuyKey, marketDTO);
                long market = redisTemplate.opsForList().size(orderBuyKey);
                if (market > RedisKeyConstant.LIST_SIZE) {
                    redisTemplate.opsForList().rightPop(orderBuyKey);
                }*/
            });
            //设置买单列表
            redisTemplate.opsForValue().set(orderBuyKey, buyMarkets);

            List<MarketDTO> sellMarkets = new ArrayList<>();
            priceDepthEvent.getData().getAsks().forEach(depthEntry -> {
                MarketDTO marketDTO = new MarketDTO();
                marketDTO.setCoinName(coinName);
                marketDTO.setUnitName(unitName);
                marketDTO.setTotalNum(depthEntry.getAmount());
                marketDTO.setTotalLastNum(depthEntry.getAmount());
                marketDTO.setUnitPrice(depthEntry.getPrice());
                marketDTO.setTradingType(DepthConstant.SELL);
                sellMarkets.add(marketDTO);

//                redisTemplate.opsForList().leftPush(orderSellKey, marketDTO);
//                long market = redisTemplate.opsForList().size(orderSellKey);
//                if (market > RedisKeyConstant.LIST_SIZE) {
//                    redisTemplate.opsForList().rightPop(orderSellKey);
//                }
            });
            redisTemplate.opsForValue().set(orderSellKey, sellMarkets);

            //推送到币币交易
            pushToCct(symbol);
        }, (error) -> {
            LOG.debug("订阅{}的深度失败,type:{},message:{}", symbols, error.getErrType(), error.getMessage());
        });
    }

    /**
     * 推送到币币交易
     *
     * @param symbol
     */
    private void pushToCct(TradingOn symbol) {
        String symbols = symbol.getCoinName() + symbol.getUnitName();//币对
        String coinName = symbol.getCoinName().toUpperCase();
        String unitName = symbol.getUnitName().toUpperCase();
        //限流，推送到币币模块刷新前端盘口不需要太频繁
        String value = symbolMap.putIfAbsent(symbols, symbols);
        //拿到设置
        if (value == null) {
            try {
                //查询币对最新的订阅状态
                TradingOn trading = tradingOnService.selectOne(symbol.getCoinName(), symbol.getUnitName());
                if (!trading.getState().equals(TradingOnConstant.STATE_SUBSCRIBE)) {
                    //如果不是订阅状态，直接返回
                    symbolMap.remove(symbols);
                    return;
                }
                LOG.info("推送到币币模块:{}", symbols);
                //币币推送
                cctFeign.pushNewOrderRecord(coinName, unitName);
                Thread.sleep(500);
            } catch (InterruptedException e) {
            } finally {
                symbolMap.remove(symbols);
            }
        }
    }

    @Override
    public void unSubscribe(TradingOn trading) {
        SubscriptionClient subscriptionClient = cresteSubscriptionClient();
        String symbols = trading.getCoinName() + trading.getUnitName();
        subscriptionClient.unSubscribeSymbol(symbols);
    }

    @Override
    public void push(TradingOn symbol) {
       // pushToCct(symbol);
    }
}
