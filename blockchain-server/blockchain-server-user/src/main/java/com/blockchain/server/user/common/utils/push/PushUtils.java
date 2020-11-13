package com.blockchain.server.user.common.utils.push;

import com.blockchain.server.user.service.impl.UserServiceImpl;
import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.exceptions.RequestException;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.TransmissionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class PushUtils {

    @Value("${getuipush.appId}")
    private String appId;
    @Value("${getuipush.appKey}")
    private String appKey;
    @Value("${getuipush.masterSecret}")
    private String masterSecret;
    @Value("${getuipush.host}")
    private String host;
    @Value("${getuipush.open}")
    private boolean open;

    //日志
    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);
    //是否可离线发送
    private static final boolean OFFLINE = true;
    //1为wifi，0为不限制网络环境。根据手机处于的网络情况，决定是否下发
    private static final int PUSH_NETWORK_TYPE = 0;


    /***
     * 对单个用户推送消息
     * @param clientId
     * @param title
     * @param content
     * @param payLoadMap
     */
    public void pushToSingle(String clientId, String title, String content, Map<String, Object> payLoadMap) {
        //open为true才发送通知
        if (open) {
            //推送对象
            IGtPush push = new IGtPush(host, appKey, masterSecret);
            //构建透传消息模板
            TransmissionTemplate template = TemplateUtils.getTransmissionTemplate(appId, appKey, title, content, payLoadMap);
            //消息对象
            SingleMessage message = new SingleMessage();
            //是否可离线发送
            message.setOffline(OFFLINE);
            //发送模板
            message.setData(template);
            //可选，1为wifi，0为不限制网络环境。根据手机处于的网络情况，决定是否下发
            message.setPushNetWorkType(PUSH_NETWORK_TYPE);
            //关闭快速域名
            System.setProperty("needOSAsigned", "true");
            //推送目标对象
            Target target = new Target();
            target.setAppId(appId);
            //设置发送的客户端Id
            target.setClientId(clientId);
            //请求返回标识
            IPushResult ret = null;
            try {
                ret = push.pushMessageToSingle(message, target);
            } catch (RequestException e) {
                e.printStackTrace();
                ret = push.pushMessageToSingle(message, target, e.getRequestId());
            }
            if (ret != null) {
                LOG.info(ret.getResponse().toString());
            } else {
                LOG.info("PUSH通知推送失败！");
            }
        }
    }
}
