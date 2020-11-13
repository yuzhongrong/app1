package com.blockchain.server.base.aop;

import com.blockchain.common.base.constant.BaseConstant;
import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.common.base.enums.BaseResultEnums;
import com.blockchain.common.base.exception.BaseException;
import com.blockchain.common.base.exception.RPCException;
import com.blockchain.server.base.annotation.BypassedFeign;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class FeignAop {
    private Logger LOG = LoggerFactory.getLogger(getClass());

    //Feign调用成功返回码
    private static final int REQUEST_SUCCESS = BaseConstant.REQUEST_SUCCESS;

    /***
     * 设置切入点
     * 切所有模块的Feign包的所有方法
     */
    @Pointcut("execution(* com.blockchain.server.*.feign..*.*(..))")
    public void handleResultDTO() {
    }

    /***
     * 请求结束返回时处理
     * @param resultDTO
     */
    @AfterReturning(returning = "resultDTO", pointcut = "handleResultDTO()")
    public void doAfterReturning(JoinPoint joinPoint, ResultDTO resultDTO) {
        //调用的方法名
        String methodName = joinPoint.getSignature().getName();
        //调用的类名
        String simpleName = joinPoint.getTarget().getClass().getSimpleName();
        //获取方法对象
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        //判断该方法上注解是否存在
        boolean isAnno = method.isAnnotationPresent(BypassedFeign.class);
        LOG.debug(method.getName() + resultDTO.toString());
        //如果存在注解，则不处理
        if (isAnno) {
            LOG.error("BypassedFeign不处理的内部接口：返回信息{}，类名{}，方法名{}", resultDTO.toString(), simpleName, methodName);
            return;
        }
        //返回值为空
        if (resultDTO == null) {
            LOG.error("feign返回为空：类名{}，方法名{}", simpleName, methodName);
            throw new BaseException(BaseResultEnums.BUSY);
        }
        //RPC调用返回码不等于200，抛出异常
        if (resultDTO.getCode() != REQUEST_SUCCESS) {
            LOG.error("返回信息{}，类名{}，方法名{}", resultDTO.toString(), simpleName, methodName);
            throw new RPCException(resultDTO);
        }
    }

}
