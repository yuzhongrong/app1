package com.blockchain.server.user.common.utils.push;

import com.gexin.rp.sdk.base.payload.APNPayload;
import com.gexin.rp.sdk.template.TransmissionTemplate;

import java.text.MessageFormat;
import java.util.Map;

public class TemplateUtils {

    //透传消息体
    private static final String TRANSMISSION_CONTENT = "title:\"{0}\",content:\"{1}\",payload:{2}";
    //透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
    private static final int TRANSMISSION_TYPE = 2;

    public static TransmissionTemplate getTransmissionTemplate(String appId, String appKey, String title,
                                                               String content, Map<String, Object> payLoadMap) {
        //封装透传数据为JSON格式
        String payLoad = getPayLoad(payLoadMap);
        //封装IOS APNs通知参数
        APNPayload apnPayLoad = getAPNPayload(content, payLoadMap);
        //封装透传消息体
        String transmissionContent = getTransmissionContent(title, content, payLoad);
        //构建透传模板
        return getTransmissionTemplate(appId, appKey, transmissionContent, apnPayLoad);
    }

    /****
     * 透传消息模板
     * @param appId 应用Id
     * @param appKey 应用key
     * @param transmissionContent 透传消息
     * @return
     */
    private static TransmissionTemplate getTransmissionTemplate(String appId, String appKey, String transmissionContent,
                                                                APNPayload apnPayload) {
        TransmissionTemplate template = new TransmissionTemplate();
        template.setAppId(appId);
        template.setAppkey(appKey);
        //透传消息设置
        template.setTransmissionType(TRANSMISSION_TYPE);
        //透传消息设置
        template.setTransmissionContent(transmissionContent);
        //设置苹果离线发送
        template.setAPNInfo(apnPayload);
        return template;
    }

    /***
     * 封装透传消息体
     * @param title
     * @param content
     * @param payload
     * @return
     */
    private static String getTransmissionContent(String title, String content, String payload) {
        String format = MessageFormat.format(TRANSMISSION_CONTENT, title, content, payload);
        return "{" + format + "}";
    }

    /***
     * 封装透传数据
     * @param payload
     * @return
     */
    private static String getPayLoad(Map<String, Object> payload) {
        StringBuilder str = new StringBuilder();
        str.append("{");
        if (payload != null && payload.size() > 0) {
            //循环传递参数
            for (Map.Entry<String, Object> entry : payload.entrySet()) {
                str.append(entry.getKey());
                str.append(":");
                //String类型的数据加""
                if (entry.getValue() instanceof String) {
                    str.append("\"" + entry.getValue() + "\"");
                }
                str.append(",");
            }
            //删除最后一个逗号
            str.deleteCharAt(str.lastIndexOf(","));
        }
        str.append("}");
        return str.toString();
    }

    /***
     * 构建IOS APNs通知参数
     * @param content
     * @param payload
     * @return
     */
    private static APNPayload getAPNPayload(String content, Map<String, Object> payload) {
        APNPayload apnPayload = new APNPayload();
        //在已有数字基础上加1显示，设置为-1时，在已有数字上减1显示，设置为数字时，显示指定数字
        apnPayload.setAutoBadge("+1");
        //推送直接带有透传数据
        apnPayload.setContentAvailable(1);
        //添加透传数据
        for (Map.Entry<String, Object> entry : payload.entrySet()) {
            //透传数据
            apnPayload.addCustomMsg(entry.getKey(), entry.getValue());
        }
        //简单模式APNPayload.SimpleMsg
        apnPayload.setAlertMsg(new APNPayload.SimpleAlertMsg(content));
        return apnPayload;
    }
}
