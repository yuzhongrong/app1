package com.blockchain.server.tron.service.impl;

import com.blockchain.server.tron.common.constant.TronConstant;
import com.blockchain.server.tron.entity.TronInform;
import com.blockchain.server.tron.mapper.TronInformMapper;
import com.blockchain.server.tron.service.TronInformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 请求通知——业务接口
 *
 * @author YH
 * @date 2018年12月5日10:21:27
 */
@Service("informService")
public class TronInformServiceImpl implements TronInformService {

    @Autowired
    private TronInformMapper tronInformMapper;

    @Override
    public int insert(String paramsId, String paramsJson, String url, String informType) {
        TronInform inform = new TronInform();
        inform.setId(UUID.randomUUID().toString());
        inform.setParamsId(paramsId);
        inform.setParamsJson(paramsJson);
        inform.setStatus(TronConstant.InformConstant.STATUS_PEND);
        inform.setUrl(url);
        inform.setTime(0);
        inform.setCreateTime(new Date());
        inform.setUpdateTime(new Date());
        inform.setInformType(TronConstant.InformConstant.TYPE_PAY);
        return tronInformMapper.insertSelective(inform);
    }

    @Override
    public List<TronInform> selectByInformTypePendingAll(String informType, int time) {
        return tronInformMapper.selectByTypeAndTime(informType, time);
    }

    @Override
    public int updateTimeInRowLock(String id) {
        return tronInformMapper.updateTimeInRowLock(id);
    }
}
