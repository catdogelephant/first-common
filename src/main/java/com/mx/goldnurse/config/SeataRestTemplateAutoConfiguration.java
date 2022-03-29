//package com.mx.goldnurse.config;
//
//import com.mx.goldnurse.interceptor.SeataRestTemplateInterceptor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.client.ClientHttpRequestInterceptor;
//import org.springframework.web.client.RestTemplate;
//
//import javax.annotation.PostConstruct;
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.List;
//
///**
// * seata配置类
// * <br>
// * created date 2020/12/14 19:47
// *
// * @author DongJunHao
// */
//@SuppressWarnings("SpringFacetCodeInspection")
//@Configuration
//public class SeataRestTemplateAutoConfiguration {
//
//    @Autowired(required = false)
//    private Collection<RestTemplate> restTemplates;
//    @Autowired
//    private SeataRestTemplateInterceptor seataRestTemplateInterceptor;
//
//    public SeataRestTemplateAutoConfiguration() {
//    }
//
//    @Bean
//    public SeataRestTemplateInterceptor seataRestTemplateInterceptor() {
//        return new SeataRestTemplateInterceptor();
//    }
//
//    @PostConstruct
//    public void init() {
//        if (this.restTemplates != null) {
//            for (RestTemplate restTemplate : this.restTemplates) {
//                List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>(restTemplate.getInterceptors());
//                interceptors.add(this.seataRestTemplateInterceptor);
//                restTemplate.setInterceptors(interceptors);
//            }
//        }
//
//    }
//}
