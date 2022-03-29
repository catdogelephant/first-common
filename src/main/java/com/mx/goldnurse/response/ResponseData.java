package com.mx.goldnurse.response;

import com.mx.goldnurse.exception.AbstractBaseExceptionEnum;
import lombok.Getter;

import java.io.Serializable;

/**
 * @author peishaopeng
 * @version 1.0.0
 * @ClassName ResponseData
 * @data 2021/3/7 4:29 下午
 */
@Getter
public class ResponseData implements Serializable {

    private Integer resultcode;

    private String msg;

    private Object result;

    public ResponseData(AbstractBaseExceptionEnum abstractBaseExceptionEnum) {
        this.resultcode = abstractBaseExceptionEnum.getCode();
        this.msg = abstractBaseExceptionEnum.getMsg();
    }


    public ResponseData(AbstractBaseExceptionEnum abstractBaseExceptionEnum, Object o) {
        this.resultcode = abstractBaseExceptionEnum.getCode();
        this.msg = abstractBaseExceptionEnum.getMsg();
        this.result = o;
    }

    protected ResponseData(Integer resultcode, String msg, Object result) {
        this.resultcode = resultcode;
        this.msg = msg;
        this.result = result;
    }



}
