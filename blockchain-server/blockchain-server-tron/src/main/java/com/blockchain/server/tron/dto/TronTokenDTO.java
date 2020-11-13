package com.blockchain.server.tron.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TronTokenDTO {
    private String tokenAddr;
    private String tokenHexAddr;
    private String tokenSymbol;
    private Long timestamp;
}
