package com.mx.goldnurse.enums;

import com.mx.goldnurse.exception.AbstractBaseExceptionEnum;
import lombok.Getter;


/**
 * @author peishaopeng
 * @version 1.0.0
 * @ClassName ResponseEnum
 * @Description TODO
 * @data 2021/3/6 11:12 下午
 */
@Getter
public enum ResponseEnum implements AbstractBaseExceptionEnum {
    /** success */
    SUCCESS(200, "操作成功"),

    /** FAIL */
    FAIL(0, "操作失败"),


    /** REMIND，提示性异常信息 */
    REMIND(101, "操作失败"),

    /** 登陆失败 */
    LOGIN_FAIL(102, "用户名或密码错误"),
    /** token 失效 */
    TOKEN_FAIL(103, "你的认证信息已失效，请重新登陆"),


    /** 内部应用错误 */
    SERVER_ERROR(104, "网络异常，请稍后再试"),

    CERTIFICATE_ERROR(105, "凭证校验失败"),

    ;

    private final Integer code;

    private final String msg;

    ResponseEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
