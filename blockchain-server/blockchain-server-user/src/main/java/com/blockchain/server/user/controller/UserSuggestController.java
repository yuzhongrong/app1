package com.blockchain.server.user.controller;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.common.base.util.SSOHelper;
import com.blockchain.server.user.controller.api.UserSuggestApi;
import com.blockchain.server.user.service.UserSuggestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Api(UserSuggestApi.USER_SUGGEST_API)
@RestController
@RequestMapping("/suggest")
public class UserSuggestController {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private UserSuggestService userSuggestService;


    /**
     * 保存用户意见反馈
     *
     * @param suggestion 反馈内容 必传
     * @return
     */
    @ApiOperation(value = UserSuggestApi.Suggest.METHOD_TITLE_NAME, notes = UserSuggestApi.Suggest.METHOD_TITLE_NOTE)
    @RequestMapping(value = "/saveUserSuggest", method = RequestMethod.POST)
    public ResultDTO suggest(@ApiParam(UserSuggestApi.Suggest.METHOD_API_SUGGEST) @RequestParam("suggestion") String suggestion,
                             HttpServletRequest request) {
        String userId = SSOHelper.getUserId(redisTemplate, request);
        userSuggestService.saveUserSuggest(userId, suggestion);
        return ResultDTO.requstSuccess();
    }

}
