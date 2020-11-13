package com.blockchain.server.imjg.dto;

import com.blockchain.common.base.dto.BaseDTO;
import com.blockchain.server.imjg.entity.ImjgMessage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("消息实体")
public class MessageDTO extends BaseDTO {

    @ApiModelProperty("消息ID")
    private int id;

    @ApiModelProperty("消息额外字段JSON串")
    private String extras;

    @ApiModelProperty("消息类型")
    private String msgType;

    @ApiModelProperty("聊天类型为text时，消息内容")
    private String text;

    @ApiModelProperty("聊天类型为image、文件时,发送消息后极光返回的media_id,下面的字段可以不传")
    private String mediaId;

    private String mediaCrc32;

    private int duration;

    private String format;

    private int fsize;

    private int width;

    private int height;

    private String fname;

    public MessageDTO(){}

    public MessageDTO(ImjgMessage imjgMessage){
        if(imjgMessage != null){
            this.fname = imjgMessage.getFname();
            this.height = imjgMessage.getHeight();
            this.width = imjgMessage.getWidth();
            this.fsize = imjgMessage.getFsize();
            this.format = imjgMessage.getFormat();
            this.duration = imjgMessage.getDuration();
            this.mediaCrc32 = imjgMessage.getMediaCrc32();
            this.mediaId = imjgMessage.getMediaId();
            this.text = imjgMessage.getText();
            this.msgType = imjgMessage.getMsgType();
            this.extras = imjgMessage.getExtras();
            this.id = imjgMessage.getId();
        }
    }


}
