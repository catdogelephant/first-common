package com.mx.goldnurse.config;

import com.mx.goldnurse.jwt.JwtInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author peishaopeng
 * @version 1.0.0
 * @ClassName WebConfig
 * @Description TODO
 * @data 2021/3/7 8:39 下午
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter implements WebMvcConfigurer {

    @Value("${jwt.tokenSecret}")
    private String tokenSecret;

    @Value("${jwt.passToken.patterns:null}")
    private String[] passTokenPatterns;

    /**
     * 添加jwt拦截器
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration registration = registry.addInterceptor(jwtInterceptor());
        //拦截请求
        registration.addPathPatterns("/**");
        //放行请求
        if (passTokenPatterns != null) {
            registration.excludePathPatterns(passTokenPatterns);
        }
    }

    /**
     * jwt拦截器
     *
     * @return
     */
    @Bean
    public JwtInterceptor jwtInterceptor() {
        return new JwtInterceptor(tokenSecret);
    }

    /**
     * 允许跨域请求
     **/
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE")
                .maxAge(3600)
                .allowCredentials(true);
    }
}
