package com.blockchain.server.user.service.impl;

import com.blockchain.server.user.common.constants.sql.UserSuggestionConstant;
import com.blockchain.server.user.common.enums.UserEnums;
import com.blockchain.server.user.common.exceprion.UserException;
import com.blockchain.server.user.entity.UserMain;
import com.blockchain.server.user.entity.UserSuggestion;
import com.blockchain.server.user.mapper.UserSuggestMapper;
import com.blockchain.server.user.service.UserMainService;
import com.blockchain.server.user.service.UserSuggestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

@Service
public class UserSuggestServiceImpl implements UserSuggestService {
    @Autowired
    private UserSuggestMapper userSuggestMapper;
    @Autowired
    private UserMainService userMainService;

    @Override
    @Transactional
    public int saveUserSuggest(String userId, String suggestion) {
        if (suggestion.contains("<") || suggestion.contains(">")) {
            throw new UserException(UserEnums.INVALID_STRING);
        }
        Date date = new Date();
        UserMain user = userMainService.selectById(userId);
        UserSuggestion userSuggestion = new UserSuggestion();
        userSuggestion.setId(UUID.randomUUID().toString());
        userSuggestion.setUserId(userId);
        userSuggestion.setUsername(user.getMobilePhone());
        userSuggestion.setTextSuggestion(suggestion);
        userSuggestion.setState(UserSuggestionConstant.USER_SUGGEST_BUILD);//状态（新建:1、已读:2、忽略:3）
        userSuggestion.setCreateTime(date);
        userSuggestion.setModifyTime(date);

        return userSuggestMapper.insertSelective(userSuggestion);
    }

}
