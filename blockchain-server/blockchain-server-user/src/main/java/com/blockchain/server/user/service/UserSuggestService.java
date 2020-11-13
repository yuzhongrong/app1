package com.blockchain.server.user.service;

public interface UserSuggestService {

    /**
     * 保存用户反馈信息
     *
     * @param userId
     * @param suggestion
     * @return
     */
    int saveUserSuggest(String userId, String suggestion);

}
