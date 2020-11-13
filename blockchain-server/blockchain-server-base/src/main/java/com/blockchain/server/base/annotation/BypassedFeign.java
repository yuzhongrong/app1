package com.blockchain.server.base.annotation;

import java.lang.annotation.*;

/***
 * 用于注解不需要处理feign返回值的类或方法
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BypassedFeign {
    String value() default "";
}
