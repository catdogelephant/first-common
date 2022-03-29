package com.mx.goldnurse.Aspect;

import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import com.mx.goldnurse.annotation.NotPackage;
import com.mx.goldnurse.annotation.PassToken;
import com.mx.goldnurse.jwt.TokenUser;
import com.mx.goldnurse.response.SuccessResponseData;
import com.mx.goldnurse.utils.JacksonUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * @author peishaopeng
 * @version 1.0.0
 * @ClassName ControllerAspect
 * @Description 控制器切面
 * @data 2021/3/7 9:09 下午
 */
@Order(-1)
@Component
@Aspect
public class ControllerAspect {

    @Resource
    private HttpServletRequest request;

    private static final Logger log = LoggerFactory.getLogger(ControllerAspect.class);

    @Around("execution(* com..*Controller.*(..))")
    public Object handleControllerMethod(ProceedingJoinPoint pjp) throws Throwable {
        Class<?> clazz = pjp.getTarget().getClass();
        NotPackage notPackage = clazz.getAnnotation(NotPackage.class);
        PassToken classPassToken = clazz.getAnnotation(PassToken.class);
        Stopwatch stopwatch = Stopwatch.createStarted();
        Signature signature = pjp.getSignature();
        Method method = ((MethodSignature) signature).getMethod();

        //赋值tokenUserId 和 tokenUserName
        Object[] objects = pjp.getArgs();

        PassToken passToken = method.getAnnotation(PassToken.class);
        if (passToken == null && classPassToken == null) {
            MethodSignature methodSignature = (MethodSignature) signature;
            String[] parameterNames = methodSignature.getParameterNames();
            int tokenUserIdIndex = ArrayUtils.indexOf(parameterNames, "tokenUserId");
            int tokenUserNameIndex = ArrayUtils.indexOf(parameterNames, "tokenUserName");
            TokenUser tokenUser = (TokenUser) request.getAttribute("tokenUser");
            if (tokenUserIdIndex != -1) {
                objects[tokenUserIdIndex] = tokenUser.getUserId();
            }
            if (tokenUserNameIndex != -1) {
                objects[tokenUserNameIndex] = tokenUser.getUserName();
            }
        }
        log.info("执行Controller开始: {}", signature);
        log.info("参数：{}", Lists.newArrayList(pjp.getArgs()).toString());
        Object proceed = pjp.proceed(pjp.getArgs());
        SuccessResponseData responseData = new SuccessResponseData(proceed);
        log.info("执行Controller结束responseData: {}", JacksonUtil.toJson(responseData));
        log.info("耗时：" + stopwatch.stop().elapsed(TimeUnit.MILLISECONDS) + "(毫秒).");

        Object target = pjp.getTarget();
        String className = target.getClass().getName();
        if (!className.startsWith("com.mx.")) {
            log.info("执行Controller结束proceed: {}", JacksonUtil.toJson(proceed));
            return proceed;
        }
        //判断类上是否有注解
        if (notPackage != null) return proceed;

        //判断方法上是否有注解
        notPackage = method.getAnnotation(NotPackage.class);
        if (notPackage != null) return proceed;
        return responseData;
    }
}
