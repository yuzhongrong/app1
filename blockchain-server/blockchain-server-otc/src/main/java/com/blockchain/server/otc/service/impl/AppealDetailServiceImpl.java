package com.blockchain.server.otc.service.impl;

import com.blockchain.server.otc.entity.AppealDetail;
import com.blockchain.server.otc.mapper.AppealDetailMapper;
import com.blockchain.server.otc.service.AppealDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

@Service
public class AppealDetailServiceImpl implements AppealDetailService {

    @Autowired
    private AppealDetailMapper appealDetailMapper;

    @Override
    @Transactional
    public String insertAppealDetail(String userId, String appealId, String role, String remark) {
        AppealDetail appealDetail = new AppealDetail();
        String appealDetialId = UUID.randomUUID().toString();
        appealDetail.setId(appealDetialId);
        appealDetail.setAppealId(appealId);
        appealDetail.setUserId(userId);
        appealDetail.setAppealRole(role);
        appealDetail.setRemark(remark);
        appealDetail.setCreateTime(new Date());
        appealDetailMapper.insertSelective(appealDetail);
        return appealDetialId;
    }
}
