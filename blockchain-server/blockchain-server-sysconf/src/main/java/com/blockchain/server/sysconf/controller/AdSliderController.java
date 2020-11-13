package com.blockchain.server.sysconf.controller;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.server.sysconf.controller.api.AdSliderApi;
import com.blockchain.server.sysconf.service.AdSliderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(AdSliderApi.AD_SLIDER_API)
@RestController
@RequestMapping("/adSlider")
public class AdSliderController {

    @Autowired
    private AdSliderService adSliderService;

    /**
     * 获取首页轮播图列表
     *
     * @return
     */
    @ApiOperation(value = AdSliderApi.ListAdSlider.METHOD_TITLE_NAME, notes = AdSliderApi.ListAdSlider.METHOD_TITLE_NOTE)
    @GetMapping("/listAdSlider")
    public ResultDTO listAdSlider() {
        return ResultDTO.requstSuccess(adSliderService.listAdSliderForApp());
    }

}
