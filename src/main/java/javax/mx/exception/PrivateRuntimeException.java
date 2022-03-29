package javax.mx.exception;

import com.mx.goldnurse.enums.ResponseEnum;
import com.mx.goldnurse.exception.AbstractBaseExceptionEnum;
import com.mx.goldnurse.utils.MxMessage;
import lombok.Getter;

/**
 * @author peishaopeng
 * @version 1.0.0
 * @ClassName RuntimeExption
 * @data 2021/3/6 11:38 下午
 */
@Getter
public class PrivateRuntimeException extends RuntimeException {
    /**
     * 错误码
     */
    private Integer code;

    /**
     * 日志信息
     */
    private String logMsg;

    public PrivateRuntimeException() {
        super(ResponseEnum.SERVER_ERROR.getMsg());
        this.code = ResponseEnum.SERVER_ERROR.getCode();
        this.logMsg = ResponseEnum.SERVER_ERROR.getMsg();
    }

    public PrivateRuntimeException(AbstractBaseExceptionEnum abstractBaseExceptionEnum) {
        super(abstractBaseExceptionEnum.getMsg());
        this.code = abstractBaseExceptionEnum.getCode();
        this.logMsg = abstractBaseExceptionEnum.getMsg();
    }

    public PrivateRuntimeException(AbstractBaseExceptionEnum abstractBaseExceptionEnum, String logMsg) {
        super(abstractBaseExceptionEnum.getMsg());
        this.code = abstractBaseExceptionEnum.getCode();
        this.logMsg = logMsg;
    }

    public PrivateRuntimeException(String message) {
        super(message);
        this.code = ResponseEnum.FAIL.getCode();
        this.logMsg = message;
    }

    public PrivateRuntimeException(Integer code) {
        super(MxMessage.getMsg(code));
        this.code = code;
        this.logMsg = MxMessage.getMsg(code);
    }
}
