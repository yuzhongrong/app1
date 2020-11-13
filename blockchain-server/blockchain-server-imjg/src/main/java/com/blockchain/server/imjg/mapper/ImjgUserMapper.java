package com.blockchain.server.imjg.mapper;

import com.blockchain.server.imjg.entity.ImjgUser;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface ImjgUserMapper extends Mapper<ImjgUser> {

    ImjgUser selectByUserId(String userId);

    void insertOne(ImjgUser imjgUser);
}
