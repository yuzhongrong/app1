package com.blockchain.server.databot.mapper;

import com.blockchain.server.databot.entity.MatchConfig;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;


/**
 * MatchConfigMapper 数据访问类
 *
 * @version 1.0
 * @date 2019-06-25 13:52:32
 */
@Repository
public interface MatchConfigMapper extends Mapper<MatchConfig> {

    /***
     * 根据基本货币、二级货币、状态查询订单
     * @param coinName
     * @param unitName
     * @param stauts
     * @return
     */
    MatchConfig selectByCoinAndUnitAndStauts(@Param("coinName") String coinName,
                                             @Param("unitName") String unitName,
                                             @Param("status") String stauts);
}