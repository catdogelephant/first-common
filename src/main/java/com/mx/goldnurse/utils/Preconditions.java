package com.mx.goldnurse.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.Nullable;

import javax.mx.exception.PrivateRuntimeException;

/**
 * <br>
 * created date 2021/8/17 10:08
 *
 * @author JiangYuhao
 */
public class Preconditions {
    private Preconditions() {

    }

    private static PrivateRuntimeException EXCEPTION = new PrivateRuntimeException();

    /**
     * 验证字段是否为null或者字符串为空
     * <br>
     * created date 2021/8/17 10:11
     *
     * @author JiangYuhao
     */
    public static void checkArgument(Object obj) {
        boolean expression = false;
        if (obj == null) {
            expression = true;
        } else if (obj instanceof String && StringUtils.isBlank(obj.toString())) {
            expression = true;
        }
        if (expression) {
            throw EXCEPTION;
        }
    }

    /**
     * 验证字段是否为null或者字符串为空
     * <br>
     * created date 2021/8/17 10:11
     *
     * @author JiangYuhao
     */
    public static void checkArgument(Object obj, @Nullable String errorMessage) {
        boolean expression = false;
        if (obj == null) {
            expression = true;
        } else if (obj instanceof String && StringUtils.isBlank(obj.toString())) {
            expression = true;
        }
        if (expression && errorMessage == null) {
            throw EXCEPTION;
        }
        if (expression) {
            throw new PrivateRuntimeException(errorMessage);
        }
    }

    /**
     * 验证字段是否为null或者字符串为空
     * <br>
     * created date 2021/8/17 10:11
     *
     * @author JiangYuhao
     */
    public static void checkArgument(Object obj, int code) {
        boolean expression = false;
        if (obj == null) {
            expression = true;
        } else if (obj instanceof String && StringUtils.isBlank(obj.toString())) {
            expression = true;
        }
        if (expression) {
            throw new PrivateRuntimeException(code);
        }
    }

    /**
     * true抛出异常
     * <br>
     * created date 2021/8/17 10:34
     *
     * @author JiangYuhao
     */
    public static void checkArgument(boolean expression, @Nullable String errorMessage) {
        if (expression && errorMessage == null) {
            throw EXCEPTION;
        }
        if (expression) {
            throw new PrivateRuntimeException(errorMessage);
        }
    }

    /**
     * true抛出异常
     * <br>
     * created date 2021/8/17 10:34
     *
     * @author JiangYuhao
     */
    public static void checkArgumentThrowCode(boolean expression, Integer code) {
        if (expression) {
            throw new PrivateRuntimeException(code);
        }
    }

}
