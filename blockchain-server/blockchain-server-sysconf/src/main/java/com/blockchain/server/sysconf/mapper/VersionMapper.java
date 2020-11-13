package com.blockchain.server.sysconf.mapper;

import com.blockchain.server.sysconf.entity.Version;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface VersionMapper extends Mapper<Version> {

    Version findNewVersion(@Param("device") String device);


    List<Version> listAll(@Param("device") String device);

}
