package com.blockchain.server.cct.mapper;

import com.blockchain.common.base.dto.MarketDTO;
import com.blockchain.server.cct.entity.PublishOrder;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * AppCctPublishOrderMapper 数据访问类
 *
 * @version 1.0
 * @date 2019-02-15 17:38:07
 */
@Repository
public interface PublishOrderMapper extends Mapper<PublishOrder> {

    /***
     * 根据id查询订单
     * @param orderId
     * @return
     */
    PublishOrder selectById(@Param("orderId") String orderId);

    /***
     * 根据id查询订单，使用锁
     * @param orderId
     * @return
     */
    PublishOrder selectByIdForUpdate(@Param("orderId") String orderId);

    /***
     * 根据条件查询用户订单
     * @param userId
     * @param coinName
     * @param unitName
     * @param orderType
     * @param publishType
     * @param orderStatus
     * @return
     */
    List<PublishOrder> listUserOrder(@Param("userId") String userId, @Param("coinName") String coinName,
                                     @Param("unitName") String unitName, @Param("orderType") String orderType,
                                     @Param("publishType") String publishType, @Param("status") String[] orderStatus);

    /***
     * 根据条件查询订单
     * @param coinName
     * @param unitName
     * @param orderType
     * @param orderStatus
     * @return
     */
    List<MarketDTO> listOrder(@Param("coinName") String coinName, @Param("unitName") String unitName,
                              @Param("type") String orderType, @Param("status") String orderStatus,
                              @Param("publishType") String publishtType, @Param("sort") String sort);

    /***
     * 查询可撮合买单
     * @param unitName 基本货币
     * @param coinName 二级货币
     * @param status 订单状态
     * @param price 单价
     * @param publishType 发布类型
     * @param pageSize  查询条数
     * @return
     */
    List<PublishOrder> listMatchBuyOrder(@Param("unitName") String unitName, @Param("coinName") String coinName,
                                         @Param("status") String status, @Param("price") BigDecimal price,
                                         @Param("publishType") String publishType, @Param("pageSize") int pageSize);

    /***
     * 查询可撮合卖单
     * @param unitName
     * @param coinName
     * @param status
     * @param price
     * @param publishType
     * @param pageSize
     * @return
     */
    List<PublishOrder> listMatchSellOrder(@Param("unitName") String unitName, @Param("coinName") String coinName,
                                          @Param("status") String status, @Param("price") BigDecimal price,
                                          @Param("publishType") String publishType, @Param("pageSize") int pageSize);

    /***
     * 查询未撮合订单 - 用于自动撮合机器人
     * @param unitName
     * @param coinName
     * @param minPrice
     * @param maxPrice
     * @param orderStatus
     * @param publishType
     * @return
     */
    List<PublishOrder> listMatchOrderToBot(@Param("unitName") String unitName, @Param("coinName") String coinName,
                                           @Param("minPrice") BigDecimal minPrice, @Param("maxPrice") BigDecimal maxPrice,
                                           @Param("orderStatus") String orderStatus, @Param("publishType") String publishType,
                                           @Param("pageSize") int pageSize);

    /***
     * 更新订单状态，使用乐观锁
     * @param orderId
     * @param beforeStatus
     * @param laterStatus
     * @return
     */
    int updateStatusInVersionLock(@Param("orderId") String orderId,
                                  @Param("beforeStatus") String beforeStatus,
                                  @Param("laterStatus") String laterStatus,
                                  @Param("time") Date time);

    /***
     * 根据币对、状态查询订单
     * @param coinName
     * @param unitName
     * @param orderStatus
     * @return
     */
    List<MarketDTO> listOrderByCoinAndUnitAndStatus(@Param("coinName") String coinName, @Param("unitName") String unitName,
                                                    @Param("status") String orderStatus, @Param("type") String orderType,
                                                    @Param("publishType") String publishType, @Param("sort") String sort);
}
