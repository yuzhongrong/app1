package com.blockchain.server.sysconf.service.impl;

import com.blockchain.common.base.util.SSOHelper;
import com.blockchain.server.sysconf.dto.ProjectCenterDto;
import com.blockchain.server.sysconf.entity.ProjectCenterClassify;
import com.blockchain.server.sysconf.entity.ProjectCenterInfo;
import com.blockchain.server.sysconf.entity.ProjectCenterReport;
import com.blockchain.server.sysconf.mapper.ProjectCenterMapper;
import com.blockchain.server.sysconf.service.ProjectCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class ProjectCenterServiceImpl implements ProjectCenterService {

    @Autowired
    private ProjectCenterMapper projectCenterMapper;

    @Override
    public List<ProjectCenterDto> list(String userOpenId, String classifyId, String descr, String status, String languages) {
        return projectCenterMapper.list(userOpenId,classifyId,descr,status,languages);
    }

    @Override
    public ProjectCenterInfo selectById(String id) {
        return projectCenterMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<ProjectCenterReport> reportList(String reportType, Integer dateType, String status, String projectId) {
        String startDate = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        if (dateType!=null){
            Calendar c = Calendar.getInstance();
            c.setTime(new Date());
            c.add(Calendar.DATE, 0-dateType);
            Date d = c.getTime();
            startDate = format.format(d);
        }
        return projectCenterMapper.reportList(reportType,startDate, status,projectId);
    }

    @Override
    public List<ProjectCenterClassify> classifyList(String status, String languages) {
        return projectCenterMapper.classifyList(status,languages);
    }

}
