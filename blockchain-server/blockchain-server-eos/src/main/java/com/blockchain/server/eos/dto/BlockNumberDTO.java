package com.blockchain.server.eos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.Date;

/**
 * \* <p>Desciption:</p>
 * \* CreateTime: 2018/12/7 17:09
 * \* User: XianChaoWei
 * \* Version: V1.0
 * \
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlockNumberDTO {

    private BigInteger currentBlock;

    private BigInteger newBlock;

    private Date createTime;

}
