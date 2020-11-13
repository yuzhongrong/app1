package com.blockchain.server.sysconf.mapper;

import com.blockchain.server.sysconf.dto.ProjectCenterDto;
import com.blockchain.server.sysconf.entity.ProjectCenterClassify;
import com.blockchain.server.sysconf.entity.ProjectCenterInfo;
import com.blockchain.server.sysconf.entity.ProjectCenterReport;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface ProjectCenterMapper extends Mapper<ProjectCenterInfo> {
    List<ProjectCenterDto> list(@Param("userId") String userId,@Param("classifyId") String classifyId,@Param("descr") String descr, @Param("status") String status, @Param("languages") String languages);

    List<ProjectCenterReport> reportList(@Param("reportType") String reportType,@Param("startDate") String startDate,@Param("status") String status, @Param("projectId") String projectId);

    List<ProjectCenterClassify> classifyList(@Param("status") String status, @Param("languages") String languages);
}
