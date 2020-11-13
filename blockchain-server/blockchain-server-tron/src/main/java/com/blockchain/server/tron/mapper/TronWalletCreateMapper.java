package com.blockchain.server.tron.mapper;

import com.blockchain.server.tron.entity.TronWalletCreate;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author Harvey
 * @date 2019/2/18 16:50
 * @user WIN10
 */
@Repository
public interface TronWalletCreateMapper extends Mapper<TronWalletCreate> {


    /**
     * 通过状态查询资金账户
     * @param status
     * @return
     */
    TronWalletCreate selectByStatus(@Param("status") Integer status);
}
