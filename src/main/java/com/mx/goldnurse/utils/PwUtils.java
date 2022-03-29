package com.mx.goldnurse.utils;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;

import javax.mx.exception.PrivateCaptureException;

/**
 * 密码处理
 * MD5加密原始明文密码 -> 将结果再次MD5，-> 结果 append userId 后再次MD5 <br/>
 * MD5(MD5(MD5("password")) + "userId")
 *
 * @author peishaopeng
 * @version 1.0.0
 * @ClassName PwUtils
 * @data 2020/11/26 5:05 下午
 */
public class PwUtils {

    /**
     * @param password 明文密码
     * @param salt     盐🧂
     * @Return: java.lang.String
     * @Author: peishaopeng
     * @Date: 2020/11/26
     **/
    public static String processPw(String password, String salt) {
        if (StrUtil.hasBlank(password, salt)) {
            throw new PrivateCaptureException("参数错误");
        }
        return SecureUtil.md5(SecureUtil.md5(SecureUtil.md5(password)) + salt);
    }

    /**
     * @param password 明文密码
     * @param secret   密文密码
     * @param salt     盐🧂
     * @Return: boolean
     * @Author: peishaopeng
     * @Date: 2020/11/26
     **/
    public static boolean checkPw(String password, String secret, String salt) {
        if (StrUtil.hasBlank(password, secret, salt)) {
            throw new PrivateCaptureException("参数错误");
        }
        return secret.equals(processPw(password, salt));
    }

}
