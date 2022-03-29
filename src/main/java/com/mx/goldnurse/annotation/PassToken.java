package com.mx.goldnurse.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author peishaopeng
 * @version 1.0.0
 * @ClassName PassToken
 * @Description 跳过 token 校验
 * @data 2021/3/6 7:21 下午
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface PassToken {

    boolean required() default true;

    /**
     * 如果需要拦截整个controller，将该注解放置在类上，并设置为true
     */
    boolean isAll() default false;
}
