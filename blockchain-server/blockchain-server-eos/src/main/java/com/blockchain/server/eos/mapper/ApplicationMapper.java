package com.blockchain.server.eos.mapper;

import com.blockchain.server.eos.entity.Application;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author Harvey
 * @date 2019/2/18 16:50
 * @user WIN10
 */
@Repository
public interface ApplicationMapper extends Mapper<Application> {

    /**
     * 查询是否存在该应用钱包
     * @param walletType
     * @return
     */
    Integer selectWalletCountByWalletType(@Param("walletType") String walletType);
}
