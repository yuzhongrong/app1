package com.blockchain.server.eth.dto;

import lombok.Data;

@Data
public class Web3jWalletDTO {
    private String addr;
    private String privateKey;
    private String keystore;
}
