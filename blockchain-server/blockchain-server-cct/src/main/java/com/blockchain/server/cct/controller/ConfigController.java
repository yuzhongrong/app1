package com.blockchain.server.cct.controller;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.server.cct.controller.api.ConfigApi;
import com.blockchain.server.cct.dto.config.ConfigDTO;
import com.blockchain.server.cct.service.ConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(ConfigApi.CONFIG_API)
@RestController
@RequestMapping("/config")
public class ConfigController {

    @Autowired
    private ConfigService configService;

    @ApiOperation(value = ConfigApi.listServiceChargeConfig.METHOD_TITLE_NAME,
            notes = ConfigApi.listServiceChargeConfig.METHOD_TITLE_NOTE)
    @GetMapping("/listServiceCharge")
    public ResultDTO listServiceChargeConfig() {
        List<ConfigDTO> configs = configService.listServiceCharge();
        return ResultDTO.requstSuccess(configs);
    }
}
