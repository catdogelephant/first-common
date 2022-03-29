package com.mx.goldnurse.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 护理服务中心切换自定义注解
 * <br>
 * created date 2019/11/4 16:44
 *
 * @author dongjunhao
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SwitchWork {

    String workId() default "";

    String ip() default "";

    String tokenUserId() default "";
}
