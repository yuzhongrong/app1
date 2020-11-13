package com.blockchain.server.otc.dto.jgim;

import com.blockchain.common.base.dto.BaseDTO;
import lombok.Data;
import lombok.Getter;

/**
 * @author ：zz
 * @date ：Created in 2019/4/25 15:11
 * @description：极光消息推送
 * @modified By：
 * @version: 1.0$
 */
@Data
public class JgMassageParam extends BaseDTO {

    /**
     * 服务模块名
     **/
    public String pType;
    /**
     * 业务ID
     **/
    public String dataId;
    /**
     * 发消息类型
     * text - 文本，
     * image - 图片,
     * custom - 自定义消息（msg_body为json对象即可，服务端不做校验）
     * voice - 语音 （必填）
     **/
    public String msgType;
    /**
     * 发送方用户ID
     **/
    public String fuserId;
    /**
     * 接收方用户ID
     **/
    public String tuserId;
    /**
     * 消息DTO类的JSON串
     **/
    public String msgBodyJson;
    /**
     * "节点消息标志状态:" +
     * //            "0:默认，不是节点消息;1:双方可见的节点消息;2:接收方可见的节点消息"
     **/
    public int nodeCue;


    public enum MsgTypeEnums {

        text("text", "文本", null),
        image("image", "图片", null),
        custom("custom", "自定义消息", null),
        voice("voice", "语音", null);

        @Getter
        private String code;
        @Getter
        private String name;//值 int类型，默认0
        @Getter
        private String value;//值 string类型

        MsgTypeEnums(String code, String name, String value) {
            this.code = code;
            this.name = name;
            this.value = value;
        }

        public String code() {
            return code;
        }
    }

}
