package com.blockchain.server.otc.dto.config;

import lombok.Data;

@Data
public class ConfigDTO {
    private String dataKey;
    private String dataValue;
    private String status;
    private java.util.Date modifyTime;
}
