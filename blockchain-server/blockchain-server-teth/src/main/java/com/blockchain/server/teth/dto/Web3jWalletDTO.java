package com.blockchain.server.teth.dto;

import lombok.Data;

@Data
public class Web3jWalletDTO {
    private String addr;
    private String privateKey;
    private String keystore;
}
