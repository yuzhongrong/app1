package com.blockchain.server.imjg.dto;

import com.blockchain.common.base.dto.BaseDTO;
import lombok.Data;

@Data
public class JgUserStatDTO extends BaseDTO {

    private Boolean login;

    private Boolean online;

    private String platform;
}
