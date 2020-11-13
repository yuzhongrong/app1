package com.blockchain.server.cct.mapper;

import com.blockchain.server.cct.entity.TradingRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * CctTradingRecordMapper 数据访问类
 *
 * @version 1.0
 * @date 2019-02-16 15:08:15
 */
@Repository
public interface TradingRecordMapper extends Mapper<TradingRecord> {
    /***
     * 根据交易对查询成交记录
     * @param coinName
     * @param unitName
     * @return
     */
    List<TradingRecord> listRecordByCoinAndUnit(@Param("coinName") String coinName, @Param("unitName") String unitName);

    /***
     * 根据交易币对查询最新一条成交记录
     * @param coinName
     * @param unitName
     * @return
     */
    TradingRecord selectByCoinAndUnitLimitOne(@Param("coinName") String coinName, @Param("unitName") String unitName);
}