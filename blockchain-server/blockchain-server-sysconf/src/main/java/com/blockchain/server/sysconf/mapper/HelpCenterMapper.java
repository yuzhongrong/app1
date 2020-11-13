package com.blockchain.server.sysconf.mapper;

import com.blockchain.server.sysconf.entity.HelpCenter;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 帮助中心 数据层
 * 
 * @author ruoyi
 * @date 2018-10-30
 */
@Repository
public interface HelpCenterMapper extends Mapper<HelpCenter> {

	List<HelpCenter> selectHelpCenterForApp(@Param("showStatus") Integer showStatus,@Param("userLocal") String userLocale);

	List<HelpCenter> selectHelpCenterForPc(@Param("showStatus") Integer showStatus, @Param("title") String title,@Param("userLocal") String userLocale);

	String selectContentById(@Param("id") String id);
}