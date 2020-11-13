package com.blockchain.server.user.service;

/**
 * 申请认证服务
 * @author huangxl
 * @create 2019-02-26 14:20
 */
public interface AuthenticationApplyService {


    /**
     * 查询高级未认证原因
     * @param userId
     * @return
     */
    String selectHighRemarkByUserId(String userId);
    /**
     * 查询初级未认证原因
     * @param userId
     * @return
     */
    String selectLowRemarkByUserId(String userId);
    /**
     * 插入初级认证申请记录
     * @param userId 用户id
     * @param realName 真实名称
     * @param idNumber 身份证号
     * @param type 证件类型
     * @param imgs 文件地址
     * @param  mark treu = 百度认证
     */
    void insertBasicAuth(String userId,String realName, String idNumber, String type,String imgs,boolean mark);

    /**
     * 插入高级认证申请记录
     * @param userId 用户id
     * @param img 文件地址
     * @param  mark true = 百度认证
     */
    void insertHighAuth(String userId, String img,boolean mark);

    /**
     * 判断用户认证状态接口
     * @param userId
     * @param authenticationType
     * @return
     */
    String judgeAuthentication(String userId, String authenticationType);
}
