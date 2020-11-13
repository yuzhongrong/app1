package com.blockchain.server.user.controller;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.common.base.util.SSOHelper;
import com.blockchain.server.user.controller.api.AuthApi;
import com.blockchain.server.user.controller.api.UserApi;
import com.blockchain.server.user.service.BaiDuAuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * @Datetime: 2020/5/18   19:17
 * @Author: Xia rong tao
 * @title   身份认证控制层
 */
@RestController
@Api(AuthApi.AUTH_API)
@RequestMapping("/auth")
public class AuthController {


    @Autowired
    private  BaiDuAuthService baiDuAuthService;



    @Autowired
    private RedisTemplate redisTemplate;


    /**
     * 身份证+ 姓名 认证
     * @param request
     * @param name
     * @param idCardNumber
     * @param type
     * @return
     */
    @ResponseBody
    @PostMapping("userLowAuth")
    @ApiOperation(value = AuthApi.userLowAuth.METHOD_NAME)
    public ResultDTO identityAuth(HttpServletRequest request,
                               @ApiParam(AuthApi.userLowAuth.NAME) @RequestParam(name = "name", required = true)String name ,
                               @ApiParam(AuthApi.userLowAuth.ID_CARD_NUMBER)@RequestParam(name = "idCardNumber", required = true)String  idCardNumber,
                               @ApiParam(AuthApi.userLowAuth.TYPE)@RequestParam(name = "type", required = false)String  type){
        String userId = SSOHelper.getUserId(redisTemplate, request);
        return  baiDuAuthService.userLowAuth(  userId ,name ,  idCardNumber,type);
    }
    /**
     * 初级认证，（识别图片信息）
     * @param imgs
     * @return
     */
    @ApiOperation(value = UserApi.CommitPrimaryAuthApply.METHOD_NAME, notes = UserApi.CommitPrimaryAuthApply.METHOD_NOTE)
    @RequestMapping(value = "/saveIdcardVali", method = RequestMethod.POST)
    public ResultDTO commitPrimaryAuthApply(HttpServletRequest request,
                                            @ApiParam(UserApi.CommitPrimaryAuthApply.METHOD_API_IMGS) @RequestParam("imgs") String imgs ) {
        //获取用户登录信息
        String userId = SSOHelper.getUserId(redisTemplate, request);
        baiDuAuthService.insertBasicAuth(userId,imgs);
        return ResultDTO.requstSuccess();
    }


    /**
     * 获取语音会话id
     */
    @RequestMapping(value = "/voiceSessionId", method = RequestMethod.POST)
    public ResultDTO voiceSessionId( ) {

        return ResultDTO.requstSuccess( baiDuAuthService.voiceSessionId());
    }



     /**
     * 视频活体解析
     */
    @RequestMapping(value = "/videoDetection", method = RequestMethod.POST)
    public ResultDTO videoDetection(HttpServletRequest request, @RequestParam(value = "file") MultipartFile file ,String sessionId) {
         String userId = SSOHelper.getUserId(redisTemplate, request);

        return  baiDuAuthService.insertHighAuth(userId,file,sessionId);

    }

}
