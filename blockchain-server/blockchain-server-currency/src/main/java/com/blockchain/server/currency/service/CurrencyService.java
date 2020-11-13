package com.blockchain.server.currency.service;


import com.blockchain.server.currency.dto.CurrencyDTO;
import com.blockchain.server.currency.model.Currency;

import java.util.List;

public interface CurrencyService {

    Currency get(String currencyName);

    CurrencyDTO getCurrencyInfo(String currencyName, String lg);

    List<String> getQuoteCurrency();

}
