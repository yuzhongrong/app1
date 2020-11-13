package com.blockchain.server.cct.controller;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.common.base.util.SSOHelper;
import com.blockchain.server.base.controller.BaseController;
import com.blockchain.server.cct.controller.api.TradingDetailApi;
import com.blockchain.server.cct.dto.trading.DetailByOrderIdDTO;
import com.blockchain.server.cct.dto.trading.ListUserDetailDTO;
import com.blockchain.server.cct.service.TradingDetailService;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Api(TradingDetailApi.DETAIL_API)
@RestController
@RequestMapping("/detail")
public class TradingDetailController extends BaseController {

    @Autowired
    private TradingDetailService detailService;
    @Autowired
    private RedisTemplate redisTemplate;

    @ApiOperation(value = TradingDetailApi.listUserHistory.METHOD_TITLE_NAME,
            notes = TradingDetailApi.listUserHistory.METHOD_TITLE_NOTE)
    @GetMapping("/listUserDetail")
    public ResultDTO listUserHistory(
            @ApiParam(TradingDetailApi.listUserHistory.METHOD_API_COINNAME) @RequestParam(value = "coinName", required = false) String coinName,
            @ApiParam(TradingDetailApi.listUserHistory.METHOD_API_UNITNAME) @RequestParam(value = "unitName", required = false) String unitName,
            @ApiParam(TradingDetailApi.listUserHistory.METHOD_API_BEGINTIME) @RequestParam(value = "beginTime", required = false) String beginTime,
            @ApiParam(TradingDetailApi.listUserHistory.METHOD_API_LASTTIME) @RequestParam(value = "lastTime", required = false) String lastTime,
            @ApiParam(TradingDetailApi.listUserHistory.METHOD_API_STATUS) @RequestParam(value = "status", required = false) String status,
            @ApiParam(TradingDetailApi.listUserHistory.METHOD_API_PAGENUM) @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
            @ApiParam(TradingDetailApi.listUserHistory.METHOD_API_PAGESIZE) @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
            HttpServletRequest request) {
        String userId = SSOHelper.getUserId(redisTemplate, request);
        PageHelper.startPage(pageNum, pageSize);
        List<ListUserDetailDTO> userDetail = detailService.listUserDetail(userId, coinName, unitName, beginTime, lastTime, status);
        return ResultDTO.requstSuccess(userDetail);
    }

    @ApiOperation(value = TradingDetailApi.listDetailByOrderId.METHOD_TITLE_NAME,
            notes = TradingDetailApi.listDetailByOrderId.METHOD_TITLE_NOTE)
    @GetMapping("/listDetailByOrderId")
    public ResultDTO listDetailByOrderId(
            @ApiParam(TradingDetailApi.listDetailByOrderId.METHOD_API_ORDERID) @RequestParam("orderId") String orderId,
            @ApiParam(TradingDetailApi.listDetailByOrderId.METHOD_API_PAGENUM) @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
            @ApiParam(TradingDetailApi.listDetailByOrderId.METHOD_API_PAGESIZE) @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
            HttpServletRequest request) {
        //校验下token，实际不需要用到user
        SSOHelper.getUser(redisTemplate, request);
        PageHelper.startPage(pageNum, pageSize);
        List<DetailByOrderIdDTO> details = detailService.listDetailByOrderId(orderId);
        return ResultDTO.requstSuccess(details);
    }

    @ApiOperation(value = TradingDetailApi.pcListUserHistory.METHOD_TITLE_NAME,
            notes = TradingDetailApi.pcListUserHistory.METHOD_TITLE_NOTE)
    @GetMapping("/pcListUserDetail")
    public ResultDTO pcListUserHistory(
            @ApiParam(TradingDetailApi.pcListUserHistory.METHOD_API_COINNAME) @RequestParam(value = "coinName", required = false) String coinName,
            @ApiParam(TradingDetailApi.pcListUserHistory.METHOD_API_UNITNAME) @RequestParam(value = "unitName", required = false) String unitName,
            @ApiParam(TradingDetailApi.pcListUserHistory.METHOD_API_BEGINTIME) @RequestParam(value = "beginTime", required = false) String beginTime,
            @ApiParam(TradingDetailApi.pcListUserHistory.METHOD_API_LASTTIME) @RequestParam(value = "lastTime", required = false) String lastTime,
            @ApiParam(TradingDetailApi.pcListUserHistory.METHOD_API_STATUS) @RequestParam(value = "status", required = false) String status,
            @ApiParam(TradingDetailApi.pcListUserHistory.METHOD_API_PAGENUM) @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
            @ApiParam(TradingDetailApi.pcListUserHistory.METHOD_API_PAGESIZE) @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
            HttpServletRequest request) {
        String userId = SSOHelper.getUserId(redisTemplate, request);
        PageHelper.startPage(pageNum, pageSize);
        List<ListUserDetailDTO> userDetail = detailService.listUserDetail(userId, coinName, unitName, beginTime, lastTime, status);
        return generatePage(userDetail);
    }

    @ApiOperation(value = TradingDetailApi.pcListDetailByOrderId.METHOD_TITLE_NAME,
            notes = TradingDetailApi.pcListDetailByOrderId.METHOD_TITLE_NOTE)
    @GetMapping("/pcListDetailByOrderId")
    public ResultDTO pcListDetailByOrderId(
            @ApiParam(TradingDetailApi.pcListDetailByOrderId.METHOD_API_ORDERID) @RequestParam("orderId") String orderId,
            @ApiParam(TradingDetailApi.pcListDetailByOrderId.METHOD_API_PAGENUM) @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
            @ApiParam(TradingDetailApi.pcListDetailByOrderId.METHOD_API_PAGESIZE) @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
            HttpServletRequest request) {
        //校验下token，实际不需要用到user
        SSOHelper.getUser(redisTemplate, request);
        PageHelper.startPage(pageNum, pageSize);
        List<DetailByOrderIdDTO> details = detailService.listDetailByOrderId(orderId);
        return generatePage(details);
    }


}
