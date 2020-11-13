package com.blockchain.server.otc.controller;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.common.base.util.SSOHelper;
import com.blockchain.server.base.controller.BaseController;
import com.blockchain.server.otc.controller.api.ConfigApi;
import com.blockchain.server.otc.service.ConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Api(ConfigApi.CONFIG_API)
@RestController
@RequestMapping("/config")
public class ConfigController extends BaseController {

    @Autowired
    private ConfigService configService;
    @Autowired
    private RedisTemplate redisTemplate;

    @ApiOperation(value = ConfigApi.SelectAutoCancelInterval.METHOD_TITLE_NAME,
            notes = ConfigApi.SelectAutoCancelInterval.METHOD_TITLE_NOTE)
    @GetMapping("/selectAutoCancelInterval")
    public ResultDTO selectAutoCancelInterval() {
        return ResultDTO.requstSuccess(configService.selectAutoCancelInterval());
    }

    @ApiOperation(value = ConfigApi.SelectMarketFreezeCoin.METHOD_TITLE_NAME,
            notes = ConfigApi.SelectMarketFreezeCoin.METHOD_TITLE_NOTE)
    @GetMapping("/selectMarketFreezeCoin")
    public ResultDTO selectMarketFreezeCoin(HttpServletRequest request) {
        SSOHelper.getUser(redisTemplate, request);
        return ResultDTO.requstSuccess(configService.selectMarketFreezeCoin());
    }

    @ApiOperation(value = ConfigApi.SelectMarketFreezeAmount.METHOD_TITLE_NAME,
            notes = ConfigApi.SelectMarketFreezeAmount.METHOD_TITLE_NOTE)
    @GetMapping("/selectMarketFreezeAmount")
    public ResultDTO selectMarketFreezeAmount(HttpServletRequest request) {
        SSOHelper.getUser(redisTemplate, request);
        return ResultDTO.requstSuccess(configService.selectMarketFreezeAmount());
    }
}
