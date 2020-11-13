package com.blockchain.server.sysconf.controller;

import com.blockchain.common.base.constant.BaseConstant;
import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.common.base.util.HttpRequestUtil;
import com.blockchain.server.base.controller.BaseController;
import com.blockchain.server.sysconf.common.constants.ImageConstant;
import com.blockchain.server.sysconf.controller.api.SysconfImageApi;
import com.blockchain.server.sysconf.service.SystemImageService;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: Liusd
 * @create: 2019-03-25 13:45
 **/
@RestController
@RequestMapping("/systemImage")
@Api(SysconfImageApi.CONTROLLER_API)
public class ImageController extends BaseController {
    private static final Logger LOG = LoggerFactory.getLogger(ImageController.class);

    @Autowired
    private SystemImageService systemImageService;

    /**
     * @Description: 轮播图列表
     * @Param: [status, pageNum, pageSize]
     * @return: com.blockchain.common.base.dto.ResultDTO
     * @Author: Liu.sd
     * @Date: 2019/3/25
     */
    @GetMapping("/systemImageList")
    @ApiOperation(value = SysconfImageApi.SystemImageList.METHOD_TITLE_NAME, notes = SysconfImageApi.SystemImageList.METHOD_TITLE_NOTE)
    public ResultDTO systemImageList(@ApiParam(SysconfImageApi.SystemImageList.METHOD_API_TYPE) @RequestParam(required = false, value = "type", defaultValue = ImageConstant.TYPE_APP) String type,
                                     @ApiParam(SysconfImageApi.SystemImageList.GROUP) @RequestParam(name = "group", defaultValue = ImageConstant.GROUP_INDEX) String group,
                                     @ApiParam(SysconfImageApi.SystemImageList.METHOD_API_PAGENUM) @RequestParam(value = "pageNum", defaultValue = BaseConstant.PAGE_DEFAULT_INDEX, required = false) Integer pageNum,
                                     @ApiParam(SysconfImageApi.SystemImageList.METHOD_API_PAGESIZE) @RequestParam(value = "pageSize", defaultValue = BaseConstant.PAGE_DEFAULT_SIZE, required = false) Integer pageSize,
                                     HttpServletRequest request) {
        PageHelper.startPage(pageNum, pageSize);
         return generatePage(systemImageService.systemImageList(type, ImageConstant.STATUS_SHOW, group ,HttpRequestUtil.getUserLocale(request)));
    }

}
