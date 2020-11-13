package com.blockchain.server.imjg.service.impl;

import com.blockchain.common.base.util.DateTimeUtils;
import com.blockchain.common.base.util.JsonUtils;
import com.blockchain.server.imjg.common.enums.MessageLogEnums;
import com.blockchain.server.imjg.common.util.JiGuangIMUtils;
import com.blockchain.server.imjg.dto.*;
import com.blockchain.server.imjg.entity.ImjgMessage;
import com.blockchain.server.imjg.entity.ImjgMessageLog;
import com.blockchain.server.imjg.entity.ImjgUser;
import com.blockchain.server.imjg.feign.UserFeign;
import com.blockchain.server.imjg.mapper.ImjgMessageLogMapper;
import com.blockchain.server.imjg.mapper.ImjgMessageMapper;
import com.blockchain.server.imjg.service.ImjgMessageLogService;
import com.blockchain.server.imjg.service.ImjgUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class ImjgMessageLogServiceImpl implements ImjgMessageLogService {

    @Autowired
    private ImjgMessageLogMapper imjgMessageLogMapper;

    @Autowired
    private ImjgMessageMapper imjgMessageMapper;

    @Autowired
    private ImjgUserService imjgUserService;

    @Autowired
    private UserFeign userFeign;

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

    private static int USER_EXIST = 899001;


    @Override
    public void save(ImjgMessageLog imjgMessageLog) {

        imjgMessageLogMapper.insert(imjgMessageLog);
    }

    @Override
    public List<MessageLogDTO> list(String pType, String dataId, String userId, String order, int page, int size) {
        Map params = new HashMap();
        params.put("pType",pType);
        params.put("dataId",dataId);
        params.put("userId",userId);
        params.put("status","NORMAL");
        params.put("order",order);
        params.put("start",(page-1)*size);
        params.put("size",size);

        params.put("chatLog",1);
        List<ImjgMessageLog> logList = imjgMessageLogMapper.listBy(params);

        List<MessageLogDTO> messageLogDTOS = new ArrayList<>();

        for(ImjgMessageLog log:logList){
            MessageLogDTO messageLogDTO = new MessageLogDTO(log);
            ImjgMessage imjgMessageDB = imjgMessageMapper.selectByPrimaryKey(log.getMsgId());
            System.out.println(imjgMessageDB.getText());
            messageLogDTO.setMessageDTO(new MessageDTO(imjgMessageDB));
            System.out.println(log.getFromId());
            UserDTO fromUser = userFeign.getUserById(log.getFromId()).getData();
            System.out.println("userDTO>>>"+fromUser);

            messageLogDTO.setFromUser(new JgUserInfoDTO(fromUser));

            Date date = DateTimeUtils.parse(log.getGmtCreate());
            messageLogDTO.setGmtCreate(DateTimeUtils.format(date,DateTimeUtils.DATE_TIME_FORMAT));
            messageLogDTOS.add(messageLogDTO);

        }

        return messageLogDTOS;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(String pType,String dataId,String fromUserId,String targetUserId,String msgType,String msgBody,int nodeCue) {

        ImjgMessage imjgMessage = JsonUtils.jsonToPojo(msgBody, ImjgMessage.class);
        imjgMessageMapper.insertOne(imjgMessage);

        ImjgMessageLog imjgMessageLog = new ImjgMessageLog();
        imjgMessageLog.setPType(pType);
        imjgMessageLog.setDataId(dataId);
        imjgMessageLog.setFromId(fromUserId);
        imjgMessageLog.setTargetId(targetUserId);
        imjgMessageLog.setMsgType(msgType);
        imjgMessageLog.setNodeCue(nodeCue);
        imjgMessageLog.setMsgId(imjgMessage.getId());
        imjgMessageLog.setStatus(MessageLogEnums.STATUS_NORMAL.getCode());

        imjgMessageLogMapper.insertOne(imjgMessageLog);
    }

    @Override
    public JgUserDTO getJGAccount(String userId) {
        if(userId == null&&"".equals(userId)){
            return null;
        }
        ImjgUser imjgUser = imjgUserService.get(userId);
        /*该用户在系统不存在极光账号*/
        if(imjgUser == null){
            String response = JiGuangIMUtils.doRegister(address+"/v1/admins/",JsonUtils.objectToJson(new JgUserDTO(userId,userId)),appkey,secret);
            if("".equals(response)){
                imjgUserService.save(new ImjgUser(0,userId,userId,userId,null,null));
                JgUserDTO jgUserDTO = new JgUserDTO(userId,userId);
                jgUserDTO.setUserId(userId);
                return jgUserDTO;
            }else{
                JgResponse jgResponse = JsonUtils.jsonToPojo(response,JgResponse.class);
                //user exist
                if(jgResponse.getError().getCode() == USER_EXIST){
                    String rs = UUID.randomUUID().toString();
                    JiGuangIMUtils.doRegister(address+"/v1/admins/",JsonUtils.objectToJson(new JgUserDTO(rs,userId)),appkey,secret);
                    imjgUserService.save(new ImjgUser(0,userId,rs,userId,null,null));
                    JgUserDTO jgUserDTO =  new JgUserDTO(rs,userId);
                    jgUserDTO.setUserId(userId);
                    return jgUserDTO;
                }
                return null;
            }

        }else{/*存在账号*/
            JgUserDTO jgUserDTO = new JgUserDTO(imjgUser.getJgUsername(), imjgUser.getJgPassword());
            jgUserDTO.setUserId(userId);
            return jgUserDTO;
        }
    }


}
