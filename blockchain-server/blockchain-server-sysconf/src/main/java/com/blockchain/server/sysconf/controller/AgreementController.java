package com.blockchain.server.sysconf.controller;


import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.common.base.util.HttpRequestUtil;
import com.blockchain.server.sysconf.common.constants.AgreementConstant;
import com.blockchain.server.sysconf.controller.api.AgreementApi;
import com.blockchain.server.sysconf.controller.api.SysconfImageApi;
import com.blockchain.server.sysconf.entity.Agreement;
import com.blockchain.server.sysconf.service.AgreementService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


@Api(AgreementApi.AGREEMENT_API)
@RestController
@RequestMapping("/agreement")
public class AgreementController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AgreementController.class);

    @Autowired
    private AgreementService agreementService;
    /**
     * 查询用户协议  (客户端）
     * @return
     */
    @ApiOperation(value = AgreementApi.FINDAGREEMENT.METHOD_TITLE_NAME, notes = AgreementApi.FINDAGREEMENT.METHOD_TITLE_NOTE)
    @RequestMapping(value = "/findAgreement", method = RequestMethod.GET)
    public ResultDTO findAgreement(@ApiParam(SysconfImageApi.SystemImageList.METHOD_API_TYPE) @RequestParam(value = "type", defaultValue = AgreementConstant.TYPE_USER, required = false) String type, HttpServletRequest request){
        Agreement agreement = agreementService.findAgreement(type,HttpRequestUtil.getUserLocale(request));
        return ResultDTO.requstSuccess(agreement);
    }

}
