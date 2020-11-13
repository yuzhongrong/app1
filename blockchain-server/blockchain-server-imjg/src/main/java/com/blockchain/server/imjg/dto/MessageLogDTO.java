package com.blockchain.server.imjg.dto;

import com.blockchain.common.base.dto.BaseDTO;
import com.blockchain.server.imjg.entity.ImjgMessageLog;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("消息记录")
public class MessageLogDTO extends BaseDTO {


    @ApiModelProperty("消息记录Id")
    private int id;

    @ApiModelProperty("模块名")
    private String pType;

    @ApiModelProperty("业务ID")
    private String dataId;

    @ApiModelProperty("消息类型(text,voice,image,file,video,location,custom)")
    private String msgType;

    @ApiModelProperty("发送目标的用户ID")
    private String targetId;

    @ApiModelProperty("发送方的用户ID")
    private String fromId;

    @ApiModelProperty("消息ID")
    private int msgId;

    @ApiModelProperty("节点提示词(0:不是节点提示词,1:两边均可见的提示词,2:接收方可见的节点提示词)")
    private int nodeCue;

    @ApiModelProperty("消息状态(NORMAL:正常,CANCEL:已删除)")
    private String status;

    @ApiModelProperty("消息发送时间")
    private String gmtCreate;

    private String gmtModified;

    public MessageLogDTO(ImjgMessageLog imjgMessageLog){
        if(imjgMessageLog != null){
            this.id = imjgMessageLog.getId();
            this.pType = imjgMessageLog.getPType();
            this.dataId = imjgMessageLog.getDataId();
            this.msgType = imjgMessageLog.getMsgType();
            this.targetId = imjgMessageLog.getTargetId();
            this.fromId = imjgMessageLog.getFromId();
            this.msgId = imjgMessageLog.getMsgId();
            this.nodeCue = imjgMessageLog.getNodeCue();
            this.status = imjgMessageLog.getStatus();
            this.gmtCreate = imjgMessageLog.getGmtCreate();
        }
    }

    @ApiModelProperty("消息体实体")
    private MessageDTO messageDTO;

    @ApiModelProperty("消息发送方实体")
    private JgUserInfoDTO fromUser;



}
