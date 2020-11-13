package com.blockchain.server.sysconf.controller;


import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.common.base.util.HttpRequestUtil;
import com.blockchain.server.sysconf.common.constants.ContactUsConstant;
import com.blockchain.server.sysconf.controller.api.ContactUsApi;
import com.blockchain.server.sysconf.entity.ContactUs;
import com.blockchain.server.sysconf.service.ContactUsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Api(ContactUsApi.CONTACTUS_API)
@RestController
@RequestMapping("/contactUs")
public class ContactUsController {
    @Autowired
    private ContactUsService contactUsService;

    /**
     * 查询联系我们信息列表
     *
     * @return
     */
    @ApiOperation(value = ContactUsApi.FINDCONTACTUSALL.METHOD_TITLE_NAME, notes = ContactUsApi.FINDCONTACTUSALL.METHOD_TITLE_NOTE)
    @RequestMapping(value = "/findContactUsAll", method = RequestMethod.POST)
    public ResultDTO findContactUsAll(HttpServletRequest request) {
        String userLocale = HttpRequestUtil.getUserLocale(request);
        List<ContactUs> contactUsList = contactUsService.listAll(ContactUsConstant.STATUS_SHOW, userLocale);
        return ResultDTO.requstSuccess(contactUsList);
    }
}
