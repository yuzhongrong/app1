package com.blockchain.server.imjg.inner;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.common.base.util.JsonUtils;
import com.blockchain.server.imjg.common.util.HttpUtils;
import com.blockchain.server.imjg.common.util.JiGuangIMUtils;
import com.blockchain.server.imjg.controller.api.ImjgApi;
import com.blockchain.server.imjg.dto.*;
import com.blockchain.server.imjg.entity.ImjgUser;
import com.blockchain.server.imjg.inner.api.ImjgInnerApi;
import com.blockchain.server.imjg.service.ImjgMessageLogService;
import com.blockchain.server.imjg.service.ImjgUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(ImjgInnerApi.IMJG_INNER_API)
@RestController
@RequestMapping("/inner")
public class ImjgInnerController {


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
    private ImjgUserService imjgUserService;

    @ApiOperation(value = ImjgInnerApi.SendSingleMsg.METHOD_API_NAME, notes = ImjgInnerApi.SendSingleMsg.METHOD_API_NOTE)
    @PostMapping("/sendSingleMsg")
    public ResultDTO sendSingleMsg(@ApiParam(ImjgInnerApi.SendSingleMsg.METHOD_API_PTYPE)@RequestParam(value = "pType") String pType,
                                   @ApiParam(ImjgInnerApi.SendSingleMsg.METHOD_API_DATAID)@RequestParam(value = "dataId") String dataId,
                                   @ApiParam(ImjgInnerApi.SendSingleMsg.METHOD_API_MSGTYPE)@RequestParam(value = "msgType") String msgType,
                                   @ApiParam(ImjgInnerApi.SendSingleMsg.METHOD_API_FUSERID)@RequestParam(value = "fuserId") String fuserId,
                                   @ApiParam(ImjgInnerApi.SendSingleMsg.METHOD_API_TUSERID)@RequestParam(value = "tuserId") String tuserId,
                                   @ApiParam(ImjgInnerApi.SendSingleMsg.METHOD_API_MSGBODYJSON)@RequestParam(value = "msgBodyJson") String msgBodyJson,
                                   @ApiParam(ImjgInnerApi.SendSingleMsg.METHOD_API_NODECUE)@RequestParam(value = "nodeCue") int nodeCue){

        try{
            JgMessageBodyDTO jgMessageBodyDTO = JsonUtils.jsonToPojo(msgBodyJson,JgMessageBodyDTO.class);
            String fId = imjgMessageLogService.getJGAccount(fuserId).getUsername();
            String tId = imjgMessageLogService.getJGAccount(tuserId).getUsername();
            JgMessageDTO jgMessageDTO = new JgMessageDTO(msgType,fId,tId,jgMessageBodyDTO);
            jgMessageDTO.setFrom_name("nodeCue");
            String json = JsonUtils.objectToJson(jgMessageDTO);
            String result = HttpUtils.doJiGuangPost(address+"/v1/messages",json, JiGuangIMUtils.getAuthString(appkey,secret));
            imjgMessageLogService.save(pType,dataId,fId,tId,msgType,JsonUtils.objectToJson(jgMessageBodyDTO.toImjgMessage()),nodeCue);
            return ResultDTO.requstSuccess(result);
        }catch (Exception e){
            return ResultDTO.requstSuccess();
        }

    }


    @ApiOperation(value = ImjgInnerApi.GetUserStatFromJG.METHOD_API_NAME, notes = ImjgInnerApi.GetUserStatFromJG.METHOD_API_NOTE)
    @GetMapping("/getUserStatFromJG")
    public ResultDTO getUserStatFromJG(@ApiParam(ImjgInnerApi.GetUserStatFromJG.METHOD_API_USERID)@RequestParam(value = "userId") String userId){
        JgUserDTO jgUserDTO = imjgMessageLogService.getJGAccount(userId);
        String userName = jgUserDTO.getUsername();
        Map params = new HashMap();
        String result = HttpUtils.doJGGet(address+"/v1/users/"+userName+"/userstat",params, JiGuangIMUtils.getAuthString(appkey,secret));
        JgUserStatDTO jgUserStatDTO = JsonUtils.jsonToPojo(result, JgUserStatDTO.class);
        return ResultDTO.requstSuccess(jgUserStatDTO);
    }

    @ApiOperation(value = ImjgInnerApi.GetJGAccount.METHOD_API_NAME, notes = ImjgInnerApi.GetJGAccount.METHOD_API_NOTE)
    @GetMapping("/getJGAccount")
    public ResultDTO getJGAccount(HttpServletRequest request,
                                  @ApiParam(ImjgInnerApi.GetJGAccount.METHOD_API_USERID)@RequestParam(value = "userId",required = false,defaultValue = "") String userId) {

        if("".equals(userId)){
            return ResultDTO.requstSuccess(imjgMessageLogService.getJGAccount(userId));
        }else{
            return ResultDTO.requstSuccess(imjgMessageLogService.getJGAccount(userId));
        }

    }

    @ApiOperation(value = ImjgInnerApi.GetUserMessages.METHOD_API_NAME, notes = ImjgInnerApi.GetUserMessages.METHOD_API_NOTE)
    @GetMapping("/getUserMessages")
    public ResultDTO<List<MessageLogDTO>> getUserMessages(@ApiParam(ImjgInnerApi.GetUserMessages.METHOD_API_DATAID)@RequestParam(value = "dataId") String dataId,
                                     @ApiParam(ImjgInnerApi.GetUserMessages.METHOD_API_PTYPE)@RequestParam(value = "pType") String pType,

                                   @ApiParam(ImjgInnerApi.GetUserMessages.METHOD_API_USERID)@RequestParam(value = "userId") String userId,
                                     @ApiParam(ImjgInnerApi.GetUserMessages.METHOD_API_ORDER)@RequestParam(value = "order",defaultValue = "gmt_create asc ",required = false) String order,
                                     @ApiParam(ImjgInnerApi.GetUserMessages.METHOD_API_PAGE)@RequestParam(value = "page",defaultValue = "1",required = false) int page,
                                     @ApiParam(ImjgInnerApi.GetUserMessages.METHOD_API_SIZE)@RequestParam(value = "size",defaultValue = "20",required = false) int size,
                                     HttpServletRequest request) {
        List<MessageLogDTO> logList = imjgMessageLogService.list(pType,dataId,userId,order,page,size);
        return ResultDTO.requstSuccess(logList);
    }

    @ApiOperation(value = ImjgInnerApi.GetUserMessagesFromJG.METHOD_API_NAME, notes = ImjgInnerApi.GetUserMessagesFromJG.METHOD_API_NOTE)
    @GetMapping("/getUserMessagesFromJG")
    public ResultDTO getUserMessagesFromJG(@ApiParam(ImjgInnerApi.GetUserMessagesFromJG.METHOD_API_USERID)@RequestParam(value = "userId") String userId,
                                     @ApiParam(ImjgInnerApi.GetUserMessagesFromJG.METHOD_API_BEGIN_TIME)@RequestParam(value = "begin_time",required = false,defaultValue = "") String begin_time,
                                     @ApiParam(ImjgInnerApi.GetUserMessagesFromJG.METHOD_API_END_TIME)@RequestParam(value = "end_time",required = false,defaultValue = "") String end_time,
                                     @ApiParam(ImjgInnerApi.GetUserMessagesFromJG.METHOD_API_CURSOR)@RequestParam(value = "cursor",required = false,defaultValue = "") String cursor)throws Exception{
        JgUserDTO jgUserDTO = imjgMessageLogService.getJGAccount(userId);
        String userName = jgUserDTO.getUsername();
        Map params = new HashMap();
        String result = "";
        end_time = URLEncoder.encode(end_time,"utf-8");
        begin_time = URLEncoder.encode(begin_time,"utf-8");
        if("".equals(cursor)){
            result = HttpUtils.doJGGet(address_v2+"/users/"+userName+"/messages?count=1000&begin_time="+begin_time+"&end_time="+end_time,params,JiGuangIMUtils.getAuthString(appkey,secret));
        }else{
            result = HttpUtils.doJGGet(address_v2+"/users/"+userName+"/messages?cursor="+cursor,params,JiGuangIMUtils.getAuthString(appkey,secret));
        }
        JgMessageLogDTO jgMessageLogDTO = JsonUtils.jsonToPojo(result,JgMessageLogDTO.class);
        if(jgMessageLogDTO == null){
            return ResultDTO.requstSuccess(JsonUtils.jsonToPojo(result,JgResponse.class));
        }
        return ResultDTO.requstSuccess(jgMessageLogDTO);
    }

    @ApiOperation(value = ImjgInnerApi.GetGroupMessagesFromJG.METHOD_API_NAME, notes = ImjgInnerApi.GetGroupMessagesFromJG.METHOD_API_NOTE)
    @GetMapping("/getGroupMessagesFromJG")
    public ResultDTO getGroupMessagesFromJG(@ApiParam(ImjgInnerApi.GetGroupMessagesFromJG.METHOD_API_GID)@RequestParam(value = "gId") long gId,
                                           @ApiParam(ImjgInnerApi.GetGroupMessagesFromJG.METHOD_API_BEGIN_TIME)@RequestParam(value = "begin_time",required = false,defaultValue = "") String begin_time,
                                           @ApiParam(ImjgInnerApi.GetGroupMessagesFromJG.METHOD_API_END_TIME)@RequestParam(value = "end_time",required = false,defaultValue = "") String end_time,
                                           @ApiParam(ImjgInnerApi.GetGroupMessagesFromJG.METHOD_API_CURSOR)@RequestParam(value = "cursor",required = false,defaultValue = "") String cursor)throws Exception{

        Map params = new HashMap();
        String result = "";
        end_time = URLEncoder.encode(end_time,"utf-8");
        begin_time = URLEncoder.encode(begin_time,"utf-8");
        if("".equals(cursor)){
            result = HttpUtils.doJGGet(address_v2+"/groups/"+gId+"/messages?count=1000&begin_time="+begin_time+"&end_time="+end_time,params,JiGuangIMUtils.getAuthString(appkey,secret));
        }else{
            result = HttpUtils.doJGGet(address_v2+"/groups/"+gId+"/messages?cursor="+cursor,params,JiGuangIMUtils.getAuthString(appkey,secret));
        }
        JgMessageLogDTO jgMessageLogDTO = JsonUtils.jsonToPojo(result,JgMessageLogDTO.class);
        if(jgMessageLogDTO == null){
            return ResultDTO.requstSuccess(JsonUtils.jsonToPojo(result,JgResponse.class));
        }
        return ResultDTO.requstSuccess(jgMessageLogDTO);
    }


    @ApiOperation(value = ImjgInnerApi.GetRoomMessagesFromJG.METHOD_API_NAME, notes = ImjgInnerApi.GetRoomMessagesFromJG.METHOD_API_NOTE)
    @GetMapping("/getRoomMessagesFromJG")
    public ResultDTO getRoomMessagesFromJG(@ApiParam(ImjgInnerApi.GetRoomMessagesFromJG.METHOD_API_CHATROOMID)@RequestParam(value = "chatroomid") long chatroomid,
                                            @ApiParam(ImjgInnerApi.GetRoomMessagesFromJG.METHOD_API_BEGIN_TIME)@RequestParam(value = "begin_time",required = false,defaultValue = "") String begin_time,
                                            @ApiParam(ImjgInnerApi.GetRoomMessagesFromJG.METHOD_API_END_TIME)@RequestParam(value = "end_time",required = false,defaultValue = "") String end_time,
                                            @ApiParam(ImjgInnerApi.GetRoomMessagesFromJG.METHOD_API_CURSOR)@RequestParam(value = "cursor",required = false,defaultValue = "") String cursor)throws Exception{
        String result = "";
        end_time = URLEncoder.encode(end_time,"utf-8");
        begin_time = URLEncoder.encode(begin_time,"utf-8");
        if("".equals(cursor)){
            result = HttpUtils.doJGGet(address_v2+"/chatrooms/"+chatroomid+"/messages?count=1000&begin_time="+begin_time+"&end_time="+end_time,null,JiGuangIMUtils.getAuthString(appkey,secret));
        }else{
            result = HttpUtils.doJGGet(address_v2+"/chatrooms/"+chatroomid+"/messages?cursor="+cursor,null,JiGuangIMUtils.getAuthString(appkey,secret));
        }
        JgMessageLogDTO jgMessageLogDTO = JsonUtils.jsonToPojo(result,JgMessageLogDTO.class);
        if(jgMessageLogDTO == null){
            return ResultDTO.requstSuccess(JsonUtils.jsonToPojo(result,JgResponse.class));
        }
        return ResultDTO.requstSuccess(jgMessageLogDTO);
    }


}
