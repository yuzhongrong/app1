package com.blockchain.server.imjg.service;


import com.blockchain.server.imjg.dto.JgUserDTO;
import com.blockchain.server.imjg.dto.MessageLogDTO;
import com.blockchain.server.imjg.entity.ImjgMessageLog;

import java.util.List;
/**
 * @author: rui
 * @date: 2019/5/5 0005 09:52
 * @description: Im消息服务
 */
public interface ImjgMessageLogService {

    /**
     * 保存一条消息记录
     * @param imjgMessageLog
     */
    void save(ImjgMessageLog imjgMessageLog);

    /**
     * 保存一条消息体，和一条消息记录
     * @param pType 服务模块名称
     * @param dataId 业务ID
     * @param fromUserId 发送方用户ID
     * @param targetUserId 接收方用户ID
     * @param msgType 消息类型
     * @param msgBody 消息体内容
     * @param nodeCue 节点消息标志
     */
    void save(String pType,String dataId,String fromUserId,String targetUserId,String msgType,String msgBody,int nodeCue);

    /**
     * 按照条件获取消息记录
     * @param pType
     * @param dataId
     * @param userId
     * @param order
     * @param page
     * @param size
     * @return
     */
    List<MessageLogDTO> list(String pType, String dataId, String userId, String order, int page, int size);

    /**
     *  根据系统用户ID获取极光账号、密码
     * @param userId
     * @return
     */
    JgUserDTO getJGAccount(String userId);


}
