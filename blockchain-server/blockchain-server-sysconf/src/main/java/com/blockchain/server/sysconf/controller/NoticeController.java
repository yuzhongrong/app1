package com.blockchain.server.sysconf.controller;

import com.blockchain.common.base.constant.BaseConstant;
import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.common.base.util.HttpRequestUtil;
import com.blockchain.server.base.controller.BaseController;
import com.blockchain.server.sysconf.controller.api.SysconfNoticeApi;
import com.blockchain.server.sysconf.service.SystemNoticeService;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: Liusd
 * @create: 2019-03-25 13:46
 **/
@RestController
@RequestMapping("/systemNotice")
@Api(SysconfNoticeApi.CONTROLLER_API)
public class NoticeController extends BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(NoticeController.class);

    @Autowired
    private SystemNoticeService systemNoticeService;

    /** 
    * @Description: 公告列表 
    * @Param: [request, pageNum, pageSize]
    * @return: com.blockchain.common.base.dto.ResultDTO 
    * @Author: Liu.sd 
    * @Date: 2019/3/25 
    */ 
    @GetMapping("/systemNoticeList")
    @ApiOperation(value = SysconfNoticeApi.SystemNoticeList.METHOD_TITLE_NAME, notes = SysconfNoticeApi.SystemNoticeList.METHOD_TITLE_NOTE)
    public ResultDTO systemNoticeList(@ApiParam(SysconfNoticeApi.SystemNoticeList.METHOD_API_PAGENUM) @RequestParam(value = "pageNum", defaultValue = BaseConstant.PAGE_DEFAULT_INDEX, required = false) Integer pageNum,
                                      @ApiParam(SysconfNoticeApi.SystemNoticeList.METHOD_API_PAGESIZE) @RequestParam(value = "pageSize", defaultValue = BaseConstant.PAGE_DEFAULT_SIZE, required = false) Integer pageSize, HttpServletRequest request) {
        PageHelper.startPage(pageNum, pageSize);
        return generatePage(systemNoticeService.systemNoticeList(HttpRequestUtil.getUserLocale(request)));
    }
}
