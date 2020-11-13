package com.blockchain.server.sysconf.mapper;

import com.blockchain.server.sysconf.entity.ContactUs;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface ContactUsMapper extends Mapper<ContactUs> {

    List<ContactUs> listAll(@Param("showStatus") Integer showStatus, @Param("userLocal") String userLocal);


}
