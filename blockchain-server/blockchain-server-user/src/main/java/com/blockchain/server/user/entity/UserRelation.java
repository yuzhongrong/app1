package com.blockchain.server.user.entity;

import com.blockchain.common.base.entity.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * UserRelation 用户关系, 数据传输类
 *
 * @version 1.0
 * @date 2019-02-21 13:37:18
 */
@Table(name = "dapp_u_user_relation")
@Data
public class UserRelation extends BaseModel {
    @Id
    @Column(name = "id")
    private String id;
    /**
     * 当前用户id
     */
    @Column(name = "user_id")
    private String userId;
    /**
     * 父id
     */
    @Column(name = "pid")
    private String pid;
    /**
     * 关系链信息
     */
    @Column(name = "relation_chain")
    private String relationChain;

    /**
     * 关系深度
     */
    @Column(name = "tree_depth")
    private Integer treeDepth;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private java.util.Date createTime;

}