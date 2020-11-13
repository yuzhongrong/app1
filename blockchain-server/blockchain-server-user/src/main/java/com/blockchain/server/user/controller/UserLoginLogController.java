package com.blockchain.server.user.controller;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.common.base.util.SSOHelper;
import com.blockchain.server.base.controller.BaseController;
import com.blockchain.server.user.controller.api.UserLoginLogApi;
import com.blockchain.server.user.dto.UserLoginLogDto;
import com.blockchain.server.user.service.UserLoginLogService;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Harvey
 * @date 2019/3/5 15:25
 * @user WIN10
 */
@RestController
@Api(UserLoginLogApi.USER_LOGIN_LOG_API)
@RequestMapping("/userLoginLog")
public class UserLoginLogController extends BaseController {
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private UserLoginLogService userLoginLogService;

    /**
     * 查询用户登录日志
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @ApiOperation(value = UserLoginLogApi.ListUserLoginLog.MATHOD_API_NAME, notes = UserLoginLogApi.ListUserLoginLog.MATHOD_API_NOTE)
    @PostMapping("/listUserLoginLog")
    public ResultDTO listUserLoginLog(@ApiParam(UserLoginLogApi.METHOD_API_PAGE_NUM) @RequestParam(value = "pageNum", required = false, defaultValue = "0") Integer pageNum,
                                      @ApiParam(UserLoginLogApi.METHOD_API_PAGE_SIZE) @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                      HttpServletRequest request) {
        String userId = SSOHelper.getUserId(redisTemplate, request);
        PageHelper.startPage(pageNum, pageSize);
        List<UserLoginLogDto> loginLogs = userLoginLogService.listUserLoginLogByUserId(userId);
        return generatePage(loginLogs);
    }

    @GetMapping("/checkLogin")
    public ResultDTO checkLogin( HttpServletRequest request) {
        String userId = SSOHelper.getUserId(redisTemplate, request);
        return ResultDTO.requstSuccess();
    }

}
