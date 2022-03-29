package com.mx.goldnurse.jwt;


import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;
import cn.hutool.json.JSONObject;
import com.mx.goldnurse.utils.JacksonUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Date;

/**
 * @author peishaopeng
 * @version 1.0.0
 * @ClassName JwtUtils
 * @data 2021/3/7 7:36 下午
 */
public class JwtUtil {
    private static final Logger log = LoggerFactory.getLogger(JwtUtil.class);
//
//    /**
//     * 过期时间ms
//     *
//     */
//    private static final long EXPIRE_TIME = 1000L * 60 * 60 * 24 * 31;

    /**
     * jwt 密钥
     */
//    private static final String SECRET = "!|?+iyk7q<ani5|LLU9f7crz!BY{^yrX*nv(fC~AVOtM9jRB)5Afh:bBNM8osKz5<hX+Od4w3XxaGpe*@<{4)u@{{mpj#YPP0&9K";


    /**
     * token 加密串
     */
    private static final String DEFAULT_TOKEN_SECRET = "JinpaiGoldnur&&%%UUEIIWKKKXLLLSLLIELLWLLS$KKEKK#JENXJJKWKMMXKKWLLSLLLE";


    /**
     * JWT 第二段信息密钥
     */
    private static final byte[] THE_SECOND_MESSAGE_KEY = {96, -76, 32, -69, 56, 81, -39, -44, 122, -53, -109, 61, -66, 112, 57, -101};

    /**
     * AES
     **/
    private static final AES AES = SecureUtil.aes(THE_SECOND_MESSAGE_KEY);

    /**
     * 生成签名
     *
     * @param userId
     * @return
     */
    public static String getToken(JSONObject subject, String tokenSecret, long validPeriod) {
        if (subject.get("userId") == null || subject.get("userName") == null) {
            throw new JwtException("JSONObject 中,必须包含 userId 和 userName");
        }

        log.debug("subject = {} ,expireTime = {}", subject, validPeriod);
        if (validPeriod < 1) {
            throw new JwtException("expireTime 不能小于 1");
        }

        //随机值
        subject.append("rn", RandomUtil.randomString(8));
        long now = System.currentTimeMillis();
        JwtBuilder jwtBuilder = Jwts.builder().setIssuer("goldnurse.com")
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + validPeriod))
                .setSubject(Arrays.toString(AES.encrypt(JacksonUtil.toJson(subject))))
                .signWith(SignatureAlgorithm.HS256, StrUtil.isBlank(tokenSecret) ? DEFAULT_TOKEN_SECRET : tokenSecret);
        return jwtBuilder.compact();
    }

    /**
     * 根据token获取userId<br/>
     * 失效的token也会正常获取
     * @param token
     * @return
     */
//    public static String getUserId(String token) {
//        try {
//            return JWT.decode(token).getAudience().get(0);
//        } catch (JWTDecodeException e) {
//            return null;
//        }
//    }

    /**
     * 校验token
     * @param token
     * @return
     */
    public static TokenUser checkSign(String token,String userId,String tokenSecret) {

        JSONObject jsonObject;
        try {
            Claims body = Jwts.parser()
                    .setSigningKey(StrUtil.isBlank(tokenSecret) ? DEFAULT_TOKEN_SECRET : tokenSecret)
                    .parseClaimsJws(token)
                    .getBody();
            String subject = body.getSubject();
            subject = new String(AES.decrypt(strToByte(subject)));
            jsonObject = JacksonUtil.toEntity(subject, JSONObject.class);
        } catch (Exception e) {
            return null;
        }
        if (jsonObject == null
                || jsonObject.get("userId") == null
                || jsonObject.get("userName") == null
                || ! jsonObject.get("userId").toString().equals(userId)){
            log.info("TOKEN 信息无效");
            return null;
        }
        TokenUser tokenUser = new TokenUser();
        tokenUser.setUserId(userId);
        tokenUser.setUserName(jsonObject.get("userName").toString());
        tokenUser.setSubject(jsonObject);
        return tokenUser;
    }


    /**
     * 仅此可用<br/>
     * String str = "[-115, -109, -14]";<br/>
     * ⬇<br/>
     * byte[] bytes = {-115, -109, -14};
     **/
    private static byte[] strToByte(String str) {
        str = StrUtil.sub(str, 1, str.length() - 1).replace(" ", "");
        String[] split = str.split(",");
        byte[] bytes = new byte[split.length];
        for (int i = 0; i < split.length; i++) {
            bytes[i] = Byte.parseByte(split[i]);
        }
        return bytes;
    }
}
