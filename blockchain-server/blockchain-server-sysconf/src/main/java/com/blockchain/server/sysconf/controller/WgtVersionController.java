package com.blockchain.server.sysconf.controller;


import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.server.sysconf.controller.api.WgtVersionApi;
import com.blockchain.server.sysconf.service.WgtVersionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@Api(WgtVersionApi.WGT_VERSION_API)
@RestController
@RequestMapping("/wgtVersion")
public class WgtVersionController {

    private static final Logger LOGGER = LoggerFactory.getLogger(WgtVersionController.class);

    @Autowired
    private WgtVersionService wgtVersionService;

    @ApiOperation(value = WgtVersionApi.FINDNEWWGTVERSION.METHOD_TITLE_NAME, notes = WgtVersionApi.FINDNEWWGTVERSION.METHOD_TITLE_NOTE)
    @RequestMapping(value = "/findNewWgtVersion", method = RequestMethod.GET)
    public ResultDTO findNewWgtVersion() {
        return ResultDTO.requstSuccess(wgtVersionService.findNewWgtVersion());
    }

}
