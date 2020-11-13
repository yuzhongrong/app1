package com.blockchain.server.cct.service;

import com.blockchain.server.cct.dto.trading.DetailByOrderIdDTO;
import com.blockchain.server.cct.dto.trading.ListUserDetailDTO;
import com.blockchain.server.cct.entity.TradingDetail;

import java.util.List;

public interface TradingDetailService {

    /***
     * 插入成交订单扣费信息
     * @param detail
     * @return
     */
    int insertTradingDetail(TradingDetail detail);

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
    List<ListUserDetailDTO> listUserDetail(String userId, String coinName, String unitName, String beginTime,
                                           String lastTime, String status);

    /***
     * 根据发布id查询用户成交记录列表
     * @param orderId
     * @return
     */
    List<DetailByOrderIdDTO> listDetailByOrderId(String orderId);
}
