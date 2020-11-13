package com.blockchain.server.otc.dto.jgim;

import com.blockchain.common.base.dto.BaseDTO;
import lombok.Data;

@Data
public class JgMessageBodyDTO extends BaseDTO {

    private int id;

    private String extras;

    private String msg_type;

    private String media_id;

    private String media_crc32;

    private int duration;

    private String format;

    private int fsize;

    private int width;

    private int height;

    private String fname;

    private String text;


    public JgMessageBodyDTO(){}

    public JgMessageBodyDTO(String text){
        this.text = text;
    }

    public ImjgMessage toImjgMessage(){
        ImjgMessage imjgMessage = new ImjgMessage();
        imjgMessage.setMsgType(this.msg_type);
        imjgMessage.setDuration(this.duration);
        imjgMessage.setExtras(this.extras);
        imjgMessage.setFname(this.fname);
        imjgMessage.setText(this.text);

        imjgMessage.setFormat(this.format);
        imjgMessage.setFsize(this.fsize);
        imjgMessage.setWidth(this.width);
        imjgMessage.setHeight(this.height);

        imjgMessage.setMediaId(this.media_id);
        imjgMessage.setMediaCrc32(this.media_crc32);
        return imjgMessage;
    }

        //private int id;




}
