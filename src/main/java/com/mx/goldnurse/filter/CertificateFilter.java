package com.mx.goldnurse.filter;

import com.alibaba.fastjson.JSON;
import com.mx.goldnurse.enums.ResponseEnum;
import com.mx.goldnurse.response.FailResponseData;
import com.mx.goldnurse.utils.CertificateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 凭证过滤器
 * <br>
 * created date 8.26 14:21
 *
 * @author DongJunHao
 */
@Slf4j
public class CertificateFilter implements Filter {

   /* private RedisClient redisClient;

    public CertificateFilter(RedisClient redisClient) {
        this.redisClient = redisClient;
    }*/

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        //获取请求头中的 applyId 和 certificate
        String applyId = request.getHeader("applyId");
        String certificate = request.getHeader("certificate");
        if (StringUtils.isEmpty(certificate) || StringUtils.isEmpty(applyId)) {
            log.error("[凭证过滤器]-[获取请求头applyId={} 和 certificate={}]-[获取失败]", applyId, certificate);
            return;
        }
        //校验凭证合法性
        String result = CertificateUtils.verify(applyId, certificate);
        if (null == result) {
            log.error("[凭证过滤器]-[凭证校验]-[校验失败]");
            response.setCharacterEncoding("UTF-8");
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            PrintWriter writer = response.getWriter();
            ResponseEnum certificateError = ResponseEnum.CERTIFICATE_ERROR;
            String body = JSON.toJSONString(new FailResponseData(certificateError.getCode(), certificateError.getMsg()));
            writer.println(body);
            writer.flush();
            writer.close();
            return;
        }
        //校验凭证状态是否正常
       /* Set<String> redisValues = redisClient.getValues(CERTIFICATE + applyId + "*");
        if (redisValues == null || redisValues.size() <= 0) {
            log.error("[凭证过滤器]-[校验凭证状态]-[该凭证已停用]");
            return;
        }
        if (!redisValues.contains(certificate)) {
            log.error("[凭证过滤器]-[校验凭证状态]-[该凭证已停用]");
            return;
        }*/
        //继续执行
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
