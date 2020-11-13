package com.blockchain.server.otc.service.impl;

import com.blockchain.server.otc.entity.AppealImg;
import com.blockchain.server.otc.mapper.AppealImgMapper;
import com.blockchain.server.otc.service.AppealImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

@Service
public class AppealImgServiceImpl implements AppealImgService {

    @Autowired
    private AppealImgMapper appealImgMapper;

    @Override
    @Transactional
    public int insertAppealImg(String appealDetailId, String appealImgUrl) {
        AppealImg appealImg = new AppealImg();
        appealImg.setId(UUID.randomUUID().toString());
        appealImg.setAppealDetailId(appealDetailId);
        appealImg.setAppealImgUrl(appealImgUrl);
        appealImg.setCreateTime(new Date());
        return appealImgMapper.insertSelective(appealImg);
    }
}
