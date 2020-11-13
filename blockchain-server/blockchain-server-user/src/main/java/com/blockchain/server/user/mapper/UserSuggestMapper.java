package com.blockchain.server.user.mapper;

import com.blockchain.server.user.entity.UserSuggestion;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface UserSuggestMapper extends Mapper<UserSuggestion> {


}
