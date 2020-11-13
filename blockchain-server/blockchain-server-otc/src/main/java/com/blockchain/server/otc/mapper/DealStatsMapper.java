package com.blockchain.server.otc.mapper;

import com.blockchain.server.otc.entity.DealStats;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.Date;

/**
 * DealStatsMapper 数据访问类
 *
 * @version 1.0
 * @date 2019-04-15 10:37:31
 */
@Repository
public interface DealStatsMapper extends Mapper<DealStats> {
    /***
     * 更新广告交易次数
     * @param userId
     * @return
     */
    int updateAdTransNum(@Param("userId") String userId, @Param("modifyTime") Date modifyTime);

    /***
     * 更新广告成交次数
     * @param userId
     * @return
     */
    int updateAdMarkNum(@Param("userId") String userId, @Param("modifyTime") Date modifyTime);

    /***
     * 更新卖单成交次数
     * @param userId
     * @return
     */
    int updateOrderSellNum(@Param("userId") String userId, @Param("modifyTime") Date modifyTime);

    /***
     * 更新买单成交次数
     * @param userId
     * @return
     */
    int updateOrderBuyNum(@Param("userId") String userId, @Param("modifyTime") Date modifyTime);
}