package com.mx.goldnurse.utils;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Key生成工具
 * <br>
 * created date 2021/1/12 12:00
 *
 * @author DongJunHao
 */
@Slf4j
public class KeyUtil {

    /**
     * 根据{方法名 + 参数列表}和md5转换生成key
     *
     * @param method 方法
     * @param args   参数
     * @return key
     */
    public static String generate(Method method, Object... args) {
        StringBuilder sb = new StringBuilder(method.toString());
        for (Object arg : args) {
            if (arg instanceof HttpServletRequest || arg instanceof HttpServletResponse) {
                continue;
            }
            sb.append(toString(arg));
        }
        String s = sb.toString();
        log.info("参数:" + s);
        return toMd5Hash(s);
    }

    private static String toString(Object object) {
        if (object == null) {
            return "null";
        }
        if (object instanceof Number) {
            return object.toString();
        }
        //调用json工具类转换成String
        return JSON.toJSONString(object);
    }

    /**
     * md5加密
     *
     * @param str 加密字符串
     * @return 加密之后的字符串
     */
    private static String toMd5Hash(String str) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(str.getBytes());
            byte[] bytes = md5.digest();

            StringBuilder buffer = new StringBuilder("");
            for (int aByte : bytes) {
                int i = aByte;
                if (i < 0) {
                    i += 256;
                }
                if (i < 16) {
                    buffer.append("0");
                }
                buffer.append(Integer.toHexString(i));
            }
            return buffer.toString();
        } catch (NoSuchAlgorithmException e) {
            log.error("MD5加密异常!", e);
            return null;
        }
    }
}
