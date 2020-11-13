package com.blockchain.server.sysconf.mapper;

import com.blockchain.server.sysconf.dto.NewsDTO;
import com.blockchain.server.sysconf.dto.NewsQueryConditionDTO;
import com.blockchain.server.sysconf.entity.News;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.Date;
import java.util.List;

@Repository
public interface NewsMapper extends BaseMapper<News> {
    /**
     * 用于前端展示
     * @param condition 查询条件
     * @return
     */

    List<News> listAll(NewsQueryConditionDTO condition);

    List<NewsDTO> listNewsDTO(@Param("type") Integer type,@Param("languages") String languages,@Param("beginTime") Date beginTime,@Param("endTime") Date endTime);
}
