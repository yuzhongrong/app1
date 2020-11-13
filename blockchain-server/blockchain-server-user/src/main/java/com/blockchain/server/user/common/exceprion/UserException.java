package com.blockchain.server.user.common.exceprion;

import com.blockchain.common.base.constant.BaseConstant;
import com.blockchain.common.base.exception.BaseException;
import com.blockchain.common.base.util.HttpRequestUtil;
import com.blockchain.server.user.common.enums.UserEnums;
import lombok.Data;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author huangxl
 * @data 2019/2/21 20:51
 */
@Data
public class UserException extends BaseException {
    protected int code;
    protected String msg;

    public UserException(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public UserException(UserEnums rs) {
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
