package com.blockchain.server.sysconf.common.exception;

import com.blockchain.common.base.constant.BaseConstant;
import com.blockchain.common.base.exception.BaseException;
import com.blockchain.common.base.util.HttpRequestUtil;
import com.blockchain.server.sysconf.common.enums.SysConfigEnums;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author huangxl
 * @create 2018-11-22 09:29
 */
public class SysConfigException extends BaseException {

    public SysConfigException(SysConfigEnums rs) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String userLocale = HttpRequestUtil.getUserLocale(request);
        String msg;
        switch (userLocale) {
            case BaseConstant.USER_LOCALE_EN_US:
                msg = rs.getEnMsg();
                break;
            default:
                msg = rs.getMsg();
                break;
        }
        this.code = rs.getCode();
        this.msg = msg;
    }
}
