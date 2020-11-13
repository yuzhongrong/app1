package com.blockchain.server.sysconf.service;

import com.blockchain.server.sysconf.entity.ProjectCenterStar;

public interface ProjectCenterStarService {
    /** 
    * @Description: 数量
    * @Param: [projectId] 
    * @return: int 
    * @Author: Liu.sd 
    * @Date: 2019/7/13 
    */ 
    int countByProjectId(String projectId);

    /** 
    * @Description: 新增 
    * @Param: [projectCenterInfo]
    * @return: void 
    * @Author: Liu.sd 
    * @Date: 2019/6/10 
    */ 
    void add(ProjectCenterStar projectCenterStar);

    int add(String projectId, String userId);
}
