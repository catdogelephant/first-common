package com.mx.goldnurse.utils;

import cn.hutool.core.util.IdUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 凭证 【生成 or 校验】 工具类
 * <br>
 * created date 8.26 14:26
 *
 * @author DongJunHao
 */
@Slf4j
public class CertificateUtils {

    /**
     * 加密串
     */
    private static String CERTIFICATE_SECRET = "aa2b3649e4904cc0a13b98555d5382a8";

    /**
     * 根据应用Id生成凭证
     *
     * @param applyId   应用Id
     * @param startTime 有效开始时间
     * @param endTime   有效结束时间
     * @return 返回凭证
     */
    public static String sign(String applyId, String startTime, String endTime) {
        log.info("applyId={},startTime={},endTime={}", applyId, startTime, endTime);
        if (StringUtils.isEmpty(applyId)) {
            log.error(" applyId is null");
            return null;
        }
        if (StringUtils.isEmpty(startTime)) {
            log.error(" startTime is null");
            return null;
        }
        if (StringUtils.isEmpty(endTime)) {
            log.error(" endTime is null");
            return null;
        }
        String certificate = null;
        try {
            Date startDate = strToDate(startTime);
            Date endDate = strToDate(endTime);
            if (null == startDate || null == endDate) {
                return null;
            }
            certificate = JWT.create()
                    .withIssuer(applyId)
                    .withIssuedAt(startDate)
                    .withExpiresAt(endDate)
                    .withNotBefore(startDate)
                    .sign(Algorithm.HMAC256(CERTIFICATE_SECRET));
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("sign: applyId={},certificate={}", applyId, certificate);
        return certificate;
    }

    /**
     * 凭证校验
     *
     * @param applyId     应用Id
     * @param certificate 凭证
     * @return 返回应用Id
     */
    public static String verify(String applyId, String certificate) {
        log.info("applyId={},certificate={}", applyId, certificate);
        if (StringUtils.isEmpty(applyId)) {
            log.error(" applyId is null");
            return null;
        }
        if (StringUtils.isEmpty(certificate)) {
            log.error(" certificate is null");
            return null;
        }
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(CERTIFICATE_SECRET)).withIssuer(applyId).build();
            DecodedJWT jwt = verifier.verify(certificate);
            return jwt.getIssuer();
        } catch (Exception e) {
            log.error("凭证校验失败");
        }
        return null;
    }

    /**
     * 日期字符串转日期
     *
     * @param dateTime 日期字符串
     * @return 日期
     */
    private static Date strToDate(String dateTime) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return format.parse(dateTime);
        } catch (ParseException e) {
            log.error("凭证工具类日期转换失败");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 生成凭证应用以及凭证到tools库的certificate_apply和certificate表
     */
    public static void demo() {
        long ertificateId = IdUtil.getSnowflake(1, 1).nextId();
        long certificateApplyId = IdUtil.getSnowflake(1, 1).nextId();
        String channelId = "27c7cbeacee94619a340cb4f636b274a";
        String creatorIdTest = "69eb93ca172d4972a7ab15280357ddad";
        String creatorIdOnline = "4d667c787adb4727aece766ed8cd4593";
        String applyId = channelId;
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDateTime yearDateTime1 = localDateTime.plusYears(1);

        String startTime = df.format(localDateTime);
        String endTime = df.format(yearDateTime1);
        String sign = sign(applyId, startTime, endTime);

        System.out.println("certificateApplyId=" + certificateApplyId);
        System.out.println("ertificateId=" + ertificateId);
        System.out.println("applyId=" + applyId);
        System.out.println("startTime=" + startTime);
        System.out.println("endTime=" + endTime);
        System.out.println("sign=" + sign);
        System.out.println("creatorIdTest=" + creatorIdTest);
        System.out.println("creatorIdOnline=" + creatorIdOnline);
        verify("27c7cbeacee94619a340cb4f636b274a", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJuYmYiOjE2MzQxODQ3MDcsImlzcyI6IjI3YzdjYmVhY2VlOTQ2MTlhMzQwY2I0ZjYzNmIyNzRhIiwiZXhwIjoxNjY1NzIwNzA3LCJpYXQiOjE2MzQxODQ3MDd9.vPhbXP3GngEC6oJuII3xky-leYWPS_GEdqFxv4kjnKI");
    }


    public static void main(String[] args) {
        demo();
    }
}
