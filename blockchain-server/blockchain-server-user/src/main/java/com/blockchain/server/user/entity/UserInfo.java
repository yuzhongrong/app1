package com.blockchain.server.user.entity;

import com.blockchain.common.base.entity.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * UserInfo 用户登录信息,数据传输类
 *
 * @version 1.0
 * @date 2019-02-21 13:37:18
 */
@Table(name = "dapp_u_user_info")
@Data
public class UserInfo extends BaseModel {
    @Id
    @Column(name = "id")
    private String id;
    /**
     * 用户id
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 用户邮箱
     */
    @Column(name = "email")
    private String email;
    /**
     * 用户头像
     */
    @Column(name = "avatar")
    private String avatar;

    /**
     * 谷歌验证器
     */
    @Column(name = "google_auth")
    private String googleAuth;

    /**
     * 初级认证状态
     */
    @Column(name = "low_auth")
    private String lowAuth;

    /**
     * 高级认证状态
     */
    @Column(name = "high_auth")
    private String highAuth;

    /**
     * 自增码
     */
    @Column(name = "incr_code")
    private Integer incrCode;

    /**
     * 随机数
     */
    @Column(name = "random_number")
    private Integer randomNumber;

    /**
     * 用户等级
     */
    @Column(name = "grade")
    private String grade;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private java.util.Date createTime;

    /**
     * 修改时间
     */
    @Column(name = "modify_time")
    private java.util.Date modifyTime;

}