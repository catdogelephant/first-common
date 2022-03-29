package com.mx.goldnurse.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author erBai
 * @version 1.0.0
 * @ClassName TokenUserArgumentResolver
 * @Description TODO
 * @data 4/12/21 11:40
 */
@Component
public class TokenUserArgumentResolver implements HandlerMethodArgumentResolver {

    @Value("${jwt.tokenSecret}")
    private String tokenSecret;

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        Class<?> clazz = methodParameter.getParameterType();
        return clazz == TokenUser.class;
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
        HttpServletResponse response = nativeWebRequest.getNativeResponse(HttpServletResponse.class);
        // 从 http 请求头中取出 token
        String token = request.getHeader("token");
        String userId = request.getHeader("userId");
        return JwtUtil.checkSign(token, userId, tokenSecret);
    }
}
