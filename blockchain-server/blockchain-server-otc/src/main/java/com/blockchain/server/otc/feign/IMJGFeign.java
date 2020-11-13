package com.blockchain.server.otc.feign;


import com.blockchain.common.base.dto.ResultDTO;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("dapp-imjg-server")
public interface IMJGFeign {
    String CONTENT_PATH = "/imjg/inner";

    /***
     * 发送单聊消息接口
     * 发送单聊信息并保存消息记录
     * @param pType 服务模块名
     * @param dataId 业务ID
     * @param msgType 消息类型
     * @param fuserId 发送方用户ID
     * @param tuserId 接收方用户ID
     * @param msgBodyJson 消息DTO类的JSON串
     * @param nodeCue 节点消息标志状态: 0:默认，不是节点消息;1:双方可见的节点消息;2:接收方可见的节点消息
     * @return
     */
    @PostMapping(CONTENT_PATH + "/sendSingleMsg")
    ResultDTO<String> sendSingleMsg(@RequestParam(value = "pType") String pType,
                                    @RequestParam(value = "dataId") String dataId,
                                    @RequestParam(value = "msgType") String msgType,
                                    @RequestParam(value = "fuserId") String fuserId,
                                    @RequestParam(value = "tuserId") String tuserId,
                                    @RequestParam(value = "msgBodyJson") String msgBodyJson,
                                    @RequestParam(value = "nodeCue") int nodeCue);
}
