package com.blockchain.server.otc.mapper;

import com.blockchain.server.otc.entity.UserPayInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * UserPayInfoMapper 数据访问类
 *
 * @version 1.0
 * @date 2019-04-15 14:35:49
 */
@Repository
public interface UserPayInfoMapper extends Mapper<UserPayInfo> {

    /***
     * 根据userId和支付类型查询支付信息
     * @param userId
     * @param payType
     * @return
     */
    UserPayInfo selectByUserIdAndPayType(@Param("userId") String userId, @Param("payType") String payType);

    /***
     * 根据userId查询用户支付信息列表
     * @param userId
     * @return
     */
    List<UserPayInfo> listByUserId(@Param("userId") String userId);
}