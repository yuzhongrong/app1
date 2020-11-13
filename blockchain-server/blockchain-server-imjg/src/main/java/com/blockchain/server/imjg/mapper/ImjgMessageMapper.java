package com.blockchain.server.imjg.mapper;

import com.blockchain.server.imjg.entity.ImjgMessage;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface ImjgMessageMapper extends Mapper<ImjgMessage> {

    void insertOne(ImjgMessage imjgMessage);
}
