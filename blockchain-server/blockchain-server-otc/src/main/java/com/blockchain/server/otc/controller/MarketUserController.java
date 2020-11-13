package com.blockchain.server.otc.controller;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.common.base.util.SSOHelper;
import com.blockchain.server.otc.controller.api.MarketUserApi;
import com.blockchain.server.otc.service.MarketUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Api(MarketUserApi.MARKET_USER_API)
@RestController
@RequestMapping("/marketUser")
public class MarketUserController {

    @Autowired
    private MarketUserService marketUserService;
    @Autowired
    private RedisTemplate redisTemplate;

    @ApiOperation(value = MarketUserApi.GetStatusByUserId.METHOD_TITLE_NAME,
            notes = MarketUserApi.GetStatusByUserId.METHOD_TITLE_NOTE)
    @GetMapping("/getStatusByUserId")
    public ResultDTO getStatusByUserId(HttpServletRequest request) {
        String userId = SSOHelper.getUserId(redisTemplate, request);
        String marketUserStatus = marketUserService.getMarketUserStatus(userId);
        return ResultDTO.requstSuccess(marketUserStatus);
    }
}
