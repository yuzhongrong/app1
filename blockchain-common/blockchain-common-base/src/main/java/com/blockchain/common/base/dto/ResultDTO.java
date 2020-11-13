package com.blockchain.common.base.dto;

import com.blockchain.common.base.constant.BaseConstant;
import com.blockchain.common.base.enums.BaseResultEnums;
import com.blockchain.common.base.util.HttpRequestUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 统一数据返回格式
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultDTO<T> extends BaseDTO {
    private int code;//返回码
    private String msg;//返回码描述
    private T data;//返回数据

    public static ResultDTO requstSuccess() {
        return requstSuccess(null);
    }

    public static ResultDTO requstSuccess(Object data) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String userLocale = BaseConstant.USER_LOCALE_DEFAULT;
        if (request != null) {
            userLocale = HttpRequestUtil.getUserLocale(request);
        }
        String msg;
        switch (userLocale) {
            case BaseConstant.USER_LOCALE_EN_US:
                msg = BaseResultEnums.SUCCESS.getEnMsg();
                break;
            case BaseConstant.USER_LOCALE_ZH_CN:
                msg = BaseResultEnums.SUCCESS.getCnmsg();
                break;
            default:
                msg = BaseResultEnums.SUCCESS.getHkmsg();
                break;
        }
        return new ResultDTO(BaseResultEnums.SUCCESS.getCode(), msg, data);
    }

    public static ResultDTO requestRejected(int code ,String msg ){

        return new ResultDTO(code, msg, null);
    }


}
