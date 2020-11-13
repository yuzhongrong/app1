package com.blockchain.server.user.service;


import com.blockchain.common.base.dto.UserRelationDTO;
import com.blockchain.common.base.dto.UserTeamDTO;
import com.blockchain.server.user.entity.UserRelation;

import java.util.List;
import java.util.Set;

public interface UserRelationService {
    /**
     * 插入用户的推荐关系
     *
     * @param userId
     * @param invitationCode
     */
    int insertRelationChain(String userId, String invitationCode);

    /**
     * 初始化用户关系链信息（无推荐关系）
     *
     * @param userId 用户id
     */
    int insertRelationChainByNotInvited(String userId);

    /**
     * 根据用户ID,查询用户关系表
     *
     * @param userId 用户关系表
     * @return
     */
    UserRelation findByUserId(String userId);

    /**
     * 根据用户id查询直推下级
     */
    Set<String> getDirects(String userId);

    /**
     * 根据用户id查询整个下级团队
     */
    Set<String> getAllSubordinate(String userId);

    /**
     * 根据用户id查询直推下级信息列表
     */
    List<UserTeamDTO> listUserDirectTeam(String userId);

    void getAllUserRelation();
}
