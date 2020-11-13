package com.blockchain.server.tron.mapper;

import com.blockchain.server.tron.entity.TronApplication;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author Harvey
 * @date 2019/2/18 16:50
 * @user WIN10
 */
@Repository
public interface TronApplicationMapper extends Mapper<TronApplication> {

    /**
     * 查询是否存在该应用钱包
     * @param walletType
     * @return
     */
    Integer selectWalletCountByWalletType(@Param("walletType") String walletType);
}
