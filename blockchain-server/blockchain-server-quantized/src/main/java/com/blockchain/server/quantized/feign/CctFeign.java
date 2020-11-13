package com.blockchain.server.quantized.feign;//package com.blockchain.server.quantized.feign;


import com.blockchain.common.base.dto.ResultDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;

/**
 * 用户微服务
 *
 * @author huangxl
 * @create 2019-02-28 17:39
 */
@FeignClient(value="dapp-cct-server",path = "/cct")
public interface CctFeign {

    @PostMapping("/inner/quantizedFillTransaction")
    ResultDTO handleFillQuantizedTransation(@RequestParam("id") String id,
                                               @RequestParam("transNum") BigDecimal transNum,@RequestParam("price") BigDecimal price) ;

    @PostMapping("/inner/quantizedPartialTransaction")
    ResultDTO handlePartialQuantizedTransation(@RequestParam("id") String id,
                                            @RequestParam("transNum") BigDecimal transNum,@RequestParam("price") BigDecimal price) ;

    @PostMapping("/inner/quantizedCancel")
    ResultDTO handleQuantizedCancel(@RequestParam("id") String id);

    /**
     * 量化交易订阅到新的广告消息刷新深度
     *
     * @param coinName 基本货币
     * @param unitName 二级货币
     */
    @PostMapping("/inner/pushRecord")
    ResultDTO pushNewOrderRecord(@RequestParam("coinName") String coinName,
                                        @RequestParam("unitName") String unitName);

    /**
     * 查询订单状态
     * @param cctId 订单ID
     * */
    @GetMapping("/inner/orderStatus")
    public ResultDTO<String> orderStatus(@RequestParam("cctId") String cctId) ;
}
