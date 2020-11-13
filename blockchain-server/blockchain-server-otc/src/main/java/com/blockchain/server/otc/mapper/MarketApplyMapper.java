package com.blockchain.server.otc.mapper;

import com.blockchain.server.otc.entity.MarketApply;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * MarketApplyMapper 数据访问类
 *
 * @version 1.0
 * @date 2019-07-13 11:48:11
 */
@Repository
public interface MarketApplyMapper extends Mapper<MarketApply> {

    /***
     * 根据用户id和申请类型
     * @param userId
     * @param applyType
     * @return
     */
    MarketApply selectByUserIdAndApplyTypeAndStatus(@Param("userId") String userId,
                                                    @Param("applyType") String applyType,
                                                    @Param("applyStatus") String applyStatus);
}