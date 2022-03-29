package com.mx.goldnurse.constants;

import java.util.regex.Pattern;

/**
 * @description: 数值常量类
 * @author: dgl
 * @time: 2020/12/10 14:41
 */
public class ConstantsUtils {
    /**数值常量*/
    public static final Integer CODE_0 = 0;
    public static final Integer CODE_1 = 1;
    public static final Integer CODE_2 = 2;
    public static final Integer CODE_3 = 3;
    public static final Integer CODE_4 = 4;
    public static final Integer CODE_5 = 5;
    public static final Integer CODE_6 = 6;
    public static final Integer CODE_7 = 7;
    public static final Integer CODE_8 = 8;
    public static final Integer CODE_9 = 9;
    public static final Integer CODE_10 = 10;
    public static final Integer CODE_11 = 11;
    public static final Integer CODE_12 = 12;
    public static final Integer CODE_13 = 13;
    public static final Integer CODE_14 = 14;
    public static final Integer CODE_15 = 15;
    public static final Integer CODE_16 = 16;
    public static final Integer CODE_17 = 17;
    public static final Integer CODE_18 = 18;
    public static final Integer CODE_19 = 19;
    public static final Integer CODE_20 = 20;
    public static final Integer CODE_21 = 21;
    public static final Integer CODE_22 = 22;
    public static final Integer CODE_23 = 23;
    public static final Integer CODE_24 = 24;
    public static final Integer CODE_25 = 25;
    public static final Integer CODE_26 = 26;
    public static final Integer CODE_27 = 27;
    public static final Integer CODE_28 = 28;
    public static final Integer CODE_29 = 29;
    public static final Integer CODE_30 = 30;
    public static final Integer CODE_40 = 40;
    public static final Integer CODE_50 = 50;
    public static final Integer CODE_60 = 60;
    public static final Integer CODE_70 = 70;
    public static final Integer CODE_80 = 80;
    public static final Integer CODE_90 = 90;
    public static final Integer CODE_100 = 100;
    public static final Integer CODE_200 = 200;
    public static final Integer CODE_300 = 300;
    public static final Integer CODE_400 = 400;
    public static final Integer CODE_500 = 500;
    public static final Integer CODE_600 = 600;
    public static final Integer CODE_700 = 700;
    public static final Integer CODE_800 = 800;
    public static final Integer CODE_900 = 900;
    public static final Integer CODE_1000 = 1000;
    public static final Integer CODE_2000 = 2000;
    public static final Integer CODE_3000 = 3000;
    public static final Integer CODE_4000 = 4000;
    public static final Integer CODE_5000 = 5000;
    public static final Integer CODE_6000 = 6000;
    public static final Integer CODE_7000 = 7000;
    public static final Integer CODE_8000 = 8000;
    public static final Integer CODE_9000 = 9000;
    public static final Integer CODE_10000 = 10000;
    public static final Integer CODE_999999999 = 999999999;
    public static final Integer CODE_MINUS_1 = -1;
    public static final Integer CODE_MINUS_2 = -2;
    public static final Integer CODE_MINUS_3 = -3;
    public static final Integer CODE_MINUS_4 = -4;
    public static final Integer CODE_MINUS_5 = -5;
    public static final Integer CODE_MINUS_6 = -6;
    public static final Integer CODE_MINUS_7 = -7;
    public static final Integer CODE_MINUS_8 = -8;
    public static final Integer CODE_MINUS_9 = -9;

    /**String常量*/
    public static final String STR_MINUS_1 = "-1";
    public static final String STR_CODE_0 = "0";
    public static final String STR_CODE_1 = "1";
    public static final String STR_CODE_2 = "2";
    public static final String STR_CODE_3 = "3";
    public static final String STR_CODE_4 = "4";
    public static final String STR_CODE_5 = "5";
    public static final String STR_CODE_6 = "6";
    public static final String STR_CODE_7 = "7";
    public static final String STR_CODE_8 = "8";
    public static final String STR_CODE_9 = "9";
    public static final String comma = ",";
    public static final String STR_0_01 = "0.01";
    public static final String STR_999999_99 = "999999.99";
    public static final String STR_9999999_99 = "9999999.99";
    public static final String STR_99999999_99 = "99999999.99";
    public static final String STR_999999999_99 = "999999999.99";

    /**
     * 正则常量：部分表达式没有验证准确性，请在使用时自行验证，或补充纠正
     */
    /** 验证单价小数点后最多为2位 */
    public static final Pattern CHECK_WORK_MILEAGE = Pattern.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$");
    public static final String REGEX_MOBILE = "((\\+86|0086)?\\s*)((134[0-8]\\d{7})|(((13([0-3]|[5-9]))|(14[5-9])|15([0-3]|[5-9])|(16(2|[5-7]))|17([0-3]|[5-8])|18[0-9]|19(1|[8-9]))\\d{8})|(14(0|1|4)0\\d{7})|(1740([0-5]|[6-9]|[10-12])\\d{7}))";
    public static final String REGEX_IDCARD_18 = "^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$";
    public static final String REGEX_IDCARD_15 = "^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{2}$";
    public static final String REGEX_IDCARD_15_18 = "(^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{2}$)";
    /** 汉字、数字、英文字母 但不包括下划线等符号 */
    public static final String CHINESE_NUMBER_ENGLISH = "^[\\u4E00-\\u9FA5A-Za-z0-9]+$";
    /** 汉字 */
    public static final String CHINESE = "^[\\u4e00-\\u9fa5]{0,}$";
    /** 数字 */
    public static final String NUMBER = "^[0-9]*$";
    /** 英文字母 */
    public static final String ENGLISH = "^[A-Za-z]+$";
    /** 汉字、数字 */
    public static final String CHINESE_NUMBER = "^[\\u4e00-\\u9fa50-9]+$";
    /** 汉字、英文字母 */
    public static final String CHINESE_ENGLISH = "^[\\u4e00-\\u9fa5A-Za-z]+$";
    /** 数字、英文字母 */
    public static final String NUMBER_ENGLISH = "^[A-Za-z0-9]+$";
    /** 大写英文字母 */
    public static final String CAPITAL_ENGLISH = "^[A-Z]+$";
    /** 小写英文字母 */
    public static final String SMALL_ENGLISH = "^[a-z]+$";
    /** 中文、英文、数字包括下划线 */
    public static final String CHINESE_NUMBER_ENGLISH_ = "^[\\u4E00-\\u9FA5A-Za-z0-9_]+$";
    /** 正整数 */
    public static final String POSITIVE_INTEGER = "^[1-9]\\d*$";
    /** 负整数 */
    public static final String NEGTIVE_INTEGER = "^-[1-9]\\d*$";
    /**
     * 缺少请求参数错误
     */
    public static final String MISSING_SERVLET_REQUEST_PARAMETER_EXCEPTION = "Missing parameter";
    /**
     * 参数类型不匹配错误
     */
    public static final String METHOD_ARGUMENT_TYPE_MISMATCH_EXCEPTION = "Parameter type mismatch";
    /**
     * 请求方法类型错误
     */
    public static final String HTTP_REQUEST_METHOD_NOT_SUPPORTED_EXCEPTION = "Request method not supported";

}
