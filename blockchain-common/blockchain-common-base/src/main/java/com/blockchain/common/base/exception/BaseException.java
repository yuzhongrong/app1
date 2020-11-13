package com.blockchain.common.base.exception;

import com.blockchain.common.base.constant.BaseConstant;
import com.blockchain.common.base.enums.BaseResultEnums;
import com.blockchain.common.base.util.HttpRequestUtil;
import lombok.Data;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 自定义基础异常，用于向上跑出可预知异常
 *
 * @author huangxl
 * @create 2018-11-12 11:09
 */
@Data
public class BaseException extends RuntimeException {
    protected int code;
    protected String msg;

    public BaseException() {
        this(BaseResultEnums.DEFAULT);
    }

    public BaseException(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public BaseException(BaseResultEnums rs) {
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