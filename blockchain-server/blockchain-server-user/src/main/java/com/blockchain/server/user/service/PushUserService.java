package com.blockchain.server.user.service;

import com.blockchain.server.user.entity.PushUser;

public interface PushUserService {

    /***
     * 新建或者更新客户端数据
     * @param userId
     * @param clientId
     * @param language
     * @return
     */
    Integer insertOrUpadteUser(String userId, String clientId, String language);

    /***
     * 新建用户客户端数据
     * @param userId
     * @param clientId
     * @param language
     * @return
     */
    Integer insertUser(String userId, String clientId, String language);

    /***
     * 更新用户客户端数据
     * @param userId
     * @param clientId
     * @param language
     * @return
     */
    Integer updateUser(String userId, String clientId, String language);

    /***
     * 更新用户语种
     * @param userId
     * @param language
     * @return
     */
    Integer updateUserLanguage(String userId, String language);

    /***
     * 根据用户id查询
     * @param userId
     * @return
     */
    PushUser selectByUserId(String userId);

    /***
     * 根据客户端id查询
     * @param clientId
     * @return
     */
    PushUser selectByClientId(String clientId);
}
