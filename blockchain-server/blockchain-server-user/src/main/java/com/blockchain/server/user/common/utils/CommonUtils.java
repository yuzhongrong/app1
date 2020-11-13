package com.blockchain.server.user.common.utils;

import com.blockchain.server.user.controller.LoginController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 存放一些规则性的方法
 *
 * @author huangxl
 * @create 2019-02-23 20:38
 */
public class CommonUtils {
    private static final Logger LOG = LoggerFactory.getLogger(CommonUtils.class);

    /**
     * 通过邀请码获取自增码
     *
     * @param invitationCode 邀请码
     */
    public static Integer getUserIncrCodeFromInvitationCode(String invitationCode) {
        try {
            Integer number = Integer.parseInt(invitationCode, 16);
            String numberStr = number .toString();
            String substring = numberStr.substring(2);
            return Integer.parseInt(substring);
        } catch (Exception e) {
            LOG.error("邀请码获取自增码异常",e);
            return null;
        }
    }

    /**
     * 根据自增码和随机数值生成邀请码
     *
     * @param incrCode     自增码
     * @param randomNumber 随机数字
     * @return
     */
    public static String generateInvitationCode(int incrCode, Integer randomNumber) {
        if (randomNumber == null) {
            return null;
        }
        try {
            String hexStr = randomNumber .toString() + incrCode;
            Integer hexNumber = Integer.parseInt(hexStr);
            return Integer.toHexString(hexNumber);
        } catch (Exception e) {
            LOG.error(" 根据自增码和随机数值生成邀请码",e);
            return null;
        }
    }

}
