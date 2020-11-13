package com.blockchain.server.quantized.service;

import com.blockchain.common.base.dto.MarketDTO;
import com.blockchain.common.base.dto.TradingRecordDTO;
import com.huobi.client.model.Order;
import com.huobi.client.model.request.NewOrderRequest;

import java.util.List;

/**
 * @author: Liusd
 * @create: 2019-04-18 19:34
 **/
public interface OrderService {

    /**
     * @Description: 下单
     * @Param: [newOrderRequest]
     * @return: com.huobi.client.model.Order
     * @Author: Liu.sd
     * @Date: 2019/4/20
     */
    Order create(NewOrderRequest newOrderRequest, String userId, String cctId);

    /**
     * @Description: 撤单
     * @Param: [symbol, orderId]
     * @return: String
     * @Author: Liu.sd
     * @Date: 2019/4/20
     */
    String cancellations(String symbol, Long orderId);

    List<MarketDTO> getPriceDepth(String symbol, int size, String type);

    String cancel(String orderId);

    void handleBySubscribe(String symbol, long orderId);

    void handleByInit(String symbol, Long orderId);

    List<TradingRecordDTO> getHistoricalTrade(String coinName, String unitName, Integer size);

    /***
     * 用于币币查询行情深度数据
     * 返回构造的DTO为MarketDTO
     * @param coinName
     * @param unitName
     * @return
     */
    List<MarketDTO> getMarketDTOHistoricalTrade(String coinName, String unitName, Integer size);
}
