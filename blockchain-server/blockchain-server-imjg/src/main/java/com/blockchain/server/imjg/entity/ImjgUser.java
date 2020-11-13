package com.blockchain.server.imjg.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "imjg_user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImjgUser {

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "jg_username")
    private String jgUsername;

    @Column(name = "jg_password")
    private String jgPassword;

    @Column(name = "gmt_create")
    private String gmtCreate;

    @Column(name = "gmt_modified")
    private String gmtModified;
}
