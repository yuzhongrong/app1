package com.blockchain.server.sysconf.controller;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.server.sysconf.controller.api.AboutUsApi;
import com.blockchain.server.sysconf.entity.AboutUs;
import com.blockchain.server.sysconf.service.AboutUsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(AboutUsApi.ABOUTUS_API)
@RestController
@RequestMapping("/aboutUs")
public class AboutUsController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AboutUsController.class);

    @Autowired
    private AboutUsService aboutUsService;

    /**
     * 查询关于我们信息 （客户端）
     * @return
     */
    @ApiOperation(value = AboutUsApi.FINDABOUTUS.METHOD_TITLE_NAME, notes = AboutUsApi.FINDABOUTUS.METHOD_TITLE_NOTE)
    @RequestMapping(value = "/findAboutUs", method = RequestMethod.POST)
    public ResultDTO findAboutUs(@ApiParam(AboutUsApi.FINDABOUTUS.METHOD_API_LANGUAGES) @RequestParam("languages") String languages) {
        AboutUs aboutUs = aboutUsService.findNewestAboutUs(languages);
        return ResultDTO.requstSuccess(aboutUs);
    }

}
