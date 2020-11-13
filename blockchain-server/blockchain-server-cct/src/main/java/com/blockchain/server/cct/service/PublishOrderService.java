package com.blockchain.server.cct.service;

import com.blockchain.common.base.dto.MarketDTO;
import com.blockchain.server.cct.dto.order.PublishOrderParamDTO;
import com.blockchain.server.cct.entity.PublishOrder;

import java.math.BigDecimal;
import java.util.List;

public interface PublishOrderService {

    /***
     * 发布限价买单
     * @param paramDTO
     * @return orderId
     */
    String handleLimitBuy(PublishOrderParamDTO paramDTO);

    /***
     * 发布限价卖单
     * @param paramDTO
     * @return orderId
     */
    String handleLimitSell(PublishOrderParamDTO paramDTO);

    /***
     * 发布限价买单 - 用于撮合机器人
     * @param userId
     * @param coinName
     * @param unitName
     * @param totalNum
     * @param price
     * @return
     */
    String handleLimitBuyToBot(String userId, String coinName, String unitName,
                               BigDecimal totalNum, BigDecimal price);

    /***
     * 发布限价买单 - 用于撮合机器人
     * @param userId
     * @param coinName
     * @param unitName
     * @param totalNum
     * @param price
     * @return
     */
    String handleLimitSellToBot(String userId, String coinName, String unitName,
                                BigDecimal totalNum, BigDecimal price);

    /**
     * 判断是否量化交易
     *
     * @param coinName
     * @param unitName
     * @return
     */
    Boolean getTradingOn(String coinName, String unitName);

    /***
     * 发布市价买单
     * @param paramDTO
     * @return
     */
    String handleMarketBuy(PublishOrderParamDTO paramDTO);

    /***
     * 发布市价卖单
     * @param paramDTO
     * @return
     */
    String handleMarketSell(PublishOrderParamDTO paramDTO);

    /***
     * 撤销订单
     * 用于外部接口
     *
     * @param orderId
     */
    int handleCancelOrder(String orderId, String userId);

    /***
     * 无法撮合时
     * 撤销市价订单
     * 用于程序内部
     *
     * @param orderId
     * @return
     */
    int handleCancelOrder(String orderId);

    /***
     * 撤销订单 - 用于撮合机器人
     * @param orderId
     * @param userId
     * @return
     */
    int handleCancelOrderToBot(String orderId, String userId);

    /***
     * 更新订单
     * @param order
     * @return
     */
    int updateByPrimaryKeySelective(PublishOrder order);

    /***
     * 更新订单状态，使用乐观锁
     * @param orderId
     * @param beforeStatus
     * @param laterStatus
     * @return
     */
    int updateStatusInVersionLock(String orderId, String beforeStatus, String laterStatus);

    /***
     * 根据id查询订单
     * @param orderId
     * @return
     */
    PublishOrder selectById(String orderId);

    /***
     * 使用锁查询订单
     * @param orderId
     * @return
     */
    PublishOrder selectByIdForUpdate(String orderId);

    /***
     * 根据条件查询首页订单
     * @param coinName
     * @param unitName
     * @param orderType
     * @param orderStatus
     * @param publishType
     * @param sort
     * @return
     */
    List<MarketDTO> listOrder(String coinName, String unitName, String orderType, String orderStatus,
                              String publishType, String sort);

    /***
     * 根据币种、状态查询订单
     * 用于行情深度图
     * @param coinName
     * @param unitName
     * @param orderStatus
     * @param orderType
     * @param sort
     * @return
     */
    List<MarketDTO> listOrderByCoinAndUnitAndStatus(String coinName, String unitName, String orderStatus,
                                                    String orderType, String sort);

    /***
     * 根据指定条件查询用户订单
     * @param userId
     * @param coinName
     * @param unitName
     * @param orderType
     * @param publishType
     * @param orderStatus
     * @return
     */
    List<PublishOrder> listUserOrder(String userId, String coinName, String unitName, String orderType,
                                     String publishType, String[] orderStatus);

    /**
     * 将订单改为撤单中
     *
     * @param orderId 订单id
     */
    void handleOrderToCanceling(String orderId);

    List<PublishOrder> selectByStatus(String status);
}
