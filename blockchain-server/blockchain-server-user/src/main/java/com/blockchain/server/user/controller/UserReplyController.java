package com.blockchain.server.user.controller;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.common.base.util.SSOHelper;
import com.blockchain.server.user.controller.api.UserReplyApi;
import com.blockchain.server.user.service.UserReplyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Api(UserReplyApi.API)
@RestController
@RequestMapping("/reply")
public class UserReplyController {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private UserReplyService userReplyService;


    @ApiOperation(value = UserReplyApi.List.METHOD_TITLE_NAME, notes = UserReplyApi.List.METHOD_TITLE_NOTE)
    @GetMapping("/list")
    public ResultDTO list(HttpServletRequest request) {
        String userId = SSOHelper.getUserId(redisTemplate, request);
        return ResultDTO.requstSuccess(userReplyService.listMine(userId));
    }

}
