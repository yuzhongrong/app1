package com.blockchain.server.quantized.mapper;

import com.blockchain.server.quantized.entity.QuantizedOrderInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.Date;
import java.util.List;


@Repository
public interface QuantizedOrderInfoMapper extends Mapper<QuantizedOrderInfo> {
    List<QuantizedOrderInfo> listByStatusAndCreateTime(@Param("status")String status,@Param("createTime") Date createTime);

    QuantizedOrderInfo selectByPrimaryKeyForUpdate(@Param("id")String id);
}
