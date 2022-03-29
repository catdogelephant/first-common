package com.mx.goldnurse.utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DoubleUtils {

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
    public static double add(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).doubleValue();
    }

    /**
     * 提供精确的减法运算。
     *
     * @param v1 被减数
     * @param v2 减数
     * @return 两个参数的差
     */
    public static double sub(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).doubleValue();
    }

    /**
     * 提供精确的减法运算。
     *
     * @param v1 被减数
     * @param v2 减数
     * @return 两个参数的差 结果的最小值为0.00
     */
    public static double subToPositive(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).doubleValue() > 0 ? b1.subtract(b2).doubleValue() : 0.0;
    }

    /**
     * 提供精确的乘法运算。保留两位向下取整
     *
     * @param v1 被乘数
     * @param v2 乘数
     * @return 两个参数的积
     */
    public static double mul(double v1, double v2) {
        BigDecimal b1 = BigDecimal.valueOf(v1);
        BigDecimal b2 = BigDecimal.valueOf(v2);
//        BigDecimal b1 = new BigDecimal(Double.toString(v1));
//        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        BigDecimal multiply = b1.multiply(b2);
        return multiply.setScale(2, BigDecimal.ROUND_DOWN).doubleValue();
    }


    /**
     * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到 小数点以后10位，以后的数字四舍五入。
     *
     * @param v1 被除数
     * @param v2 除数
     * @return 两个参数的商
     */
    public static double div(double v1, double v2) {
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
    public static double div(double v1, double v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指 定精度，以后向下取整。
     *
     * @param v1    被除数
     * @param v2    除数
     * @param scale 表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
    public static double divDown(double v1, double v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2, scale, BigDecimal.ROUND_DOWN).doubleValue();
    }

    /**
     * 提供精确的小数位四舍五入处理。
     *
     * @param v     需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static double round(double v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(Double.toString(v));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 获取传入数组最小值的角标索引
     *
     * @param d 传入数组
     * @return
     */
    public static Integer getMinNum(double[] d) {
        if (d.length <= 0) {
            return -1;
        }
        int index = -1;
        List<Double> new_b = new ArrayList<>();
        for (int i = 0; i < d.length; i++) {
            if (DoubleUtils.sub(d[i], 0) <= 0) {
                continue;
            }
            new_b.add(d[i]);
        }
        if (new_b.size() <= 0) {
            return -1;
        }
        double num = new_b.get(0);
        for (int i = 0; i < new_b.size(); i++) {
            if (DoubleUtils.sub(num, new_b.get(i)) >= 0) {
                num = new_b.get(i);
            }
        }
        for (int i = 0; i < d.length; i++) {
            if (DoubleUtils.sub(num, d[i]) == 0) {
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
     * double保留n位小数,向下取整
     */
    public static Double getSpecificDecimal(Double number, int n) {
        BigDecimal bigDecimal = BigDecimal.valueOf(number);
        return bigDecimal.setScale(n, BigDecimal.ROUND_DOWN).doubleValue();
    }

    public static Double getMin(List<Double> doubleList) {
        if (doubleList == null || doubleList.size() <= 0) {
            return null;
        }
        List<Double> newDoublesList = new ArrayList<>();
        for (int i = 0; i < doubleList.size(); i++) {
            Double doubleLoad = doubleList.get(i);
            if (doubleLoad == null) {
                continue;
            }
            newDoublesList.add(doubleLoad);
        }
        if (newDoublesList.size() <= 0) {
            return null;
        }
        return Collections.min(newDoublesList);
    }

    public static void main(String[] args) {
        System.out.println(DoubleUtils.div(85.0, 100, 3));
        System.out.println(Math.floor(85.98));
    }

}
