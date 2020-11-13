package com.blockchain.server.currency.mapper;

import com.blockchain.server.currency.dto.CurrencyPairDTO;
import com.blockchain.server.currency.model.CurrencyPair;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

@Repository
public interface CurrencyPairMapper extends Mapper<CurrencyPair> {

    List<CurrencyPairDTO> getUsableList();
    List<CurrencyPairDTO> getHomeList();
    List<CurrencyPairDTO> getQuoteList(@Param("currencyName")String currencyName);


}