package com.blockchain.server.cct.mapper;

import com.blockchain.server.cct.dto.trading.DetailByOrderIdDTO;
import com.blockchain.server.cct.dto.trading.ListUserDetailDTO;
import com.blockchain.server.cct.entity.TradingDetail;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * CctTradingDetailMapper 数据访问类
 *
 * @version 1.0
 * @date 2019-02-16 15:08:15
 */
@Repository
public interface TradingDetailMapper extends Mapper<TradingDetail> {

    /***
     * 查询用户成交记录
     * @param userId
     * @param coinName 基本货币
     * @param unitName 二级货币
     * @param beginTime 开始时间
     * @param lastTime 结束时间
     * @param status 发布订单状态
     * @return
     */
    List<ListUserDetailDTO> listUserDetail(@Param("userId") String userId, @Param("coinName") String coinName,
                                           @Param("unitName") String unitName, @Param("beginTime") String beginTime,
                                           @Param("lastTime") String lastTime, @Param("status") String status);

    /***
     * 根据发布id查询成交记录列表
     * @param orderId
     * @return
     */
    List<DetailByOrderIdDTO> listDetailByOrderId(@Param("orderId") String orderId);
}