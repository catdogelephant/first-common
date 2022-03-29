package com.mx.goldnurse.response;


import com.mx.goldnurse.enums.ResponseEnum;
import com.mx.goldnurse.utils.MxMessage;

/**
 * @author peishaopeng
 * @version 1.0.0
 * @ClassName SuccessResponseData
 * @data 2021/3/7 4:38 下午
 */
public class SuccessResponseData extends ResponseData {

    public SuccessResponseData() {
        super(ResponseEnum.SUCCESS.getCode(),ResponseEnum.SUCCESS.getMsg(),null);
    }

    public SuccessResponseData(Object o) {
        super(ResponseEnum.SUCCESS,o);
    }

    public SuccessResponseData(String msg) {
        super(ResponseEnum.SUCCESS.getCode(), msg,null);
    }

    public SuccessResponseData(Integer code) {
        super(code, MxMessage.getMsg(code),null);
    }

}
