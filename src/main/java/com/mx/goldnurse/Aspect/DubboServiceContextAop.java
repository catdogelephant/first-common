package com.mx.goldnurse.Aspect;

import com.google.common.base.Stopwatch;
import com.mx.goldnurse.utils.ArrayUtils;
import com.mx.goldnurse.utils.JacksonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.rpc.RpcContext;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

import static com.mx.goldnurse.consts.UserConst.TOKEN_USER_ID;
import static com.mx.goldnurse.consts.UserConst.TOKEN_USER_NAME;

/**
 * dubbo Service层切面
 * <br>
 * created date 9.5 17:44
 *
 * @author DongJunHao
 */
@Slf4j
@Aspect
@Component
public class DubboServiceContextAop {

    @Pointcut("execution(* com..*Impl.*(..))")
    public void serviceApi() {

    }

    @Around("serviceApi()")
    public Object dubboContext(ProceedingJoinPoint point) throws Throwable {
        Object[] objects = point.getArgs();
        Signature signature = point.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        RpcContext rpcContext = RpcContext.getContext();
        String[] parameterNames = methodSignature.getParameterNames();
        int tokenUserIdIndex = ArrayUtils.indexOf(parameterNames, TOKEN_USER_ID);
        int tokenUserNameIndex = ArrayUtils.indexOf(parameterNames, TOKEN_USER_NAME);
        if (tokenUserIdIndex != -1 && tokenUserNameIndex != -1) {
            String tokenUserId = String.valueOf(objects[tokenUserIdIndex]);
            String tokenUserName = String.valueOf(objects[tokenUserNameIndex]);
            System.out.println("切面线程名称:" + Thread.currentThread().getName());
            rpcContext.setAttachment(TOKEN_USER_ID, tokenUserId);
            rpcContext.setAttachment(TOKEN_USER_NAME, tokenUserName);
        }
        return printDubboRequestLog(rpcContext, point, methodSignature);
    }

    /**
     * 打印dubbo请求日志
     *
     * @param rpcContext dubbo上下文
     */
    private Object printDubboRequestLog(RpcContext rpcContext, ProceedingJoinPoint point, MethodSignature methodSignature) throws Throwable {
        if (rpcContext == null) return point.proceed(point.getArgs());
        if (StringUtils.isBlank(rpcContext.getLocalAddressString()) || StringUtils.isBlank(rpcContext.getRemoteAddressString()))
            return point.proceed(point.getArgs());
        if (rpcContext.getRemoteAddressString().contains("null"))
            return point.proceed(point.getArgs());
        if (rpcContext.getLocalAddressString().equals(rpcContext.getRemoteAddressString()))
            return point.proceed(point.getArgs());
        Stopwatch stopwatch = Stopwatch.createStarted();
        log.info("========dubbo调用开始========");
        log.info("调用方:{}", rpcContext.getRemoteAddressString());
        log.info("调用方法:{}", methodSignature.getMethod().getName());
        log.info("参数:{}", JacksonUtil.toJson(rpcContext.getArguments()));
        Object proceed = point.proceed(point.getArgs());
        log.info("被调用方:{}", rpcContext.getLocalAddressString());
        log.info("返回参数:{}", JacksonUtil.toJson(proceed));
        log.info("========dubbo调用结束========");
        log.info("========耗时:{}========", stopwatch.stop().elapsed(TimeUnit.MILLISECONDS) + "(毫秒).");
        return proceed;
    }
}
