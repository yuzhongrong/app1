package com.blockchain.server.sysconf.mapper;

import com.blockchain.server.sysconf.dto.AdSliderDto;
import com.blockchain.server.sysconf.entity.AdSlider;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 首页广告轮播 数据层
 *
 * @author ruoyi
 * @date 2018-09-03
 */
@Repository
public interface AdSliderMapper extends Mapper<AdSlider> {

    /**
     * 获取首页轮播广告图片
     *
     * @return
     */
    List<AdSliderDto> listAdSliderForApp();

}