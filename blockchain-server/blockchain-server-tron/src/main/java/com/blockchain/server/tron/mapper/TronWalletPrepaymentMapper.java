package com.blockchain.server.tron.mapper;

import com.blockchain.server.tron.entity.TronWalletPrepayment;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * WalletPrepaymentMapper 数据访问类
 *
 * @version 1.0
 * @date 2018-11-05 15:10:47
 */
@Repository
public interface TronWalletPrepaymentMapper extends Mapper<TronWalletPrepayment> {
    /**
     * 根据 应用标识 与 商户单号 查询是否存在
     *
     * @param appId
     * @param tradeNo
     * @return [ 0,1 ]
     */
    int findByAppIdAndTradeNoLimit(@Param("appId") String appId, @Param("tradeNo") String tradeNo);
}