package com.blockchain.server.tron.service.impl;

import com.blockchain.common.base.util.ExceptionPreconditionUtils;
import com.blockchain.server.tron.common.enums.TronWalletEnums;
import com.blockchain.server.tron.common.exception.TronWalletException;
import com.blockchain.server.tron.entity.TronApply;
import com.blockchain.server.tron.mapper.TronApplyMapper;
import com.blockchain.server.tron.service.TronApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 应用信息——业务接口
 *
 * @author YH
 * @date 2018年12月5日10:21:27
 */
@Service("applyService")
public class TronApplyServiceImpl implements TronApplyService {
    @Autowired
    private TronApplyMapper tronApplyMapper;

    @Override
    public TronApply findByAppidAndAppSecret(String appid, String appSecret) {
        ExceptionPreconditionUtils.checkStringNotBlank(appid, new TronWalletException(TronWalletEnums.APPLY_APPIDNULL_ERROR));
        ExceptionPreconditionUtils.checkStringNotBlank(appSecret, new TronWalletException(TronWalletEnums.APPLY_APPSECRETNULL_ERROR));
        TronApply whereApply = new TronApply();
        whereApply.setAppId(appid);
        whereApply.setAppSecret(appSecret);
        return tronApplyMapper.selectOne(whereApply);
    }
}
