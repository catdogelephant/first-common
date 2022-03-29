package com.mx.goldnurse.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义幂等注解
 * <br>
 * created date 1.13 11:57
 *
 * @author DongJunHao
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiIdempotent {

    /**
     * 幂等名称，作为redis缓存Key的一部分。
     */
    String value() default "";

    /**
     * 幂等过期时间，即：在此时间段内，对API进行幂等处理。
     */
    int expireSeconds() default 3;
}
