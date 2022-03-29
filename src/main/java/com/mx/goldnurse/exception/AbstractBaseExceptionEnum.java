package com.mx.goldnurse.exception;

/**
 * @author peishaopeng
 */
public interface AbstractBaseExceptionEnum {

    /**
     * 获取状态码
     */
    Integer getCode();

    /**
     * 获取 message
     */
    String getMsg();

}
