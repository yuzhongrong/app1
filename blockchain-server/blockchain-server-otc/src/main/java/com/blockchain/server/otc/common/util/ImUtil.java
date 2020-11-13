package com.blockchain.server.otc.common.util;

import com.alibaba.fastjson.JSON;
import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.server.otc.dto.jgim.JgMassageParam;
import com.blockchain.server.otc.dto.jgim.JgMessageBodyDTO;
import com.blockchain.server.otc.feign.IMJGFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author ：zz
 * @date ：Created in 2019/4/25 17:01
 * @description：Im工具接口
 * @modified By：
 * @version: 1.0$
 */
@Component
public class ImUtil {
    @Autowired
    private IMJGFeign imjgFeign;

    String PTYPE = "OTC";

    /***
     *
     * @param content 发送内容
     * @param sendUser 发送方
     * @param recUser 接收方
     * @param dataId 业务id
     * @return
     */
    public ResultDTO<String> postMsgIMSelf(String content, String sendUser, String recUser, String dataId) {
        JgMassageParam param = new JgMassageParam();
        JgMessageBodyDTO jgMessageBodyDTO = new JgMessageBodyDTO();
        jgMessageBodyDTO.setText(content);
        jgMessageBodyDTO.setMsg_type(JgMassageParam.MsgTypeEnums.text.getCode());
        param.setPType(PTYPE);
        param.setDataId(dataId);
        param.setMsgType(JgMassageParam.MsgTypeEnums.text.getCode());
        param.setMsgBodyJson(JSON.toJSONString(jgMessageBodyDTO));
        param.setNodeCue(2);
        param.setFuserId(sendUser);
        param.setTuserId(recUser);
        return imjgFeign.sendSingleMsg(
                param.getPType(),
                param.getDataId(),
                param.getMsgType(),
                param.getFuserId(),
                param.getTuserId(),
                param.getMsgBodyJson(),
                param.getNodeCue());
    }
}
