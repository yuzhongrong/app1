package com.blockchain.server.imjg.mapper;

import com.blockchain.server.imjg.entity.ImjgMessageLog;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

@Repository
public interface ImjgMessageLogMapper extends Mapper<ImjgMessageLog> {

    List<ImjgMessageLog> listBy(Map params);

    int insertOne(ImjgMessageLog imjgMessageLog);


}
