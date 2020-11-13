package com.blockchain.server.imjg.controller;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.common.base.util.JsonUtils;
import com.blockchain.common.base.util.MD5Utils;
import com.blockchain.common.base.util.SSOHelper;
import com.blockchain.server.imjg.common.util.HttpUtils;
import com.blockchain.server.imjg.common.util.JiGuangIMUtils;
import com.blockchain.server.imjg.controller.api.ImjgApi;
import com.blockchain.server.imjg.dto.*;
import com.blockchain.server.imjg.entity.ImjgMessage;
import com.blockchain.server.imjg.service.ImjgMessageLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Api(ImjgApi.JGIM_CONTROLLER_API)
@RestController
@RequestMapping("/jgim")
public class ImjgController {

    @Value("${JiGuang.appkey}")
    private String appkey;

    @Value("${JiGuang.secret}")
    private String secret;

    @Value("${JiGuang.api.address}")
    private String address;

    @Value("${JiGuang.random_str}")
    private String random_str;

    @Value("${JiGuang.api.address_v2}")
    private String address_v2;

    @Value("${JiGuang.p_type}")
    private String p_type;

    @Autowired
    private ImjgMessageLogService imjgMessageLogService;
    @Autowired
    private RedisTemplate redisTemplate;

    @ApiOperation(value = ImjgApi.GetSignature.METHOD_API_NAME, notes = ImjgApi.GetSignature.METHOD_API_NOTE)
    @GetMapping("/getSignature")
    public ResultDTO<JgInitDTO> getSignature(HttpServletRequest request) {
        long timestamp = (new Date()).getTime();
        System.out.println("timestamp>>>" + timestamp);
        String signature = MD5Utils.MD5("appkey=" + appkey + "&timestamp=" + timestamp + "&random_str=" + random_str + "&key=" + secret);
        JgInitDTO jgInitDTO = new JgInitDTO(timestamp, signature, random_str, appkey);
        return ResultDTO.requstSuccess(jgInitDTO);
    }

    /*@ApiOperation(value = ImjgApi.Register.METHOD_API_NAME, notes = ImjgApi.Register.METHOD_API_NOTE)*/
    @PostMapping("/register")
    public ResultDTO register(
            @ApiParam(ImjgApi.Register.METHOD_API_USERNAME) @RequestParam(name = "username") String username,
            @ApiParam(ImjgApi.Register.METHOD_API_PASSWORD) @RequestParam(name = "password") String password,
            HttpServletRequest request) {
        return ResultDTO.requstSuccess(JiGuangIMUtils.doRegister(address + "/v1/admins/", JsonUtils.objectToJson(new JgUserDTO(username, password)), appkey, secret));
    }

    @ApiOperation(value = ImjgApi.GetUserMessages.METHOD_API_NAME, notes = ImjgApi.GetUserMessages.METHOD_API_NOTE)
    @GetMapping("/getUserMessages")
    public ResultDTO<List<MessageLogDTO>> getUserMessages(@ApiParam(ImjgApi.GetUserMessages.METHOD_API_DATAID) @RequestParam(value = "dataId") String dataId,
                                                          @ApiParam(ImjgApi.GetUserMessages.METHOD_API_USERID) @RequestParam(value = "userId") String userId,
                                                          @ApiParam(ImjgApi.GetUserMessages.METHOD_API_APPID) @RequestParam(value = "appId", required = false, defaultValue = "") String appId,
                                                          @ApiParam(ImjgApi.GetUserMessages.METHOD_API_TOKEN) @RequestParam(value = "token", required = false, defaultValue = "") String token,
                                                          @ApiParam(ImjgApi.GetUserMessages.METHOD_API_ORDER) @RequestParam(value = "order", defaultValue = "gmt_create asc ", required = false) String order,
                                                          @ApiParam(ImjgApi.GetUserMessages.METHOD_API_PAGE) @RequestParam(value = "page", defaultValue = "1", required = false) int page,
                                                          @ApiParam(ImjgApi.GetUserMessages.METHOD_API_SIZE) @RequestParam(value = "size", defaultValue = "100", required = false) int size,
                                                          HttpServletRequest request) {
        userId = SSOHelper.getUserId(redisTemplate, request);
        List<MessageLogDTO> logList = imjgMessageLogService.list(p_type, dataId, userId, order, page, size);
        return ResultDTO.requstSuccess(logList);
    }


    @ApiOperation(value = ImjgApi.SaveMessageLog.METHOD_API_NAME, notes = ImjgApi.SaveMessageLog.METHOD_API_NOTE)
    @PostMapping("/saveMessageLog")
    public ResultDTO<String> saveMessageLog(
            @ApiParam(ImjgApi.SaveMessageLog.METHOD_API_DATAID) @RequestParam(value = "dataId") String dataId,
            @ApiParam(ImjgApi.SaveMessageLog.METHOD_API_TARGETUSERID) @RequestParam(value = "targetUserId") String targetUserId,
            @ApiParam(ImjgApi.SaveMessageLog.METHOD_API_FROMUSERID) @RequestParam(value = "fromUserId") String fromUserId,
            @ApiParam(ImjgApi.SaveMessageLog.METHOD_API_MSGTYPE) @RequestParam(value = "msgType", defaultValue = "text") String msgType,
            @ApiParam(ImjgApi.SaveMessageLog.METHOD_API_MSGBODY) @RequestParam(value = "msgBody") String msgBody,
            @ApiParam(ImjgApi.SaveMessageLog.METHOD_API_NODECUE) @RequestParam(value = "nodeCue") int nodeCue,
            HttpServletRequest request) {
        fromUserId = SSOHelper.getUserId(redisTemplate, request);
        imjgMessageLogService.save(p_type, dataId, fromUserId, targetUserId, msgType, msgBody, nodeCue);
        return ResultDTO.requstSuccess();
    }


    @ApiOperation(value = ImjgApi.GetJGAccount.METHOD_API_NAME, notes = ImjgApi.GetJGAccount.METHOD_API_NOTE)
    @GetMapping("/getJGAccount")
    public ResultDTO<JgUserDTO> getJGAccount(HttpServletRequest request,
                                             @ApiParam(ImjgApi.GetJGAccount.METHOD_API_USERID) @RequestParam(value = "userId", required = false, defaultValue = "") String userId,
                                             @ApiParam(ImjgApi.GetJGAccount.METHOD_API_APPID) @RequestParam(value = "appId", required = false, defaultValue = "") String appId,
                                             @ApiParam(ImjgApi.GetJGAccount.METHOD_API_TOKEN) @RequestParam(value = "token", required = false, defaultValue = "") String token) {

        if ("".equals(userId)) {
            userId = SSOHelper.getUserId(redisTemplate, request);
            return ResultDTO.requstSuccess(imjgMessageLogService.getJGAccount(userId));
        } else {
            return ResultDTO.requstSuccess(imjgMessageLogService.getJGAccount(userId));
        }

    }


    @GetMapping("/getResource")
    @ApiOperation(value = ImjgApi.GetResource.METHOD_API_NAME, notes = ImjgApi.GetResource.METHOD_API_NOTE)
    private ResultDTO<JgResourceDTO> getResource(@ApiParam(ImjgApi.GetResource.METHOD_API_MEDIA_ID) @RequestParam(value = "mediaId") String mediaId) {
        Map params = new HashMap();
        params.put("mediaId", mediaId);
        String response = HttpUtils.doJGGet(address + "/v1/resource", params, JiGuangIMUtils.getAuthString(appkey, secret));
        JgResourceDTO resourceDTO = JsonUtils.jsonToPojo(response, JgResourceDTO.class);
        return ResultDTO.requstSuccess(resourceDTO);
    }


}
