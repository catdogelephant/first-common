package com.mx.goldnurse.exception;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.mx.goldnurse.constants.ConstantsUtils;
import com.mx.goldnurse.enums.ResponseEnum;
import com.mx.goldnurse.mq.rocket.producer.ProducerUtil;
import com.mx.goldnurse.response.FailResponseData;
import com.mx.goldnurse.utils.JacksonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.mx.exception.PrivateCaptureException;
import javax.mx.exception.PrivateRuntimeException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * @author peishaopeng
 * @version 1.0.0
 * @ClassName ExceptionHandlerAdvice
 * @Description TODO
 * @data 2021/3/7 5:19 下午
 */
@ControllerAdvice
public class ExceptionHandlerAdvice {
    private static final Logger log = LoggerFactory.getLogger(ExceptionHandlerAdvice.class);


    /**
     * 通知状态
     */
    @Value("${DingTalk.exceptionNotice.enable:false}")
    private boolean noticeEnable;

    /**
     * 异常通知域名
     */
    @Value("${DingTalk.exceptionNotice.domain:http://lan.resource.goldnurse.com/errorlog/}")
    private String domain;

    /**
     * 错误日志存放路径
     */
    @Value("${DingTalk.exceptionNotice.errorLogPath:/nas/errorlog/}")
    private String filePath;

    /**
     * 是否@所有人
     */
    @Value("${DingTalk.exceptionNotice.isAtAll:false}")
    private boolean isAtAll;

    private static List<String> atMobileArr;

    @Autowired
    private ProducerUtil producer;

    /**
     * 需要@的用户
     */
    @Value("${DingTalk.exceptionNotice.atMobiles:null}")
    private void setNoticeEnable(String atMobiles) {
        if (StrUtil.isBlank(atMobiles)) {
            return;
        }
        atMobileArr = Arrays.asList(atMobiles.split(","));
    }

    /**
     * AccessToken
     */
    @Value("${DingTalk.exceptionNotice.accessToken:dc683ceb1004ea926dcae5d13e8f3a844ef621fc5bf932298e88b1299fcde01a}")
    private String accessToken;
    /**
     * ketword
     */
    @Value("${DingTalk.exceptionNotice.keyword:测试环境}")
    private String keyword;
    /**
     * serverName
     */
    @Value("${DingTalk.exceptionNotice.serverName:demo}")
    private String serverName;

    private final String ERROR_LOG_TOPIC = "goldnurse-tools-manage-news-topic";


    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({Exception.class})
    @ResponseBody
    public FailResponseData exception(HttpServletRequest request, Exception e) {
        log.error("异常路径: " + request.getRequestURL());
        log.error("异常信息: ", e);
        if (noticeEnable) {
            ErrorLog errorLog = ExceptionNoticeUtils.notice(request, e, domain, filePath, accessToken, keyword, serverName, isAtAll, atMobileArr);
            producer.sendMsg(ERROR_LOG_TOPIC, "error_log", JSON.toJSONString(errorLog).getBytes(StandardCharsets.UTF_8), "error_log");
        }
        return new FailResponseData();
    }


    @ExceptionHandler({PrivateCaptureException.class})
    @ResponseBody
    public FailResponseData handleCaptureException(HttpServletRequest request, PrivateCaptureException e) {
        return new FailResponseData(e.getCode(), e.getMessage());
    }


    @ExceptionHandler({PrivateRuntimeException.class})
    @ResponseBody
    public FailResponseData handleRuntimeException(HttpServletRequest request, PrivateRuntimeException e) {
        log.error("异常路径: " + request.getRequestURL());
        log.error("异常信息: code: {} , logMsg : {} , msg: {} ", e.getCode(), e.getLogMsg(), e.getMessage());
        return new FailResponseData(e.getCode() == null ? ResponseEnum.FAIL.getCode() : e.getCode(), e.getMessage());
    }


    /**
     * 请求的form表单参数校验
     *
     * @param e 异常信息
     * @return 返回数据
     */
    @ExceptionHandler({BindException.class})
    @ResponseBody
    public FailResponseData handleBindException(HttpServletRequest request, BindException e) {
        log.error("请求地址:" + request.getRequestURL().toString());
        log.error("请求类方法参数:" + JacksonUtil.toJson(request.getParameterMap()));
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append("{");
        for (ObjectError fieldError : e.getBindingResult().getAllErrors()) {
            strBuilder.append(fieldError.getDefaultMessage()).append(",");
        }
        strBuilder.append("}");
        log.error(strBuilder.toString());
        return new FailResponseData(strBuilder.toString());
    }

    /**
     * 请求的form表单参数校验
     *
     * @param e 异常信息
     * @return 返回数据
     */
    @ExceptionHandler({ConstraintViolationException.class})
    @ResponseBody
    public FailResponseData handleBindException1(HttpServletRequest request, ConstraintViolationException e) {
        log.error("请求地址:" + request.getRequestURL().toString());
        log.error("请求类方法参数:" + JacksonUtil.toJson(request.getParameterMap()));
        return constraintViolationException(e);
    }

    /**
     * 缺少请求参数错误
     *
     * @param e 异常信息
     * @return 返回数据
     */
    @ExceptionHandler({MissingServletRequestParameterException.class})
    @ResponseBody
    public FailResponseData missingServletRequestParameterException(HttpServletRequest request, MissingServletRequestParameterException e) {
        log.error("参数({})缺失 {} {}", e.getParameterName(), request.getMethod(), request.getRequestURL().toString());
        return new FailResponseData(ConstantsUtils.MISSING_SERVLET_REQUEST_PARAMETER_EXCEPTION);
    }

    /**
     * 参数类型不匹配错误
     *
     * @param e 异常信息
     * @return 返回数据
     */
    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    @ResponseBody
    public FailResponseData methodArgumentTypeMismatchException(HttpServletRequest request, MethodArgumentTypeMismatchException e) {
        log.error("参数({}={})类型({})不匹配 {} {}", e.getName(), e.getValue(), e.getRequiredType(), request.getMethod(), request.getRequestURL().toString());
        return new FailResponseData(ConstantsUtils.METHOD_ARGUMENT_TYPE_MISMATCH_EXCEPTION);
    }

    /**
     * 请求方法类型错误
     *
     * @param e 异常信息
     * @return 返回数据
     */
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    @ResponseBody
    public FailResponseData httpRequestMethodNotSupportedException(HttpServletRequest request, HttpRequestMethodNotSupportedException e) {
        log.error("请求方法({})不支持 {}", request.getMethod(), request.getRequestURL().toString());
        return new FailResponseData(ConstantsUtils.HTTP_REQUEST_METHOD_NOT_SUPPORTED_EXCEPTION);
    }

    private FailResponseData constraintViolationException(ConstraintViolationException ex) {
        Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
        if (constraintViolations.isEmpty()) {
            return new FailResponseData(ResponseEnum.SERVER_ERROR);
        }
        StringBuilder sb = new StringBuilder();

        constraintViolations.forEach(constraintViolation -> {
            // 把错误信息循环放到sb中, 并以逗号隔开
            sb.append(constraintViolation.getPropertyPath().toString().split("\\.")[1])
                    .append(" ")
                    .append(constraintViolation.getMessage())
                    .append(",");
        });
        log.error("参数校验信息：" + sb.toString());
        ConstraintViolation<?> next = constraintViolations.iterator().next();
        String message = next.getMessage();
        return new FailResponseData(message);
    }

    /**
     * 请求的 JSON 参数在请求体内的参数校验
     *
     * @param e 异常信息
     * @return 返回数据
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public FailResponseData handleBindException1(MethodArgumentNotValidException e) {
        StringBuilder strBuilder = new StringBuilder();
        for (ObjectError fieldError : e.getBindingResult().getAllErrors()) {
            strBuilder.append(fieldError.getDefaultMessage()).append("\n");
            return new FailResponseData(fieldError.getDefaultMessage());
        }
        log.error(strBuilder.toString());
        return new FailResponseData("参数错误");
    }
}
