package com.blockchain.server.cct.service;

import com.blockchain.server.cct.dto.order.PublishOrderDTO;
import com.blockchain.server.cct.entity.PublishOrder;

import java.math.BigDecimal;
import java.util.List;

public interface MatchService {

    /***
     * 循环条件的匹配的订单集合，进行撮合操作
     * @param matchId
     * @param bymatchId
     */
    boolean handleMatch(String matchId, String bymatchId);

    /***
     * 查询可撮合的订单
     * @param orderId
     * @return
     */
    List<PublishOrder> listBeMatchOrder(String orderId);

    /***
     * 查询单价范围内的挂单
     * @param coinName
     * @param unitName
     * @return
     */
    List<PublishOrderDTO> listBeMatchOrderToBot(String coinName, String unitName, BigDecimal minPrice, BigDecimal maxPrice);

    /**
     * 量化部分交易
     *
     * @param id       广告id
     * @param transNum 成交的数量
     * @param price    成交的总价
     * @param isFill   是否是完全成交
     */
    void handleQuantizedTransation(String id, BigDecimal transNum, BigDecimal price, boolean isFill);

    /***
     * 发送推送通知，推送买卖盘口数据
     * @param coinName
     * @param unitName
     */
    void convertAndSend(String coinName, String unitName);
}
