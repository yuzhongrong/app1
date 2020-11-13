package com.blockchain.server.base.aop;

import com.blockchain.common.base.util.JsonUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class MethodAop {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Pointcut("execution(* com.blockchain.server.*.service.*.update*(..))," +
            "execution(* com.blockchain.server.*.service.*.delete*(..))")
    public void aopPoint() {
    }

    @Around("aopPoint()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        /** 获取方法名 */
        String methodName = joinPoint.getSignature().getName();
        logger.info("=================== {}方法开始执行 ===================", methodName);
        logger.info("=================== 执行的参数有:{} ===================", Arrays.toString(joinPoint.getArgs()));
        long start = System.currentTimeMillis();
        Object proceed = joinPoint.proceed();
        long end = System.currentTimeMillis();
        logger.info("=================== {}方法执行时使用了{}毫秒 ===================", methodName, (end - start));

        logger.info("=================== {}方法执行后返回的数据是{} ===================", JsonUtils.objectToJson(proceed));

        logger.info("=================== {}方法结束执行 ===================", methodName);

        return proceed;
    }

    @Pointcut("execution(* com.blockchain.server.*.service.*.*.*(..))")
    public void aopPoint2() {
    }

    @AfterThrowing(throwing = "ex", value = "aopPoint2()")
    public void errorAop(JoinPoint joinPoint, Throwable ex) {
        /* 出现异常的类名 */
        String className = joinPoint.getTarget().getClass().getSimpleName();
        /*出现异常的方法名*/
        String methodName = joinPoint.getSignature().getName();
        /*传递的参数*/
        Object[] args = joinPoint.getArgs();
        Object[] args1=null;
        //缩减打印
        if(args!=null&&args.length>50){
            args1=new Object[50];
            System.arraycopy(args,0,args1,0,50);
            args=args1;
        }
        //最大打印150长度
        for(int i=0;i<args.length;i++){
            if(args[i].toString().length()>150){
                args[i]= args[i].toString().substring(0, 150);
            }
        }
        /*异常类*/
        String exName = ex.getClass().getName();
        /*异常信息*/
        String exMsg = ex.getMessage();

        Object[] params = {className, methodName, exName, args, exMsg};

        logger.error("{}类的{}出现了{}异常，参数是:{}，异常信息是:{}", params);
//        logger.error(exMsg, ex);
    }

}
