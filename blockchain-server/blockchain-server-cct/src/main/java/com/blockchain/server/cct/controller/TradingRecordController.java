package com.blockchain.server.cct.controller;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.server.cct.common.redistool.RedisTool;
import com.blockchain.server.cct.controller.api.TradingRecordApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(TradingRecordApi.RECORD_API)
@RestController
@RequestMapping("/record")
public class TradingRecordController {

    @Autowired
    private RedisTool redisTool;

    @ApiOperation(value = TradingRecordApi.listRecordByCoinAndUnit.METHOD_TITLE_NAME,
            notes = TradingRecordApi.listRecordByCoinAndUnit.METHOD_TITLE_NOTE)
    @GetMapping("/listRecord")
    public ResultDTO listRecordByCoinAndUnit(
            @ApiParam(TradingRecordApi.listRecordByCoinAndUnit.METHOD_API_COINNAME) @RequestParam("coinName") String coinName,
            @ApiParam(TradingRecordApi.listRecordByCoinAndUnit.METHOD_API_UNITNAME) @RequestParam("unitName") String unitName,
            @ApiParam(TradingRecordApi.listRecordByCoinAndUnit.METHOD_API_PAGENUM) @RequestParam(value = "pageNum", required = false, defaultValue = "0") Integer pageNum,
            @ApiParam(TradingRecordApi.listRecordByCoinAndUnit.METHOD_API_PAGESIZE) @RequestParam(value = "pageSize", required = false, defaultValue = "1") Integer pageSize) {
        //查询最新成交历史
        List tradingRecords = redisTool.listRecord(coinName, unitName, pageNum, pageSize);
        return ResultDTO.requstSuccess(tradingRecords);
    }
}
