package com.mx.goldnurse.jwt;

import cn.hutool.core.util.StrUtil;
import com.mx.goldnurse.annotation.PassToken;
import com.mx.goldnurse.enums.ResponseEnum;
import com.mx.goldnurse.utils.RequestLocal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import javax.mx.exception.PrivateCaptureException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author peishaopeng
 * @version 1.0.0
 * @ClassName JwtInterceptor
 * @Description jwt拦截器
 * @data 2021/3/7 8:24 下午
 */
public class JwtInterceptor implements HandlerInterceptor, WebMvcConfigurer {

    private static final Logger log = LoggerFactory.getLogger(JwtInterceptor.class);

    private String tokenSecret;

    private TokenUser tokenUser;

    @Resource
    private TokenUserArgumentResolver tokenUserArgumentResolver;

    @Value("${jwt.tokenEnable:true}")
    private boolean tokenEnable;

    public JwtInterceptor(String tokenSecret) {
        this.tokenSecret = tokenSecret;
    }

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) {
        StringBuffer requestURL = httpServletRequest.getRequestURL();
        // 从 http 请求头中取出 token
        String token = httpServletRequest.getHeader("token");
        String userId = httpServletRequest.getHeader("userId");
        String requestMethod = httpServletRequest.getMethod();

        if (!tokenEnable) {
            return true;
        }

        // 如果不是映射到方法直接通过
        if (!(object instanceof HandlerMethod)) {
            log.info("未映射到方法     [放行请求] 地址：[{}] {}", requestMethod, requestURL);
            return true;
        }
        log.info("用户ID       [放行请求] 地址：[{}] {}, userId={}", requestMethod, requestURL, userId);
        HandlerMethod handlerMethod = (HandlerMethod) object;
        Method method = handlerMethod.getMethod();
        //判断类上是否存在PassToken注解
        PassToken passToken = method.getDeclaringClass().getAnnotation(PassToken.class);
        if (passToken != null && passToken.isAll()) {
            log.info("注解 PassToken [放行请求] 地址：[{}] {}", requestMethod, requestURL);
            return true;
        }
        // 有 PassToken 注解时，无需校验
        if (method.isAnnotationPresent(PassToken.class)) {
            log.info("注解 PassToken [放行请求] 地址：[{}] {}", requestMethod, requestURL);
            return true;
        }
        if (StrUtil.isBlank(userId)) {
            log.info("未查询到userId   [拦截请求] 地址：[{}] {}", requestMethod, requestURL);
            throw new PrivateCaptureException(ResponseEnum.TOKEN_FAIL);
        }
        // 获取 token 中的 userId
//        String userId = JwtUtil.getUserId(token);
        // 验证 token
        tokenUser = JwtUtil.checkSign(token, userId, tokenSecret);
        if (tokenUser == null) {
            log.info("TOKEN 验证失败  [拦截请求] 地址：[{}] {}", requestMethod, requestURL);
            throw new PrivateCaptureException(ResponseEnum.TOKEN_FAIL);
        }
        httpServletRequest.setAttribute("tokenUser", tokenUser);
        RequestLocal.setTokenUserId(tokenUser.getUserId());
        RequestLocal.setTokenUserName(tokenUser.getUserName());
        RequestLocal.setTokenUser(tokenUser);
        log.info("TOKEN 验证成功  [放行请求] 地址：[{}] {}", requestMethod, requestURL);
        return true;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
//        WebMvcConfigurer.super.addArgumentResolvers(resolvers);
        resolvers.add(tokenUserArgumentResolver);
    }
}
