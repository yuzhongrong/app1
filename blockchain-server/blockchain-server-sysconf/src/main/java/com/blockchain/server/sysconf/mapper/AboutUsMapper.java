package com.blockchain.server.sysconf.mapper;

import com.blockchain.server.sysconf.entity.AboutUs;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface AboutUsMapper extends Mapper<AboutUs> {

    List<AboutUs> listAllOrderByCreateTimeDesc();

    AboutUs findNewestAboutUs(@Param("languages") String languages);

}
