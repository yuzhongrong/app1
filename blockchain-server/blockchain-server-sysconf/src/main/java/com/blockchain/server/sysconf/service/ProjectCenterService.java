package com.blockchain.server.sysconf.service;

import com.blockchain.server.sysconf.dto.ProjectCenterDto;
import com.blockchain.server.sysconf.entity.ProjectCenterClassify;
import com.blockchain.server.sysconf.entity.ProjectCenterInfo;
import com.blockchain.server.sysconf.entity.ProjectCenterReport;

import java.util.List;

public interface ProjectCenterService {
    /** 
    * @Description:
    * @Param: [status, languages]
    * @return: java.util.List<com.blockchain.server.sysconf.dto.ProjectCenterDto>
    * @Author: Liu.sd 
    * @Date: 2019/7/5 
    */ 
    List<ProjectCenterDto> list(String userOpenId, String classifyId, String descr, String status, String languages);

    ProjectCenterInfo selectById(String id);

    List<ProjectCenterReport> reportList(String reportType, Integer dateType, String status, String projectId);

    List<ProjectCenterClassify> classifyList(String status, String languages);
}
