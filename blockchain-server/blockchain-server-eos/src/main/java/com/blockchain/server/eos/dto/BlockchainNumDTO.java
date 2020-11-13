package com.blockchain.server.eos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

/**
 * @author Harvey
 * @date 2019/2/21 15:57
 * @user WIN10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlockchainNumDTO {

    private BigInteger begin;
    private BigInteger current;
    private BigInteger newBlockNum;

}
