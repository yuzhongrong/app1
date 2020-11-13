package com.blockchain.server.sysconf.mapper;

import com.blockchain.server.sysconf.entity.WgtVersion;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface WgtVersionMapper extends Mapper<WgtVersion> {

    WgtVersion findNewWgtVersion();

    List<WgtVersion> listAll();

}
