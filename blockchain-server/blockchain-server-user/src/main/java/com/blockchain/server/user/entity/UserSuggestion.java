package com.blockchain.server.user.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户反馈表
 */
@Table(name = "dapp_u_user_suggestions")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSuggestion implements Serializable {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "user_id")
    private String userId;
    @Column(name = "username")
    private String username;
    @Column(name = "text_suggestion")
    private String textSuggestion;
    @Column(name = "state")
    private Integer state;
    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "modify_time")
    private Date modifyTime;

}
