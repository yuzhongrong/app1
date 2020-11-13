package com.blockchain.server.user.service.impl;

import com.blockchain.common.base.enums.BaseResultEnums;
import com.blockchain.common.base.exception.BaseException;
import com.blockchain.server.user.common.enums.SmsCountEnum;
import com.blockchain.server.user.common.enums.UserEnums;
import com.blockchain.server.user.common.exceprion.UserException;
import com.blockchain.server.user.common.utils.CheckUtils;
import com.blockchain.server.user.common.utils.SmsCodeUtils;
import com.blockchain.server.user.entity.SmsCount;
import com.blockchain.server.user.mapper.SmsCountMapper;
import com.blockchain.server.user.service.SmsCountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

@Service
public class SmsCountServiceImpl implements SmsCountService {
    private static final Logger LOG = LoggerFactory.getLogger(SmsCountService.class);

    @Autowired
    private SmsCountMapper smsCountMapper;
    @Autowired
    private SmsCodeUtils smsCodeUtils;

    @Override
    @Transactional
    public void handleInsertSmsCode(String phone, String internationalCode,SmsCountEnum smsCountEnum) {
        //校验手机号
        if (!CheckUtils.checkMobilePhone(phone)) {
            throw new UserException(UserEnums.PHONE_FORMAT_ERROR);
        }
        //校验短信类型
        if (smsCountEnum == null) {
            throw new UserException(UserEnums.VERIFY_CODE_TYPE_ERROR);
        }
        SmsCount smsCount = smsCountMapper.getSmsRecordByPhoneAndTypeOfDay(phone, smsCountEnum.getType(), new Date());
        if (smsCount == null) {
            insertOneRecord(phone, smsCountEnum.getType());//插入一条新记录
        } else {
            incrCountRecord(smsCount, smsCountEnum.getMaxCount());//获取短信次数+1
        }
        smsCodeUtils.sendSmsCodeAndStoreToCache(phone, internationalCode,smsCountEnum);
    }

    /**
     * 插入一条新纪录
     */
    private void insertOneRecord(String phone, String type) {
        SmsCount smsCount = new SmsCount();
        smsCount.setId(UUID.randomUUID().toString());
        smsCount.setPhone(phone);
        smsCount.setSmsCount(1);
        smsCount.setSmsType(type);
        smsCount.setSmsDate(new Date());
        smsCountMapper.insert(smsCount);
    }

    /**
     * 获取短信次数+1
     */
    private void incrCountRecord(SmsCount smsCount, int maxCount) {
        int count = smsCount.getSmsCount();
        if (count + 1 > maxCount) {//检查是否大于获取短信数量限制

          LOG.error("-----------------》》今日获取验证码到达上限",smsCount);
          throw new UserException(UserEnums.VERIFY_CODE_OVER_COUNT);
        }
        //数量自增1
        int row = smsCountMapper.updateIncrCountByPhoneAndTypeInRowLock(smsCount.getPhone(), smsCount.getSmsType(), count);
        if (row < 1) {
            throw new BaseException(BaseResultEnums.BUSY);
        }
    }



}
