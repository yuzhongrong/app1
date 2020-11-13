package com.blockchain.server.user.common.utils;

import com.blockchain.common.base.constant.BaseConstant;
import com.blockchain.server.user.common.constants.other.RedisConstant;
import com.blockchain.server.user.common.enums.UserEnums;
import com.blockchain.server.user.common.exceprion.UserException;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.*;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * @author huangxl
 * @create 2019-01-18 18:05
 */
@Data
@Component
@ConfigurationProperties(prefix = "email")
public class EMailTransmitHelper {
    private static final Logger LOG = LoggerFactory.getLogger(EMailTransmitHelper.class);
    private String fromAccount;//账号
    private String hostName;//smtp服务器
    private String userName;//发件人姓名
    private String authCode;//授权码、密码
    private int timeout = 30;//超时时间，分钟
    private boolean closed;

    @Autowired
    private RedisTemplate redisTemplate;

    private String host163 ; // smtp服务器
    private String from163 ; // 发件人地址
    private String user163 ; // 用户名
    private String pwd163  ; // 163的授权码



    //邮箱验证码 一般发送
    @Async
    public void sendEmail(String emailaddress, String code, String userLocale,String redisKey) {
        if (closed) {
            //判断是否关闭了邮箱发送功能，如果是关闭状态，直接返回结果
            return;
        }
//        if(emailaddress.contains("@163.com")){
//            send163(emailaddress,code,userLocale,  redisKey);
//        }else{
            Properties props = new Properties();
            props.put("mail.smtp.host", hostName);
            props.put("mail.smtp.socketFactory.port", "465");
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.port", "465");
            Session session = Session.getDefaultInstance(props,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(fromAccount, authCode);
                        }
                    });
            try {
                String sendUserName = MimeUtility.encodeText(userName);//发件人姓名
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(fromAccount,sendUserName));
                message.setRecipients(Message.RecipientType.TO,
                        InternetAddress.parse(emailaddress));
                message.setSubject(Subject.getSubject(userLocale));
                message.setText(MessageFormat.format(Msg.getSubject(userLocale),emailaddress, code));
                Transport.send(message);
            } catch (Exception e) {
                LOG.error("发送邮件失败，邮箱地址为：" + emailaddress,e);
                redisTemplate.delete(redisKey);//移除缓存的key
                throw new UserException(UserEnums.SEND_CODE_ERROR);
            }
        }

 //   }


    /**
     *  发送163邮件
     * @param emailaddress
     * @param code
     * @param userLocale
     * @param  redisKey
     */
    public   void send163( String emailaddress,String code, String userLocale,String redisKey) {
        Properties props = new Properties();
        props.put("mail.smtp.host", host163);//设置发送邮件的邮件服务器的属性（这里使用网易的smtp服务器）
        props.put("mail.smtp.auth", "true");  //需要经过授权，也就是有户名和密码的校验，这样才能通过验证（一定要有这一条）
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.port", "465");
        Session session = Session.getInstance(props);//用props对象构建一个session
        //Session.getInstance
        session.setDebug(true);
        MimeMessage message = new MimeMessage(session);//用session为参数定义消息对象
        Transport transport = null ;
        try {
            message.setFrom(new InternetAddress(from163));// 加载发件人地址
 /*           InternetAddress[] sendTo = new InternetAddress[TOS.length]; // 加载收件人地址
            for (int i = 0; i < TOS.length; i++) {
                sendTo[i] = new InternetAddress(TOS[i]);
            }*/
            InternetAddress[] sendTo = new InternetAddress[1]; // 加载收件人地址
            sendTo[0] = new InternetAddress(emailaddress);
            message.addRecipients(Message.RecipientType.TO,sendTo);
            message.addRecipients(MimeMessage.RecipientType.CC, InternetAddress.parse(from163));//设置在发送给收信人之前给自己（发送方）抄送一份，不然会被当成垃圾邮件，报554错
            message.setSubject(Subject.getSubject(userLocale));//加载标题
            Multipart multipart = new MimeMultipart();//向multipart对象中添加邮件的各个部分内容，包括文本内容和附件
            BodyPart contentPart = new MimeBodyPart();//设置邮件的文本内容
            contentPart.setText(MessageFormat.format(Msg.getSubject(userLocale), code));
            multipart.addBodyPart(contentPart);
            message.setContent(multipart);//将multipart对象放到message中
            message.saveChanges(); //保存邮件
            transport = session.getTransport("smtp");//发送邮件
            transport.connect(host163, user163, pwd163);//连接服务器的邮箱
            transport.sendMessage(message, message.getAllRecipients());//把邮件发送出去

        } catch (Exception e) {
            LOG.error("发送异常失败",e);
            redisTemplate.delete(redisKey);//移除缓存的key
            throw new UserException(UserEnums.SEND_CODE_ERROR);
        }finally {

            if(transport != null)
            {
                try {
                    transport.close();//关闭连接
                }catch (javax.mail.MessagingException e){
                    LOG.error("163会话关闭失败",e);
                }
            }
        }
    }


    /**
     * 邮箱主题
     */
    private enum Subject {
        ZH_CN("【DUOBIT】安全验证"),
        ZH_HK("【DUOBIT】安全驗證"),
        EN_US("【DUOBIT】Safety Verification");
        private static Map<String, Subject> map = new HashMap<>();

        static {
            map.put(BaseConstant.USER_LOCALE_ZH_HK, ZH_HK);
            map.put(BaseConstant.USER_LOCALE_ZH_CN, ZH_CN);
            map.put(BaseConstant.USER_LOCALE_EN_US, EN_US);
        }

        private String value;

        Subject(String value) {
            this.value = value;
        }

        public static String getSubject(String userLocale) {
            return map.getOrDefault(userLocale, map.get(BaseConstant.USER_LOCALE_DEFAULT)).value;
        }

    }

    /**
     * 邮箱主题
     */
    private enum Msg {
        ZH_CN("\n【DOBTmall】尊敬的{0}先生/女士，您的短信验证码为：{1}，请在15分钟内按照页面提示提交验证码，切勿将验证码告诉他人。谢谢！"),
        ZH_HK("\n【DOBTmall】尊敬的{0}先生/女士，您的短信验证码为：{1}，请在15分钟内按照页面提示提交验证码，切勿将验证码告诉他人。谢谢！"),
        EN_US("Dear: \n【DUOBIT】Your verification code is: {1} ,The verification code is only used for DUOBIT official website. If you do not operate the code, please ignore it!");
        private static Map<String, Msg> map = new HashMap<>();

        static {
            map.put(BaseConstant.USER_LOCALE_ZH_HK, ZH_HK);
            map.put(BaseConstant.USER_LOCALE_ZH_CN, ZH_CN);
            map.put(BaseConstant.USER_LOCALE_EN_US, EN_US);
        }

        private String value;

        Msg(String value) {
            this.value = value;
        }

        public static String getSubject(String userLocale) {
            return map.getOrDefault(userLocale, map.get(BaseConstant.USER_LOCALE_DEFAULT)).value;
        }

    }

}
