package com.blockchain.server.otc.service;

import com.blockchain.server.otc.dto.order.OrderDTO;
import com.blockchain.server.otc.entity.Order;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface OrderService {
    /***
     * 根据广告id和单个或多个状态查询订单
     * @param adId
     * @param status
     * @return
     */
    List<Order> selectByAdIdAndStatus(String adId, String... status);

    /***
     * 根据参数查询用户订单
     * @param userId
     * @param coinName
     * @param unitName
     * @param orderType
     * @param orderStatus
     * @param payType
     * @return
     */
    List<OrderDTO> listUserOrder(String userId, String coinName, String unitName, String orderType, String orderStatus, String payType);

    /***
     * 根据id查询订单
     * @param orderId
     * @return
     */
    OrderDTO selectByUserIdAndOrderId(String userId, String orderId);

    /***
     * 法币买入
     * @param userId
     * @param adId
     * @param amount
     * @param price
     * @param turnover
     */
    String buyOrder(String userId, String adId, BigDecimal amount, BigDecimal price, BigDecimal turnover);

    /***
     * 法币卖出
     * @param userId
     * @param adId
     * @param amount
     * @param turnover
     * @param pass
     */
    String sellOrder(String userId, String adId, BigDecimal amount, BigDecimal price, BigDecimal turnover, String pass);

    /***
     * 撤销订单
     * @param userId
     * @param orderId
     */
    void cancelBuyOrder(String userId, String orderId);

    /****
     * 撤销订单，定时器调用
     * @param orderId
     */
    void autoCancelOrder(String orderId);

    /***
     * 根据订单状态查询订单
     * @param status
     * @return
     */
    List<Order> listByStatus(String status);

    /***
     * 查询广告伞下是否还有未完成订单
     * @param adId
     * @return true存在未完成订单
     */
    boolean checkOrdersUnfinished(String adId);

    /***
     * 确认付款
     * @param userId
     * @param orderId
     * @param payType
     */
    void pay(String userId, String orderId, String payType);

    /***
     * 确认收款
     * @param userId
     * @param orderId
     * @param pass
     */
    void receipt(String userId, String orderId, String pass);

    /***
     * 排他锁查询订单信息
     * @param orderId
     * @return
     */
    Order selectByIdForUpdate(String orderId);

    /***
     * 根据id查询订单
     * @param orderId
     * @return
     */
    Order selectById(String orderId);

    /***
     * 更新订单信息
     * @param order
     * @return
     */
    int updateByPrimaryKeySelective(Order order);

    /***
     * 根据订单流水号查询订单
     * @param orderNumber
     * @return
     */
    Order selectByOrderNumber(String orderNumber);

    /***
     * 查询可以撤销的订单id列表
     * @param status
     * @param date
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<String> selectAutoCancelOrder(String status, Date date, Integer pageNum, Integer pageSize);
}
