package com.blockchain.server.imjg.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "imjg_message_log")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImjgMessageLog {

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "p_type")
    private String pType;

    @Column(name = "data_id")
    private String dataId;

    @Column(name = "msg_type")
    private String msgType;
    @Column(name = "target_id")
    private String targetId;


    @Column(name = "from_id")
    private String fromId;


    @Column(name = "msg_id")
    private int msgId;

    @Column(name = "node_cue")
    private int nodeCue;

    @Column(name = "status")
    private String status;

    @Column(name = "gmt_create")
    private String gmtCreate;

    @Column(name = "gmt_modified")
    private String gmtModified;



}
