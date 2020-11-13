package com.blockchain.server.currency.mapper;

import com.blockchain.server.currency.dto.CurrencyMarketDTO;
import com.blockchain.server.currency.dto.CurrencyMarketKDTO;
import com.blockchain.server.currency.model.CurrencyMarket;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Repository
public interface CurrencyMarketMapper extends Mapper<CurrencyMarket> {
    /**
     * K线数据查询
     * @param params
     * @return
     */
    List<CurrencyMarketKDTO> queryForK(Map<String, Object> params);



}