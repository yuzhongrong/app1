package com.blockchain.server.teth.mapper;

import com.blockchain.server.teth.entity.EthPrivateBalance;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.Date;
import java.util.List;

@Repository
public interface EthPrivateBalanceMapper extends Mapper<EthPrivateBalance> {

    /**
     * 获取私募资金列表
     */
    List<EthPrivateBalance> listPrivateBalance();

    /**
     * 扣减释放私募资金
     */
    Integer releaseBalance(@Param("id") String id, @Param("date") Date date);

}