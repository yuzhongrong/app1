package com.blockchain.server.eos.eos4j;

import java.math.BigInteger;

/**
 * @author Harvey
 * @date 2019/2/16 14:08
 * @user WIN10
 */
public class GetTransactionParamentDto {

    private String id;
    private BigInteger block_num_hint;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigInteger getBlock_num_hint() {
        return block_num_hint;
    }

    public void setBlock_num_hint(BigInteger block_num_hint) {
        this.block_num_hint = block_num_hint;
    }
}
