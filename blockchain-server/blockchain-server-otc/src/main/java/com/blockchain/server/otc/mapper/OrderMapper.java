package com.blockchain.server.otc.mapper;

import com.blockchain.server.otc.dto.order.OrderDTO;
import com.blockchain.server.otc.entity.Order;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.Date;
import java.util.List;

/**
 * OrderMapper 数据访问类
 *
 * @version 1.0
 * @date 2019-04-15 10:37:32
 */
@Repository
public interface OrderMapper extends Mapper<Order> {

    /***
     * 根据广告id和单个或多个状态查询订单
     * @param adId
     * @param status
     * @return
     */
    List<Order> selectByAdIdAndStatus(@Param("adId") String adId, @Param("status") String[] status);

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
    List<OrderDTO> listUserOrder(@Param("userId") String userId, @Param("coinName") String coinName,
                                 @Param("unitName") String unitName, @Param("orderType") String orderType,
                                 @Param("orderStatus") String orderStatus, @Param("payType") String payType);

    /***
     * 根据userid查询订单
     * @param userId
     * @return
     */
    Order selectByUserId(@Param("userId") String userId);

    /***
     * 使用排他锁查询订单
     * @param orderId
     * @return
     */
    Order selectByIdForUpdate(@Param("orderId") String orderId);

    /***
     * 根据订单流水号查询订单
     * @param orderNumber
     * @return
     */
    Order selectByOrderNumber(@Param("orderNumber") String orderNumber);

    /***
     * 根据userId和订单Id查询
     * @param userId
     * @param orderId
     * @return
     */
    Order selectByUserIdAndOrderID(@Param("userId") String userId, @Param("orderId") String orderId);

    /***
     * 根据状态查询订单
     * @param status
     * @return
     */
    List<Order> listByStatus(@Param("status") String status);

    /***
     * 根据状态、创建时间比较 查询
     * @param status
     * @param date
     * @param offest
     * @param rowConut
     * @return
     */
    List<String> selectByStatusAndCreateTime(@Param("status") String status,
                                             @Param("date") Date date,
                                             @Param("offest") Integer offest,
                                             @Param("rowConut") Integer rowConut);
}