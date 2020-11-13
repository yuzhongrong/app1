package com.blockchain.server.sysconf.mapper;

import com.blockchain.server.sysconf.entity.SystemNotice;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;


/**
 * @author: Liusd
 * @create: 2019-03-04 17:34
 **/
@Repository
public interface SystemNoticeMapper extends Mapper<SystemNotice> {

    List<SystemNotice> selectByStatus(@Param("status")  String status);

    List<SystemNotice> selectByStatusAndLanguages(@Param("status")String status,@Param("languages") String languages);
}