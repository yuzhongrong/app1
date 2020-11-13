package com.blockchain.server.otc.controller;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.common.base.util.SSOHelper;
import com.blockchain.server.otc.common.constant.MarketApplyConstants;
import com.blockchain.server.otc.controller.api.MarketApplyApi;
import com.blockchain.server.otc.service.MarketApplyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Api(MarketApplyApi.MARKET_APPLY_API)
@RestController
@RequestMapping("/marketApply")
public class MarketApplyController {

    @Autowired
    private MarketApplyService marketApplyService;
    @Autowired
    private RedisTemplate redisTemplate;

    @ApiOperation(value = MarketApplyApi.CancelApply.METHOD_TITLE_NAME,
            notes = MarketApplyApi.CancelApply.METHOD_TITLE_NOTE)
    @PostMapping("/cancelApply")
    public ResultDTO cancelApply(HttpServletRequest request) {
        String userId = getUserId(request);
        marketApplyService.applyMarket(userId, MarketApplyConstants.CANCEL);
        return ResultDTO.requstSuccess();
    }

    @ApiOperation(value = MarketApplyApi.MarketApply.METHOD_TITLE_NAME,
            notes = MarketApplyApi.MarketApply.METHOD_TITLE_NOTE)
    @PostMapping("/marketApply")
    public ResultDTO marketApply(HttpServletRequest request) {
        String userId = getUserId(request);
        marketApplyService.applyMarket(userId, MarketApplyConstants.MARKET);
        return ResultDTO.requstSuccess();
    }

    /***
     * 获取用户id
     * @param request
     * @return
     */
    private String getUserId(HttpServletRequest request) {
        return SSOHelper.getUserId(redisTemplate, request);
    }
}
