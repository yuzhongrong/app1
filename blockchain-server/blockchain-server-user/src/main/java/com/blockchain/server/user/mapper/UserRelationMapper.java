package com.blockchain.server.user.mapper;

import com.blockchain.common.base.dto.UserRelationDTO;
import com.blockchain.common.base.dto.UserTeamDTO;
import com.blockchain.server.user.entity.UserRelation;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Set;

/**
 * AppUUserRelationMapper 数据访问类
 * @date 2019-02-21 13:37:18
 * @version 1.0
 */
@Repository
public interface UserRelationMapper extends Mapper<UserRelation> {

    /**
     * 根据用户id查询直推下级
     */
    Set<String> getDirects(@Param("userId") String userId);

    /**
     * 根据用户id查询整个下级团队
     */
    Set<String> getAllSubordinate(@Param("userId") String userId);

    /**
     * 根据用户id查询直推下级信息列表
     */
    List<UserTeamDTO> listUserDirectTeam(@Param("userId") String userId);

    /**
     * 获取所有用户的关系
     * */
    List<UserRelationDTO> getAllUserRelation();
}