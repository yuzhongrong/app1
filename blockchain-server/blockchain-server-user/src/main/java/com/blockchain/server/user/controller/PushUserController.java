package com.blockchain.server.user.controller;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.common.base.util.HttpRequestUtil;
import com.blockchain.common.base.util.SSOHelper;
import com.blockchain.server.user.controller.api.PushUserApi;
import com.blockchain.server.user.service.PushUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Api(PushUserApi.PUSH_USER_API)
@RestController
@RequestMapping("/pushUser")
public class PushUserController {

    @Autowired
    private PushUserService pushUserService;
    @Autowired
    private RedisTemplate redisTemplate;

    @ApiOperation(value = PushUserApi.UpdateUserlangue.METHOD_NAME,
            notes = PushUserApi.UpdateUserlangue.METHOD_NOTE)
    @GetMapping("/updateUserLanguage")
    public ResultDTO updateUserLanguage(HttpServletRequest request) {
        String userId = SSOHelper.getUserId(redisTemplate, request);
        String userLocale = HttpRequestUtil.getUserLocale(request);
        pushUserService.updateUserLanguage(userId, userLocale);
        return ResultDTO.requstSuccess();
    }
}
