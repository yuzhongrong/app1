package com.blockchain.server.imjg.dto;

import com.blockchain.common.base.dto.BaseDTO;
import lombok.Data;

import java.util.List;

@Data
public class JgMessageLogDTO extends BaseDTO {


    private int total;

    private int count;

    private String cursor;

    private List<JgMessageDTO> messages;
}
