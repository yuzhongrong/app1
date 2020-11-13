package com.blockchain.server.user.service;

import com.blockchain.common.base.dto.ResultDTO;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * @Datetime: 2020/5/18   18:22
 * @Author: Xia rong tao
 * @title   百度认证
 */

public interface BaiDuAuthService {


    /**
     * 获取API访问token
     * @param  type 0 姓名身份证认证，1 文字提取
     * @return
     */
    String getAuth(int type);


    /**
     * 身份认证
     * @param name   姓名
     * @param idCardNumber   身份证号码
     * @return
     */
    String identityAuth(String name , String idCardNumber);

    /**
     * 初级身份认证
     * @param  userId
     * @param name   姓名
     * @param idCardNumber   身份证号码
     * @param type 类型
     * @return
     */
    ResultDTO userLowAuth(String userId, String name , String idCardNumber , String type);


    /**
     * 获取转入身份证正面的文字
     * @param imagePath 图片地址

     * @return
     */
    String getIdcard(String imagePath );


    /**
     * 身份认证
     * @param imageBase64Str base64字符图片解析字符
     * @param idCardNumber 身份证号码
     * @param name        名字
     * @param qualityControl
                 * 图片质量控制
                 * NONE: 不进行控制
                 * LOW:较低的质量要求
                 * NORMAL: 一般的质量要求
                 * HIGH: 较高的质量要求
                 * 默认 NONE
     * @return
     */
    String identityValidation(String imageBase64Str ,String idCardNumber,String name ,String qualityControl);


    /**
     * 获取系统缓存token
     * @param  type   0 姓名身份证认证，1 文字提取
     * @return
     */
    String getCacheToken(int type);


    /**
     * 对转入图片地址进行 base64编码
     * @param imagePath
     * @return
     */
    String base64Encode(String imagePath);


    /**
     * 视频活体认证
     * @param  videoBase64 base64 编码的视频数据
     * @return
     */
    String videoAuth(String videoBase64);
    /**
     * 插入初级认证申请记录
     * @param imgs 文件地址
     */
    void insertBasicAuth(String userId,String imgs);


/*
    */
/**
     * 高級认证
     * @param  userId
     * @param img
     *//*

    void insertHighAuth(String userId, String img);
*/


    /**
     * 获取语音会话id
     * @return
     */
    String voiceSessionId();


    /**
     * 视频活体检测
     * @param  file
     * @param  sessionId 会话id
     * @return
     */
    String videoDetection(  MultipartFile file ,String sessionId);




    /**
     * 视频高级认证
     * @param userId
     * @param  file
     * @param  sessionId 会话id
     * @return
     */
    ResultDTO insertHighAuth(String userId ,  MultipartFile file ,String sessionId);



    /**
     * 视频高级认证
     * @param userId
     * @param  fileStr base64
     * @param  sessionId 会话id
     * @return
     */
    ResultDTO insertHighAuth(String userId ,  String  fileStr ,String sessionId);
}
