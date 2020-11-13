package com.blockchain.server.sysconf.controller;

import com.blockchain.common.base.constant.BaseConstant;
import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.common.base.util.HttpRequestUtil;
import com.blockchain.common.base.util.SSOHelper;
import com.blockchain.server.base.controller.BaseController;
import com.blockchain.server.sysconf.common.constants.ImageConstant;
import com.blockchain.server.sysconf.controller.api.ProjectCenterApi;
import com.blockchain.server.sysconf.controller.api.SysconfImageApi;
import com.blockchain.server.sysconf.controller.api.SysconfNoticeApi;
import com.blockchain.server.sysconf.service.ProjectCenterService;
import com.blockchain.server.sysconf.service.ProjectCenterStarService;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 项目中心
 *
 */
@RestController
@RequestMapping("/projectCenter")
public class ProjectCenterController extends BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(ProjectCenterController.class);

    @Autowired
    private ProjectCenterService projectCenterService;
    @Autowired
    private ProjectCenterStarService projectCenterStarService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 项目列表
     */
    @GetMapping("/list")
    @ApiOperation(value = ProjectCenterApi.ProjectCenterList.METHOD_TITLE_NAME, notes = ProjectCenterApi.ProjectCenterList.METHOD_TITLE_NOTE)
    public ResultDTO list(@ApiParam(ProjectCenterApi.ProjectCenterList.METHOD_API_CLASSIFYID) @RequestParam(required = false, value = "classifyId") String classifyId,
                          @ApiParam(ProjectCenterApi.ProjectCenterList.METHOD_API_DESCR) @RequestParam(required = false, value = "descr") String descr,
                          @ApiParam(ProjectCenterApi.ProjectCenterList.METHOD_API_PAGENUM) @RequestParam(value = "pageNum", defaultValue = BaseConstant.PAGE_DEFAULT_INDEX, required = false) Integer pageNum,
                          @ApiParam(ProjectCenterApi.ProjectCenterList.METHOD_API_PAGESIZE) @RequestParam(value = "pageSize", defaultValue = BaseConstant.PAGE_DEFAULT_SIZE, required = false) Integer pageSize, HttpServletRequest request) {
        PageHelper.startPage(pageNum, pageSize);
        String userOpenId = "";
        try {
            userOpenId = SSOHelper.getUserId(redisTemplate, request);
        }catch (Exception e){
            e.printStackTrace();
        }
        return generatePage(projectCenterService.list(userOpenId,classifyId,descr,ImageConstant.STATUS_SHOW, HttpRequestUtil.getUserLocale(request)));
    }

    /**
    * @Description:
    * @Param: [status, pageNum, pageSize]
    * @return: com.blockchain.common.base.dto.ResultDTO
    * @Author: Liu.sd
    * @Date: 2019/7/13
    */
    @GetMapping("/classifyList")
    @ApiOperation(value = ProjectCenterApi.ClassifyList.METHOD_TITLE_NAME, notes = ProjectCenterApi.ClassifyList.METHOD_TITLE_NOTE)
    public ResultDTO classifyList(HttpServletRequest request) {
        return generatePage(projectCenterService.classifyList(ImageConstant.STATUS_SHOW,HttpRequestUtil.getUserLocale(request)));
    }
    /**
     * 详情
     * @param id
     * @return
     */
    @GetMapping("/selectById")
    @ApiOperation(value = ProjectCenterApi.ProjectCenterSelectById.METHOD_TITLE_NAME, notes = ProjectCenterApi.ProjectCenterSelectById.METHOD_TITLE_NOTE)
    public ResultDTO selectById(
                          @ApiParam(ProjectCenterApi.ProjectCenterSelectById.METHOD_API_ID) @RequestParam("id") String id) {
        return ResultDTO.requstSuccess(projectCenterService.selectById(id));
    }


    /** 
    * @Description: 项目报告列表
    * @Param: [reportType, dateType, projectId, pageNum, pageSize] 
    * @return: com.blockchain.common.base.dto.ResultDTO 
    * @Author: Liu.sd 
    * @Date: 2019/7/13 
    */ 
    @GetMapping("/reportList")
    @ApiOperation(value = ProjectCenterApi.ReportList.METHOD_TITLE_NAME, notes = ProjectCenterApi.ReportList.METHOD_TITLE_NOTE)
    public ResultDTO reportList(@ApiParam(ProjectCenterApi.ReportList.METHOD_API_REPORTTYPE) @RequestParam(required = false, value = "reportType") String reportType,
                                @ApiParam(ProjectCenterApi.ReportList.METHOD_API_DATETYPE) @RequestParam(required = false, value = "dateType") Integer dateType,
                                @ApiParam(ProjectCenterApi.ReportList.METHOD_API_PROJECTID) @RequestParam("projectId") String projectId,
                          @ApiParam(ProjectCenterApi.ReportList.METHOD_API_PAGENUM) @RequestParam(value = "pageNum", defaultValue = BaseConstant.PAGE_DEFAULT_INDEX, required = false) Integer pageNum,
                          @ApiParam(ProjectCenterApi.ReportList.METHOD_API_PAGESIZE) @RequestParam(value = "pageSize", defaultValue = BaseConstant.PAGE_DEFAULT_SIZE, required = false) Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return generatePage(projectCenterService.reportList(reportType,dateType,ImageConstant.STATUS_SHOW,projectId));
    }


    @PostMapping("/star")
    @ApiOperation(value = ProjectCenterApi.Star.METHOD_TITLE_NAME, notes = ProjectCenterApi.Star.METHOD_TITLE_NOTE)
    public ResultDTO star(@ApiParam(ProjectCenterApi.Star.METHOD_API_PROJECTID) @RequestParam("projectId") String projectId,
                          @ApiParam(ProjectCenterApi.Star.METHOD_API_USERID) @RequestParam("userId") String userId) {
        return ResultDTO.requstSuccess(projectCenterStarService.add(projectId,userId));
    }
}
