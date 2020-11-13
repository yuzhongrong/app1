package com.blockchain.server.otc.mapper;

import com.blockchain.server.otc.entity.MarketUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * MarketUserMapper 数据访问类
 *
 * @version 1.0
 * @date 2019-07-13 11:48:11
 */
@Repository
public interface MarketUserMapper extends Mapper<MarketUser> {

    /***
     * 根据用户id查询
     * @param userId
     * @return
     */
    MarketUser selectByUserId(@Param("userId") String userId);
}