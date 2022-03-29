package com.mx.goldnurse.config;

import com.mx.goldnurse.filter.CertificateFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;

/**
 * 凭证过滤器配置
 * <br>
 * created date 8.26 14:21
 *
 * @author DongJunHao
 */
@Configuration
@ConditionalOnProperty(prefix = "goldnurse.tools.config.enable", name = "certificateFilter", havingValue = "true")
public class CertificateFilterConfig {

    @Value("${goldnurse.tools.config.certificate.verify.urlPatterns}")
    private String[] urlPatterns;

   /* @Resource
    private RedisClient redisClient;*/

    @Bean
    public FilterRegistrationBean<Filter> certificateFilter() {
        FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<>();
        registration.setDispatcherTypes(DispatcherType.REQUEST);
        registration.setFilter(new CertificateFilter());
        registration.addUrlPatterns(urlPatterns);
        registration.setName("CertificateFilter");
        registration.setOrder(10);
        return registration;
    }
}

