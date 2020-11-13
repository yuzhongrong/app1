package com.blockchain.server.sysconf.service.impl;

import com.blockchain.server.sysconf.dto.AdSliderDto;
import com.blockchain.server.sysconf.mapper.AdSliderMapper;
import com.blockchain.server.sysconf.service.AdSliderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdSliderServiceImpl implements AdSliderService {

    @Autowired
    private AdSliderMapper adSliderMapper;

    @Override
    public List<AdSliderDto> listAdSliderForApp() {
        return adSliderMapper.listAdSliderForApp();
    }

}
