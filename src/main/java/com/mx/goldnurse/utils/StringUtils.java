package com.mx.goldnurse.utils;

import com.mx.goldnurse.consts.CommonConst;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 * <br>
 * created date 2021/10/17 14:30
 *
 * @author sx
 */
public class StringUtils {

    /**
     * 判断字符串是否为字母和字符串
     */
    private static final Pattern PATTERN = Pattern.compile("[0-9a-zA-Z]{1,}");

    /**
     * 判断传入的字符串是否为大写字母（传入字符串必须是英文字母组成的字符串）
     *
     * @param str 被校验字符串
     * @return 如果字符串全部由大写字母组成，则返回true；否则返回false
     */
    public static boolean isUpperCaseStr(String str) {
        for (int i = 0; i < str.length(); i++) {
            int j = str.charAt(i);
            if (j < 65 || j > 90) {
                return false;
            }
        }
        return true;
    }

    /**
     * 获取脱敏姓名
     *
     * @param name 姓名
     */
    public static String getUnsensitiveName(String name) {
        if (org.apache.commons.lang3.StringUtils.isBlank(name)) {
            return "**";
        }
        if (name.trim().length() <= 2 && name.trim().length() > 0) {
            return name.trim().charAt(0) + "*";
        }
        if (name.trim().length() > 2) {
            return name.trim().charAt(0) + "*" + name.trim().substring((name.trim().length() - 1));
        }
        return "**";
    }

    /**
     * 字符串判空
     *
     * @param val
     * @return
     */
    public static Boolean isNotBlank(String val) {
        if (val == null || CommonConst.EMPTY_STRING.equals(val) ||
                CommonConst.EMPTY_STRING.equals(val.trim()) ||
                CommonConst.NULL_STRING.equals(val.trim().toLowerCase()) ||
                CommonConst.UNDEFINED_STRING.equals(val.trim().toLowerCase())) {
            return false;
        }
        return true;
    }

    /**
     * 功能描述：判断字符串是否为字母和字符串
     *
     * @param
     * @return
     * @Author GaoPeng
     * @Date 2020/9/3
     **/
    public static boolean mxIsAlphaNumeric(String s) {
        Matcher m = PATTERN.matcher(s);
        return m.matches();
    }

    public static void main(String[] args) {
        System.out.println(isNotBlank(null));
        System.out.println(isNotBlank(""));
        System.out.println(isNotBlank("  "));
        System.out.println(isNotBlank("null"));
        System.out.println(isNotBlank("UNDEFINED"));
        System.out.println(isNotBlank("undefined"));
        System.out.println(isNotBlank("main"));
    }
}
