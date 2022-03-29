package com.mx.goldnurse.response;


import com.mx.goldnurse.enums.ResponseEnum;

/**
 * @author peishaopeng
 * @version 1.0.0
 * @ClassName FailResponseData
 * @data 2021/3/7 5:14 下午
 */
public class FailResponseData extends ResponseData {

    public FailResponseData() {
        super(ResponseEnum.FAIL.getCode(),ResponseEnum.FAIL.getMsg(),null);
    }

    public FailResponseData(Object o) {
        super(ResponseEnum.FAIL,o);
    }

    public FailResponseData(String msg) {
        super(ResponseEnum.FAIL.getCode(), msg,null);
    }

    public FailResponseData(Integer code, String msg) {
        super(code == null ? ResponseEnum.FAIL.getCode() : code, msg,null);
    }

}
