package com.blockchain.server.user.mapper;

import com.blockchain.server.user.entity.SmsCount;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.Date;

@Repository
public interface SmsCountMapper extends Mapper<SmsCount> {

    /**
     * 获取某个日期的获取短信记录
     * @param phone 手机号
     * @param smsType 类型
     * @param smsDate 日期
     * @return
     */
    SmsCount getSmsRecordByPhoneAndTypeOfDay(@Param("phone") String phone,
                                             @Param("smsType") String smsType,
                                             @Param("smsDate") Date smsDate);

    /**
     * 更新手机获取短信记录+1
     * @param phone 手机号
     * @param smsType 类型
     * @param count 原来的数量
     * @return 影响的行数
     */
    int updateIncrCountByPhoneAndTypeInRowLock(@Param("phone") String phone,
                                               @Param("smsType") String smsType,
                                               @Param("count") int count);
}
