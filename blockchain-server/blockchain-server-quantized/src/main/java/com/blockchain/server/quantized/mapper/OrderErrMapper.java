package com.blockchain.server.quantized.mapper;

import com.blockchain.server.quantized.entity.OrderErr;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;


@Repository
public interface OrderErrMapper extends Mapper<OrderErr> {
}
