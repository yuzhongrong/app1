package com.blockchain.server.ltc.common.exception;

import com.blockchain.common.base.constant.BaseConstant;
import com.blockchain.common.base.exception.BaseException;
import com.blockchain.common.base.util.HttpRequestUtil;
import com.blockchain.server.ltc.common.enums.ExceptionEnums;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class ServiceException extends BaseException {

    public ServiceException(ExceptionEnums rs) {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        //可能是定时器调用，避免获取request空指针
        if (servletRequestAttributes == null) {
            this.code = rs.getCode();
            this.msg = rs.getCnmsg();
        } else {
            HttpServletRequest request = servletRequestAttributes.getRequest();
            String userLocale = HttpRequestUtil.getUserLocale(request);
            String msg = "";
            switch (userLocale) {
                case BaseConstant.USER_LOCALE_EN_US:
                    msg = rs.getEnMsg();
                    break;
                case BaseConstant.USER_LOCALE_ZH_HK:
                    msg = rs.getHkmsg();
                    break;
                default:
                    msg = rs.getCnmsg();
                    break;
            }
            this.code = rs.getCode();
            this.msg = msg;
        }
    }

}
