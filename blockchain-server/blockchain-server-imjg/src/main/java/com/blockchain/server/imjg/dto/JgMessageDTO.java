package com.blockchain.server.imjg.dto;

import com.blockchain.common.base.dto.BaseDTO;
import lombok.Data;

@Data
public class JgMessageDTO extends BaseDTO {

    //版本号 目前是1 （必填）
    private int version;

    //	发送目标类型 single - 个人，group - 群组 chatroom - 聊天室（必填）
    private String target_type;

    //目标id single填username group 填Group id chatroom 填chatroomid（必填）
    private String target_id;

    //发送消息者身份 当前只限admin用户，必须先注册admin用户 （必填）
    private String from_type;


    //发送者的username （必填)
    private String from_id;

    //发消息类型 text - 文本，image - 图片, custom - 自定义消息（msg_body为json对象即可，服务端不做校验）voice - 语音 （必填）
    private String msg_type;


    private JgMessageBodyDTO msg_body;

    private String target_name;

    private String from_name;

    private String from_platform;

    private String from_appkey;

    private String target_appkey;

    private long create_time;

    private long msgid;

    private int msg_level;

    private long msg_ctime;

    private long sui_mtime;

    private int set_from_name;


    //simple
    public JgMessageDTO(String msg_type,String from_id,String target_id,JgMessageBodyDTO msg_body){

        this.version = 1;
        this.target_type = "single";
        this.target_id = target_id;
        this.from_type = "admin";
        this.from_id = from_id;
        this.msg_type = msg_type;
        this.msg_body = msg_body;

    }


}
