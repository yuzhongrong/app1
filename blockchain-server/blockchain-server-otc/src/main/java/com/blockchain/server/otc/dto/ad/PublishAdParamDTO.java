package com.blockchain.server.otc.dto.ad;

import com.blockchain.common.base.dto.BaseDTO;
import com.blockchain.common.base.entity.BaseModel;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PublishAdParamDTO extends BaseDTO {
    private String pass;
    private String userId;
    private BigDecimal price;
    private BigDecimal totalNum;
    private String coinName;
    private String unitName;
    private BigDecimal minLimit;
    private String[] payType;
    private String remark;
}
