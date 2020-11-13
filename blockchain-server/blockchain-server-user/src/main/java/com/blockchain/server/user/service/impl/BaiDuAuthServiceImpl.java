package com.blockchain.server.user.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.common.base.util.ExceptionPreconditionUtils;
import com.blockchain.server.user.common.constants.other.UserFileUploadConstant;
import com.blockchain.server.user.common.constants.sql.UserInfoConstant;
import com.blockchain.server.user.common.enums.UserEnums;
import com.blockchain.server.user.common.exceprion.UserException;
import com.blockchain.server.user.common.utils.FileUploadHelper;
import com.blockchain.server.user.common.utils.StrUtils;
import com.blockchain.server.user.common.utils.baidu.ai.Base64Util;
import com.blockchain.server.user.common.utils.baidu.ai.FileUtil;
import com.blockchain.server.user.common.utils.baidu.ai.GsonUtils;
import com.blockchain.server.user.common.utils.baidu.ai.HttpUtil;
import com.blockchain.server.user.entity.AuthenticationApply;
import com.blockchain.server.user.entity.UserAuthentication;
import com.blockchain.server.user.entity.UserInfo;
import com.blockchain.server.user.mapper.AuthenticationApplyMapper;
import com.blockchain.server.user.mapper.UserAuthenticationMapper;
import com.blockchain.server.user.service.AuthenticationApplyService;
import com.blockchain.server.user.service.BaiDuAuthService;
import com.blockchain.server.user.service.UserInfoService;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
 import org.springframework.web.multipart.MultipartFile;


import java.io.*;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @Datetime: 2020/5/18   18:24
 * @Author: Xia rong tao
 * @title
 */

@Service
public class BaiDuAuthServiceImpl implements BaiDuAuthService {

    private static final Logger LOG = LoggerFactory.getLogger(BaiDuAuthServiceImpl.class);


    @Autowired
    private UserAuthenticationMapper userAuthenticationMapper;


    @Value("${baidu.apiKey}")
    private String  apiKey ;

    @Value("${baidu.secretKey}")
    private String secretKey;

    /**
     * 获取token
     */
    @Value("${baidu.authHost}")
    private String authHost;

    /**
     * 身份证 + 姓名验证
     */
    @Value("${baidu.identityAuthHost}")
    private String identityAuthHost;



    /**
     * 身份证文字提取
     */
    @Value("${baidu.idcardHost}")
    private String idcardHost;


    @Value("${baidu.idcardApiKey}")
    private String  idcarApiKey ;

    @Value("${baidu.idcardSecretKey}")
    private String idcarSecret;

    /**
     * 身份验证
     */
    @Value("${baidu.identityValidationHost}")
    private String identityValidationHost;


    /**
     *会话id
     */
    @Value("${baidu.sessionHost}")
    private String sessionHost;

    /**
     *视频检测
     */
    @Value("${baidu.videoDetectionHost}")
    private String videoDetectionHost;



    @Autowired
    private RedisTemplate redisTemplate;

    private final  static String baiDuAuth = "user.baiDuAuth:{0}";



    @Autowired
    private AuthenticationApplyService authenticationApplyService;


    @Autowired
    private AuthenticationApplyMapper authenticationApplyMapper;


    @Value("${FILES_DIR.ROOT}")
    private String FILE_ROOT_PATH;//文件上传根目录


    @Autowired
    private UserInfoService userInfoService;


    @Override
    public String getAuth(int type) {

        String apikey = apiKey ;

        String secret  = secretKey;

        switch (type){
            case 1 :
                apikey = idcarApiKey;
                secret = idcarSecret ;
                break;
        }

        // 获取token地址
        StringBuffer accessTokenUrl = new StringBuffer(authHost);
        // 1. grant_type为固定参数
        accessTokenUrl.append("grant_type=client_credentials");
        // 2. 官网获取的 API Key
        accessTokenUrl.append("&client_id=").append(apikey);
        // 3. 官网获取的 Secret Key
        accessTokenUrl.append("&client_secret=").append(secret);
        try {
            String url = accessTokenUrl.toString();
             URL realUrl = new URL(url);
            // 打开和URL之间的连接
            HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            // 定义 BufferedReader输入流来读取URL的响应
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuffer result = new StringBuffer();
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
            String resultStr = result.toString();
            JSONObject jsonObject = JSON.parseObject(resultStr);
            String access_token = jsonObject.getString("access_token");
            //设置redisTemplate
            String key = String.format(baiDuAuth,type);
            redisTemplate.opsForValue().set(key,access_token, 29,TimeUnit.DAYS);
            return access_token;
        } catch (Exception e) {
            LOG.error("获取token异常", e);
        }
        return null;
    }



    @Override
    public ResultDTO userLowAuth(String userId, String name, String idCardNumber,String type) {

        AuthenticationApply apply = new AuthenticationApply();
        apply.setUserId(userId);
        apply.setStatus(UserInfoConstant.STATUS_HIGHT_AUTH_YES);
        apply = authenticationApplyMapper.selectOne(apply);
        //判断是否找到 ,true代表已经完成一级认证不允许再次认证
        if(apply != null)   {
            return ResultDTO.requestRejected(1201,"你已完成审核，请勿重复操作");
        }
        //交易身份证信息是否已经录入系统，如果是则不允许录入

        UserAuthentication example = new UserAuthentication();
        example.setIdNumber(idCardNumber);

        if( userAuthenticationMapper.select(example).size() >=1 )   {
            return ResultDTO.requestRejected(1202,"证件已被认证，请更换后重试");
        }
        String result = this.identityAuth(name,idCardNumber);
        JSONObject json  = JSON.parseObject(result);
        if("0".equals(  json.get("error_code").toString())){
         //认证成功
            authenticationApplyService.insertBasicAuth(userId, name, idCardNumber, type,  "[,]",true);
            return ResultDTO.requstSuccess();
        }else{
            return ResultDTO.requestRejected(1201,"认证失败");
        }

    }


    @Override
    public String getIdcard(String imagePath) {
        {
            try {
                /**
                 * front：身份证含照片的一面；back：身份证带国徽的一面
                 * 图像数据，base64编码后进行urlencode，要求base64编码和urlencode后大小不超过4M，最短边至少15px，最长边最大4096px,支持jpg/jpeg/png/bmp格式
                 */
                String accessToken = this.getCacheToken(1);
                String param = "id_card_side=front&image=" + this.base64Encode(imagePath);
                String result = HttpUtil.post(idcardHost, accessToken, param);
                return result;
            } catch (Exception e) {
                LOG.error("提取身份证文字异常", imagePath, e);
            }
            return null;
        }

    }

    @Override
    public String identityValidation(String imageBase64Str, String idCardNumber, String name, String qualityControl) {


        try {
            String accessToken = this.getCacheToken(0);
            Map<String, Object> map = new HashMap<>();
            //base64编码后的图片数据，需urlencode，编码后的图片大小不超过5M，图片尺寸不超过1920*1080
            map.put("image", imageBase64Str);
            map.put("liveness_control", "HIGH");
            //身份证名字 姓名（注：需要是UTF-8编码的中文）
            map.put("name", StrUtils.transcoding(name,"utf-8"));
            //身份证号码
            map.put("id_card_number", idCardNumber);
            /**
             *
             * 图片类型
             * BASE64:图片的base64值，base64编码后的图片数据，编码后的图片大小不超过2M；
             * URL:图片的 URL地址( 可能由于网络等原因导致下载图片时间过长)；
             * FACE_TOKEN: 人脸图片的唯一标识
             */
            map.put("image_type", "BASE64");
            //图片质量控制
            //NONE: 不进行控制
            //LOW:较低的质量要求
            //NORMAL: 一般的质量要求
            //HIGH: 较高的质量要求
            //默认 NONE
            //若图片质量不满足要求，则返回结果中会提示质量检测失败
            map.put("quality_control", "LOW");
            String param = GsonUtils.toJson(map);
            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String result = HttpUtil.post(identityValidationHost, accessToken, "application/json", param);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("身份认证失败",idCardNumber,e);
        }

        return null;
    }

    @Override
    public String getCacheToken(int type) {
        String key = MessageFormat.format(baiDuAuth,type);
        String token = (String) redisTemplate.opsForValue().get(key);
        if (StringUtils.isEmpty(token)) {
            token = this.getAuth(type);
        }
        return token;
    }

    @Override
    public String base64Encode(String imagePath) {

        try {
            // 本地文件路径
            String filePath = FILE_ROOT_PATH+"/" +imagePath;
            LOG.info("图片解析地址："+filePath);
            byte[] imgData = FileUtil.readFileByBytes(filePath);
            String imgStr = Base64Util.encode(imgData);
            String imgParam = URLEncoder.encode(imgStr, "UTF-8");
            return imgParam;
        }catch (Exception e){
            LOG.error("base64异常失败",imagePath,e);
            throw new UserException(UserEnums.BOSE64_ERROR);

        }

    }

    @Override
    public String videoAuth(String videoBase64) {
        return null;
    }

    @Transactional
    @Override
    public void insertBasicAuth( String userId, String imgs) {
        {

            ExceptionPreconditionUtils.notEmpty( imgs);
          //  FileUploadHelper.verifyFileNameList(imgs, 2);//初级认证需要身份证正反面，手持身份证照片
            Date now = new Date();
            String[] filePath = imgs.split(",");
            //解析图片
            String resultImageText = this.getIdcard(filePath[0]);
            if(resultImageText == null)  throw new UserException(UserEnums.BOSE64_ERROR);
            //转成 json 格式
            JSONObject json = JSON.parseObject(resultImageText);
            if( json.get("image_status") == null)  throw new UserException(UserEnums.BOSE64_ERROR);
            //判断状态 https://ai.baidu.com/ai-doc/OCR/rk3h7xzck
            if(!"normal".equals(json.get("image_status").toString()))  throw new UserException(UserEnums.BOSE64_ERROR);
            //解析正常
            json = (JSONObject)json.get("words_result");
            String idNumber = ((JSONObject)json.get("公民身份号码")).get("words").toString();
            String realName = ((JSONObject)json.get("姓名")).get("words").toString();
            AuthenticationApply apply = new AuthenticationApply();
            apply.setUserId(userId);
            apply.setStatus("Y");
            AuthenticationApply updateApply = authenticationApplyMapper.selectOne(apply);
            //判断是否找到 ,false 代表未完成1级认证
            if(apply == null)   {
                throw new UserException(UserEnums.USER_LEVEL);
            }else if(apply != null){

                if(idNumber.equals(updateApply.getIdNumber()) ==false || realName.equals(updateApply.getRealName()) ==false ){
                    //代表身份证解析信息和之前录入信息不匹配
                    throw new UserException(UserEnums.USER_NO_IDCARD);
                }else{
                    //先判断是否之前存在上转证件退出操作，则不插入数据，过滤，直接进入视频解析
                    UserAuthentication  userAuth = new UserAuthentication();
                    userAuth.setUserId(userId);
                    userAuth.setIdNumber(idNumber);
                    userAuth.setRealName(realName);
                    if(userAuthenticationMapper.select(userAuth).isEmpty()){
                        UserAuthentication userAuthentication = new UserAuthentication();
                        Date date = new Date();
                        userAuthentication.setId(UUID.randomUUID().toString());
                        userAuthentication.setUserId(userId);
                        userAuthentication.setCreateTime(date);
                        userAuthentication.setType(updateApply.getType());
                        userAuthentication.setIdNumber(updateApply.getIdNumber());
                        userAuthentication.setRealName(updateApply.getRealName());
                        userAuthentication.setFileUrl1(filePath[0]);
                        userAuthentication.setFileUrl2(filePath[1]);
                        userAuthentication.setModifyTime(now);
                        userAuthenticationMapper.insertSelective(userAuthentication);
                        return;
                    }
                   LOG.info(userId+":"+realName+"已身份整图片解析过，不需要二次插入");

                }
            }
        }
    }


    @Override
    public String voiceSessionId() {

        try {
            Map<String, Object> map = new HashMap<>(0);
            String param = GsonUtils.toJson(map);
            String result = HttpUtil.post(sessionHost, getCacheToken(0), "application/x-www-form-urlencoded", param);
            return result;
            //error_code为0即代表成功, 其他情况则为失败
        } catch (Exception ex) {
            LOG.error("获取语音会话id异常失败", ex);
            throw new UserException(UserEnums.FAILED);
        }
    }

    @Override
    public String videoDetection  ( MultipartFile file ,  String sessionId) {
        try {
            //"?session_id"+sessionId
            LOG.info("sessionId:"+sessionId);
            byte[] imgData = file.getBytes();
            String imgStr = Base64Util.encode(imgData);
            String imgParam = URLEncoder.encode(imgStr, "UTF-8");
            //String param =  "lip_identify=COMMON&session_id="+sessionId+"&"+URLEncoder.encode("video_base64", "UTF-8") +"="+imgParam;
           // String param = URLEncoder.encode("video_base64", "UTF-8") +"="+imgParam;
            String param =  "session_id="+sessionId+"&"+URLEncoder.encode("video_base64", "UTF-8") +"="+imgParam;

            String result = HttpUtil.post(videoDetectionHost , this.getCacheToken(0), "application/x-www-form-urlencoded", param);
            return result;
            //error_code为0即代表成功, 其他情况则为失败
        } catch (Exception ex) {
            LOG.error("视频活体检测异常失败", ex);
            throw new UserException(UserEnums.FAILED);
        }
     }


/*    public static void main(String[] args) throws  Exception {


        File file = new File("D:\\data\\files_root\\mp4\\xrt.mp4");
        byte[] imgData =fileConvertToByteArray(file);
        String imgStr = Base64Util.encode(imgData);
        String imgParam = URLEncoder.encode(imgStr, "UTF-8");
        String param =   "lip_identify=COMMON&session_id=S5ee3566b387e7137617438&"+URLEncoder.encode("video_base64", "UTF-8") +"="+imgParam;



        String result = HttpUtil.post( "https://aip.baidubce.com/rest/2.0/face/v1/faceliveness/verify" ,  "24.25b8411c7c21481cff9b2484ab7f8596.2592000.1592444764.282335-19937637", "application/x-www-form-urlencoded", param);
        System.out.println(result);
*//*
        {

            try {
                Map<String, Object> map = new HashMap<>(0);
                String param = GsonUtils.toJson(map);
                String result = HttpUtil.post("https://aip.baidubce.com/rest/2.0/face/v1/faceliveness/sessioncode", "24.25b8411c7c21481cff9b2484ab7f8596.2592000.1592444764.282335-19937637", "application/x-www-form-urlencoded", param);
                System.out.println(result);
                //error_code为0即代表成功, 其他情况则为失败
            } catch (Exception ex) {
                LOG.error("获取语音会话id异常失败", ex);
                throw new UserException(UserEnums.FAILED);
            }
        }*//*

    }*/

    /**
     * 把一个文件转化为byte字节数组。
     *
     * @return
     */
    private static byte[]  fileConvertToByteArray(File file) {
        byte[] data = null;

        try {
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            int len;
            byte[] buffer = new byte[1024];
            while ((len = fis.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }

            data = baos.toByteArray();

            fis.close();
            baos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;
    }


    @Override
    public ResultDTO insertHighAuth(String userId, String fileStr, String sessionId) {

        try {
            //判断图片格式
            //file.getOriginalFilename();
            //状态判断
            UserInfo userInfo = userInfoService.selectByUserId(userId);
            if (!userInfo.getLowAuth().equals(UserInfoConstant.STATUS_LOW_AUTH_YES)) {
                //提交高级认证前必须经过初级认证
                return ResultDTO.requestRejected(1123, "请先完成初级认证！");

            }
            if (userInfo.getHighAuth().equals(UserInfoConstant.STATUS_HIGHT_AUTH_WAIT)) {
                //已经是等待审核状态
                return ResultDTO.requestRejected(1201, "认证审核中，请勿重复操作！");
            }
            if (userInfo.getHighAuth().equals(UserInfoConstant.STATUS_HIGHT_AUTH_YES)) {
                //已经是认证状态
                return ResultDTO.requestRejected(1203, "你已完成认证");
            }
            //如果成功校验图片  identityValidation
            String param =  URLEncoder.encode("video_base64", "UTF-8") +"="+fileStr;
            String result = HttpUtil.post(videoDetectionHost, this.getCacheToken(0), "application/x-www-form-urlencoded", param);
            LOG.info("视频活体检测返回信息：" + result);

            JSONObject json = JSON.parseObject(result);
            if ("0".equals(json.get("err_no").toString())) {
                //解析成功，获取到图片信息，开始解析
                JSONObject imagesJson = (JSONObject) json.get("result");
                List<JSONObject> list = (List<JSONObject>) imagesJson.get("pic_list");
                //循环遍历8张图片，只要有一张成功，则终止
                AuthenticationApply apply = new AuthenticationApply();
                apply.setUserId(userId);
                apply = authenticationApplyMapper.selectOne(apply);
                //判断是否找到 ,false 代表未完成1级认证
                if (apply == null) throw new UserException(UserEnums.USER_LEVEL);
                String idNumber = apply.getIdNumber();//证件号码
                String realName = apply.getRealName();//真实姓名
                for (JSONObject images : list) {
                    String imagesStr64 = images.get("pic").toString();
                    String resultText = identityValidation(imagesStr64, apply.getIdNumber(), apply.getRealName(), null);
                    FileUploadHelper.generateImage(imagesStr64, FILE_ROOT_PATH, UserFileUploadConstant.UPLOAD_HIGH_AUTH);
                    //解析返回码
                    JSONObject resultJson = JSON.parseObject(resultText);
                    LOG.info("resultJson--->>" + resultJson);
                    //判断是否为本人
                    Object obj = resultJson.get("result");
                    if (obj == null) continue;
                    resultJson = (JSONObject) obj;
                    //与公安小图相似度可能性，用于验证生活照与公安小图是否为同一人，有正常分数时为[0~100]，推荐阈值80，超过即判断为同一人
                    if (((BigDecimal) resultJson.get("score")).compareTo(new BigDecimal("80")) >= 0) {
                        //代表不是同一个人抛出异常
                        String filePath = FileUploadHelper.generateImage(imagesStr64, FILE_ROOT_PATH, UserFileUploadConstant.UPLOAD_HIGH_AUTH);
                        //插入保存数据库
                        authenticationApplyService.insertHighAuth(userId, filePath,true);
                        return ResultDTO.requstSuccess();//匹配成功结束
                    }
                }
                return ResultDTO.requestRejected(1204, "个人信息和身份证信息不符合!");
            } else {
                return ResultDTO.requestRejected(1205, "视频解析失败");
            }
        }catch (Exception ex){
            LOG.error("io异常",ex);
            throw new UserException(UserEnums.FAILED);
        }

    }


    @Override
    public ResultDTO insertHighAuth(String userId, MultipartFile file, String sessionId) {

        try {
            //判断图片格式
            //状态判断
            UserInfo userInfo = userInfoService.selectByUserId(userId);
            if (!userInfo.getLowAuth().equals(UserInfoConstant.STATUS_LOW_AUTH_YES)) {
                //提交高级认证前必须经过初级认证
                return ResultDTO.requestRejected(1123, "请先完成初级认证！");

            }
            if (userInfo.getHighAuth().equals(UserInfoConstant.STATUS_HIGHT_AUTH_WAIT)) {
                //已经是等待审核状态
                return ResultDTO.requestRejected(1201, "认证审核中，请勿重复操作！");
            }
            if (userInfo.getHighAuth().equals(UserInfoConstant.STATUS_HIGHT_AUTH_YES)) {
                //已经是认证状态
                return ResultDTO.requestRejected(1203, "你已完成认证");
            }
            //如果成功校验图片  identityValidation
            JSONObject json =  videoIsRetry(file,sessionId,5);
            String  code = json.get("error_code").toString();

            if ("0".equals(code)) {
                //解析成功，获取到图片信息，开始解析
                JSONObject imagesJson = (JSONObject) json.get("result");
                //唇语识别结果 pass代表唇语验证通过，fail代表唇语验证未通过

                String  similarity  =   ( (JSONObject) imagesJson.get("code")).get("similarity").toString();

                LOG.info(userId+"语音返回信息："+similarity);
                double similarityDouble = Double.parseDouble(similarity);
                if( similarityDouble <  0.75 ){

                    return ResultDTO.requestRejected(1207, "语音验证失败!");
                }
               /* if(!"pass".equals(lipLanguage)){
                    return ResultDTO.requestRejected(1204, "唇语验证失败！");
                }*/
                //获取图片信息
                List<JSONObject> list = (List<JSONObject>) imagesJson.get("pic_list");
                //循环遍历8张图片，只要有一张成功，则终止
                AuthenticationApply apply = new AuthenticationApply();
                apply.setStatus("Y");
                apply.setUserId(userId);
                apply = authenticationApplyMapper.selectOne(apply);
                //判断是否找到 ,false 代表未完成1级认证
                if (apply == null) throw new UserException(UserEnums.USER_LEVEL);
                String idNumber = apply.getIdNumber();//证件号码
                String realName = apply.getRealName();//真实姓名
                Object errorMsg = null;
                for (JSONObject images : list) {
                    String imagesStr64 = images.get("pic").toString();
                    LOG.info("转入参数："+ apply.getIdNumber()  + ","+ apply.getRealName());
                    String resultText = identityValidation(imagesStr64, apply.getIdNumber(), apply.getRealName(), null);
                    LOG.info("返回信息---->>"+JSON.toJSONString(resultText));
                    //解析返回码
                    JSONObject resultJson = JSON.parseObject(resultText);
                      errorMsg = resultJson.get("error_msg");
                    //判断是否为本人
                    //
                    /* if( resultJson.get("error_code") == null)  throw new UserException(UserEnums.USER_NO_IDCARD);
                    if(!"0".equals(resultJson.get("error_code").toString()))  throw new UserException(UserEnums.USER_NO_IDCAR);*/
                    Object obj = resultJson.get("result");
                    if (obj == null) continue;
                    resultJson = (JSONObject) obj;
                    //与公安小图相似度可能性，用于验证生活照与公安小图是否为同一人，有正常分数时为[0~100]，推荐阈值80，超过即判断为同一人
                    if (((BigDecimal) resultJson.get("score")).compareTo(new BigDecimal("80")) >= 0) {
                        //代表不是同一个人抛出异常
                        String filePath = FileUploadHelper.generateImage(imagesStr64, FILE_ROOT_PATH, UserFileUploadConstant.UPLOAD_HIGH_AUTH);
                        //插入保存数据库
                        authenticationApplyService.insertHighAuth(userId, filePath,true);
                        return ResultDTO.requstSuccess(filePath);//匹配成功结束
                    }
                }
                return ResultDTO.requestRejected(1207, "个人信息和身份证信息比对失败!"+errorMsg.toString());
            } else {
                return ResultDTO.requestRejected(1208, "活体认证失败;"+json.get("error_msg").toString());
            }
        }catch (Exception ex){
            LOG.error("活体认证失败",ex);
            throw new UserException(UserEnums.FAILED);
        }

    }



    /**
     * 视频解析
     * @param file
     * @param sessionId
     * @param count  失败重试次数
     * @return
     */
    private JSONObject videoIsRetry( MultipartFile file, String sessionId,int count ) {

            /*  216430	rtse/face service error	rtse/face 服务异常	请重新尝试
            216431	voice service error	语音识别服务异常	请重新尝试
            216432	video service call fail	视频解析服务调用失败	请重新尝试
            216433	video service error	视频解析服务发生错误	请重新尝试
            216434	liveness check fail	活体检测失败	请重新尝试*/

        JSONObject json = JSON.parseObject(this.videoDetection(file, sessionId));
        String code = json.get("error_code").toString();
        while (count >0){
            if(!"0".equals(code)){
                if ("216430".equals(code) || "216431".equals(code) || "216432".equals(code) || "216433".equals(code) || "216434".equals(code)) {
                    LOG.info(sessionId+"重试"+count );
                    videoIsRetry(file, sessionId,count);
                }
            }else{
                return json;
            }
            count --;
        }
        return json;
    }

    @Override
    public String identityAuth(String name, String idCardNumber) {
        Map<String, String> paramMap = new HashMap<>();
        String token = this.getCacheToken(0);
        paramMap.put("access_token", token);
        paramMap.put("id_card_number", idCardNumber);

        paramMap.put("name", StrUtils.transcoding(name,"utf-8"));
        StringBuilder sb = new StringBuilder();
        PostMethod postMethod = new PostMethod(identityAuthHost+token);
        postMethod.addRequestHeader("Content-Type", "application/json");
        paramMap.forEach((k, v) -> {
            postMethod.addParameter(k, v);
        });
        HttpClient httpClient = new HttpClient();
        String responseBodyAsString = null;
        try {
            int status = httpClient.executeMethod(postMethod);
            responseBodyAsString = postMethod.getResponseBodyAsString();
        } catch (Exception ex) {
            LOG.error("身份请求异常",idCardNumber, ex);
        }

        // 根据error_code判断，为0时表示匹配为同一个人。否则按错误码表的定义，如222351表示身份证号码与名字不匹配。
        return responseBodyAsString;
    }

}
