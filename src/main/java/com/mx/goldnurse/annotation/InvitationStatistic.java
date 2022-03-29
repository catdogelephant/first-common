package com.mx.goldnurse.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 邀请有礼统计
 * <br>
 * created date 2021/7/20 15:53
 *
 * @author sx
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface InvitationStatistic {
}
