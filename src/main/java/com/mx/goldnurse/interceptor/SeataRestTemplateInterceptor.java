//package com.mx.goldnurse.interceptor;
//
//import io.seata.core.context.RootContext;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.http.HttpRequest;
//import org.springframework.http.client.ClientHttpRequestExecution;
//import org.springframework.http.client.ClientHttpRequestInterceptor;
//import org.springframework.http.client.ClientHttpResponse;
//import org.springframework.http.client.support.HttpRequestWrapper;
//
//import java.io.IOException;
//
///**
// * seata控制器
// * <br>
// * created date 2020/12/14 19:48
// *
// * @author DongJunHao
// */
//@SuppressWarnings("NullableProblems")
//public class SeataRestTemplateInterceptor implements ClientHttpRequestInterceptor {
//    public SeataRestTemplateInterceptor() {
//    }
//
//    @Override
//    public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] bytes, ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {
//        HttpRequestWrapper requestWrapper = new HttpRequestWrapper(httpRequest);
//        String xid = RootContext.getXID();
//        if (StringUtils.isNotEmpty(xid)) {
//            requestWrapper.getHeaders().add(RootContext.KEY_XID, xid);
//        }
//
//        return clientHttpRequestExecution.execute(requestWrapper, bytes);
//    }
//}
