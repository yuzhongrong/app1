package com.blockchain.server.imjg.dto;

import com.blockchain.common.base.dto.BaseDTO;
import lombok.Data;

@Data
public class JgError extends BaseDTO {


    private int code;

    private String message;

}
