package com.mx.goldnurse.utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BigDecimalUtils {

    //默认除法运算精度
    private static final int DEF_DIV_SCALE = 2;

    // 这个类不能实例化

    /**
     * 提供精确的加法运算。
     *
     * @param v1 被加数
     * @param v2 加数
     * @return 两个参数的和
     */
    public static BigDecimal add(BigDecimal v1, BigDecimal v2) {
        return v1.add(v2);
    }

    /**
     * 提供精确的减法运算。
     *
     * @param v1 被减数
     * @param v2 减数
     * @return 两个参数的差
     */
    public static BigDecimal sub(BigDecimal v1, BigDecimal v2) {
        return v1.subtract(v2);
    }

    /**
     * 提供精确的减法运算。
     *
     * @param v1 被减数
     * @param v2 减数
     * @return 两个参数的差 结果的最小值为0.00
     */
    public static BigDecimal subToPositive(BigDecimal v1, BigDecimal v2) {
        return v1.compareTo(v2) > 0 ? v1.subtract(v2) : BigDecimal.ZERO;
    }

    /**
     * 提供精确的乘法运算。保留两位向下取整
     *
     * @param v1 被乘数
     * @param v2 乘数
     * @return 两个参数的积
     */
    public static BigDecimal mul(BigDecimal v1, BigDecimal v2) {
        BigDecimal multiply = v1.multiply(v2);
        return multiply.setScale(2, BigDecimal.ROUND_DOWN);
    }


    /**
     * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到 小数点以后10位，以后的数字四舍五入。
     *
     * @param v1 被除数
     * @param v2 除数
     * @return 两个参数的商
     */
    public static BigDecimal div(BigDecimal v1, BigDecimal v2) {
        return div(v1, v2, DEF_DIV_SCALE);
    }

    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指 定精度，以后的数字四舍五入。
     *
     * @param v1    被除数
     * @param v2    除数
     * @param scale 表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
    public static BigDecimal div(BigDecimal v1, BigDecimal v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        return v1.divide(v2, scale, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指 定精度，以后向下取整。
     *
     * @param v1    被除数
     * @param v2    除数
     * @param scale 表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
    public static BigDecimal divDown(BigDecimal v1, BigDecimal v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        return v1.divide(v2, scale, BigDecimal.ROUND_DOWN);
    }

    /**
     * 提供精确的小数位四舍五入处理。
     *
     * @param v     需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static BigDecimal round(BigDecimal v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        return v.divide(BigDecimal.ONE, scale, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 获取传入数组最小值的角标索引
     *
     * @param d 传入数组
     * @return
     */
    public static Integer getMinNum(BigDecimal[] d) {
        if (d.length <= 0) {
            return -1;
        }
        int index = -1;
        List<BigDecimal> new_b = new ArrayList<>();
        for (int i = 0; i < d.length; i++) {
            if (d[i].compareTo(BigDecimal.ZERO) <= 0) {
                continue;
            }
            new_b.add(d[i]);
        }
        if (new_b.size() <= 0) {
            return -1;
        }
        BigDecimal num = new_b.get(0);
        for (int i = 0; i < new_b.size(); i++) {
            if (num.compareTo(new_b.get(i)) >= 0) {
                num = new_b.get(i);
            }
        }
        for (int i = 0; i < d.length; i++) {
            if (num.compareTo(d[i]) == 0) {
                index = i;
                break;
            }
        }
        return index;
    }

    /**
     * 精确乘法运算
     *
     * @param v1 被乘数
     * @param v2 乘数
     * @return 两个参数的积(BigDecimal)
     */
    public static BigDecimal multiply(BigDecimal v1, BigDecimal v2) {
        if (null == v1) {
            v1 = BigDecimal.ONE;
        }
        if (null == v2) {
            v2 = BigDecimal.ONE;
        }
        return v1.multiply(v2);
    }

    /**
     * BigDecimal保留n位小数,向下取整
     */
    public static BigDecimal getSpecificDecimal(BigDecimal number, int n) {
        return number.setScale(n, BigDecimal.ROUND_DOWN);
    }

    public static BigDecimal getMin(List<BigDecimal> BigDecimalList) {
        if (BigDecimalList == null || BigDecimalList.size() <= 0) {
            return null;
        }
        List<BigDecimal> newBigDecimalsList = new ArrayList<>();
        for (int i = 0; i < BigDecimalList.size(); i++) {
            BigDecimal BigDecimalLoad = BigDecimalList.get(i);
            if (BigDecimalLoad == null) {
                continue;
            }
            newBigDecimalsList.add(BigDecimalLoad);
        }
        if (newBigDecimalsList.size() <= 0) {
            return null;
        }
        return Collections.min(newBigDecimalsList);
    }
}
