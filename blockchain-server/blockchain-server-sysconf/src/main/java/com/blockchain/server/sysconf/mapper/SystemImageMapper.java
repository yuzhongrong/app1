package com.blockchain.server.sysconf.mapper;

import com.blockchain.server.sysconf.entity.SystemImage;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author: Liusd
 * @create: 2019-03-04 17:34
 **/
@Repository
public interface SystemImageMapper extends Mapper<SystemImage> {

    List<SystemImage> selectByTypeAndStatus(@Param("type") String type, @Param("status") String status,
                                            @Param("group") String group,@Param("language") String  language);
}