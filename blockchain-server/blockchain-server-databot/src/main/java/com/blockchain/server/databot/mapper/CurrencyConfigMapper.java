package com.blockchain.server.databot.mapper;

import com.blockchain.server.databot.entity.CurrencyConfig;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * CurrencyConfigMapper 数据访问类
 *
 * @version 1.0
 * @date 2019-06-03 11:37:01
 */
@Repository
public interface CurrencyConfigMapper extends Mapper<CurrencyConfig> {

    /***
     * 根据币对查询币对配置
     * @param currencyPair
     * @return
     */
    CurrencyConfig selectByCurrencyPair(@Param("currencyPair") String currencyPair);

    /***
     * 根据状态查询币对配置
     * @return
     */
    List<CurrencyConfig> selectByStatus(@Param("status") String status);

}