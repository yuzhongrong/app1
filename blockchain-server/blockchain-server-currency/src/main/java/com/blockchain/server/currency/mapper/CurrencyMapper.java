package com.blockchain.server.currency.mapper;

import com.blockchain.server.currency.dto.CurrencyDTO;
import com.blockchain.server.currency.model.Currency;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface CurrencyMapper extends Mapper<Currency> {

    /**
     * 获取货币信息
     * @param currencyName ：货币名称
     * @param lg ：语种
     * @return
     */
    CurrencyDTO getByCurrencyName(@Param("currencyName")String currencyName,@Param("lg")String lg);

    List<String> getQuoteCurrency();
}