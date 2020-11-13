package com.blockchain.server.sysconf.controller;


import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.common.base.util.HttpRequestUtil;
import com.blockchain.server.sysconf.controller.api.HelpCenterApi;
import com.blockchain.server.sysconf.service.HelpCenterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Api(HelpCenterApi.HELPCENTER_API)
@RestController
@RequestMapping("/helpCenter")
public class HelpCenterController {


    @Autowired
    private HelpCenterService helpCenterService;

    /**
     * 查询帮助中心列表，手机端
     * @return
     */
    @ApiOperation(value = HelpCenterApi.SELECTHELPCENTERFORAPP.METHOD_TITLE_NAME, notes = HelpCenterApi.SELECTHELPCENTERFORAPP.METHOD_TITLE_NOTE)
    @RequestMapping(value = "/selectHelpCenterForApp", method = RequestMethod.POST)
    public ResultDTO selectHelpCenterForApp(HttpServletRequest request) {
        String userLocale = HttpRequestUtil.getUserLocale(request);
        return ResultDTO.requstSuccess(helpCenterService.selectHelpCenterForApp(userLocale));
    }
    /**
     * 查询帮助中心列表，PC端
     * @return
     */
    @ApiOperation(value = HelpCenterApi.SELECTHELPCENTERFORAPP.METHOD_TITLE_NAME, notes = HelpCenterApi.SELECTHELPCENTERFORAPP.METHOD_TITLE_NOTE)
    @RequestMapping(value = "/selectHelpCenterForPc", method = RequestMethod.POST)
    public ResultDTO selectHelpCenterForPc(@ApiParam(HelpCenterApi.SELECTHELPCENTERFORPC.METHOD_API_TITLE) @RequestParam(required = false,value = "title") String title,HttpServletRequest request) {
        String userLocale = HttpRequestUtil.getUserLocale(request);
        return ResultDTO.requstSuccess(helpCenterService.selectHelpCenterForPc(title,userLocale));
    }

    /**
     * 根据id查询帮助中心内容
     * @return
     */
    @ApiOperation(value = HelpCenterApi.SELECTCONTENTBYID.METHOD_TITLE_NAME, notes = HelpCenterApi.SELECTCONTENTBYID.METHOD_TITLE_NOTE)
    @RequestMapping(value = "/selectContentById", method = RequestMethod.POST)
    public ResultDTO selectContentById(@ApiParam(HelpCenterApi.SELECTCONTENTBYID.METHOD_API_ID) @RequestParam("id") String id) {
        return ResultDTO.requstSuccess(helpCenterService.selectContentById(id));
    }

}
