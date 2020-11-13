package com.blockchain.server.sysconf.controller;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.server.sysconf.controller.api.VersionApi;
import com.blockchain.server.sysconf.entity.Version;
import com.blockchain.server.sysconf.service.VersionService;
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

import java.util.Map;


@Api(VersionApi.VERSION_API)
@RestController
@RequestMapping("/version")
public class VersionController {

    private static final Logger LOGGER = LoggerFactory.getLogger(VersionController.class);

    @Autowired
    private VersionService versionService;
    /**
     * 根据系统类型查询最新app版本信息
     * @param device
     * @return
     */
    @ApiOperation(value = VersionApi.FINDNEWVERSION.METHOD_TITLE_NAME, notes = VersionApi.FINDNEWVERSION.METHOD_TITLE_NOTE)
    @RequestMapping(value = "/findNewVersion", method = RequestMethod.GET)
    public ResultDTO findNewVersion(@ApiParam(VersionApi.FINDNEWVERSION.METHOD_API_DEVICE) @RequestParam("device") String device){
        Version version = versionService.findNewVersion(device);
        return ResultDTO.requstSuccess(version);
    }

    /**
     * 查询所有系统最新的版本信息
     * @return
     */
    @ApiOperation(value = VersionApi.FINDNEWVERSIONALL.METHOD_TITLE_NAME, notes = VersionApi.FINDNEWVERSIONALL.METHOD_TITLE_NOTE)
    @RequestMapping(value = "/findNewVersionAll", method = RequestMethod.GET)
    public ResultDTO findNewVersionAll(){
        Map<String, Version> map = versionService.findNewVersionAll();
        return ResultDTO.requstSuccess(map);
    }

}
