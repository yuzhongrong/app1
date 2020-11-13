package com.blockchain.server.sysconf.service;

import com.blockchain.server.sysconf.dto.AdSliderDto;

import java.util.List;

public interface AdSliderService {

    /**
     * 获取首页轮播广告图片
     *
     * @return
     */
    List<AdSliderDto> listAdSliderForApp();

}
