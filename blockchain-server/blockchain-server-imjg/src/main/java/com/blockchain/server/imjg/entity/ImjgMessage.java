package com.blockchain.server.imjg.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "imjg_message")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImjgMessage {

    @Id
    @Column(name = "id")
    private int id;
    @Column(name = "extras")
    private String extras;
    @Column(name = "msg_type")
    private String msgType;
    @Column(name = "text")
    private String text;

    @Column(name = "media_id")
    private String mediaId;
    @Column(name = "media_crc32")
    private String mediaCrc32;
    @Column(name = "duration")
    private int duration;
    @Column(name = "format")
    private String format;

    @Column(name = "fsize")
    private int fsize;
    @Column(name = "width")
    private int width;
    @Column(name = "height")
    private int height;
    @Column(name = "fname")
    private String fname;

/*    @Column(name = "latitude")
    private int latitude;
    @Column(name = "longitude")
    private int longitude;
    @Column(name = "scale")
    private int scale;
    @Column(name = "lable")
    private String lable;*/
}
