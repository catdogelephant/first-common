package javax.mx.exception;

import com.mx.goldnurse.enums.ResponseEnum;
import com.mx.goldnurse.exception.AbstractBaseExceptionEnum;
import lombok.Getter;

/**
 * @author peishaopeng
 * @version 1.0.0
 * @ClassName CaptureException
 * @Description 会被全局异常捕获，并返回异常信息的异常
 * @data 2021/3/6
 */
@Getter
public class PrivateCaptureException extends RuntimeException {

    private Integer code;

    public PrivateCaptureException(AbstractBaseExceptionEnum abstractBaseExceptionEnum) {
        super(abstractBaseExceptionEnum.getMsg());
        this.code = abstractBaseExceptionEnum.getCode();
    }

    public PrivateCaptureException(String message) {
        super(message);
        this.code = ResponseEnum.REMIND.getCode();
    }
}
