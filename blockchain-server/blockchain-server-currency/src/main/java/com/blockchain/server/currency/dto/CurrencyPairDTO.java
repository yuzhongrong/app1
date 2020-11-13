package com.blockchain.server.currency.dto;

import com.blockchain.common.base.dto.BaseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyPairDTO extends BaseDTO {

	private String currencyPair;

}