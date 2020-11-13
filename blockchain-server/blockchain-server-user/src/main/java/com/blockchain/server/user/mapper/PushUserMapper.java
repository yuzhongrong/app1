package com.blockchain.server.user.mapper;

import com.blockchain.server.user.entity.PushUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.Date;

/**
 * UserMapper 数据访问类
 *
 * @version 1.0
 * @date 2019-06-18 17:27:35
 */
@Repository
public interface PushUserMapper extends Mapper<PushUser> {

    /***
     * 根据用户id查询
     * @param userId
     * @return
     */
    PushUser selectByUserId(@Param("userId") String userId);

    /***
     * 根据客户端id查询
     * @param clientId
     * @return
     */
    PushUser selectByClientId(@Param("clientId") String clientId);

    /***
     * 根据用户id更新
     * @param userId
     * @param clientId
     * @param language
     * @param modifyTime
     * @return
     */
    int updateUser(@Param("userId") String userId, @Param("clientId") String clientId,
                   @Param("language") String language, @Param("modifyTime") Date modifyTime);
}