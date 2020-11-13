package com.blockchain.server.quantized.mapper;

import com.blockchain.server.quantized.entity.QuantizedAccount;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;


@Repository
public interface AccountMapper extends Mapper<QuantizedAccount> {
}
