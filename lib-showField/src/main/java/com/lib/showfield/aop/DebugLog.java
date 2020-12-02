package com.lib.showfield.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *    author : JoeyChow
 *    time   : 2020/04/01
 *    desc   : Debug 日志注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.CONSTRUCTOR})
public @interface DebugLog {

    String value() default "DebugLog";
}