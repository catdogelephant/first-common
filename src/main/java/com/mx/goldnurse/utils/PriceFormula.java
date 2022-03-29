package com.mx.goldnurse.utils;

public class PriceFormula {

    public static void main(String[] args) {
        PriceFormula priceFormula = new PriceFormula();
        for (int i = 1; i <= 30; i++) {
            if (i > 1) {
                System.out.println(i + ":" + Math.ceil(priceFormula.getPriceFormulaBySetMeal(139.0, i)));
            }
        }
    }

    /**
     * 价格计算公式，通过公式得出对应价格 || 公式：价格*次数-(10+次数)*(次数-1)
     *
     * @param price 单价
     * @param num   次数
     * @return 对应次数价格
     */
    public Double getPriceFormula(Double price, Integer num) {
        //公式：价格*次数-(10+次数)*(次数-1)
        double mul = DoubleUtils.mul(price, num);
        double add = DoubleUtils.add(10, num);
        double sub = DoubleUtils.sub(num, 1);
        double mul2 = DoubleUtils.mul(add, sub);
        return DoubleUtils.sub(mul, mul2);
    }

    /**
     * 价格计算公式，通过公式得出对应价格 ||  公式：（0.99*次数-0.01*次数²）*价格
     *
     * @param price 单价
     * @param num   次数
     * @return 该次数对应价格
     */
    public Double getPriceFormulaBySetMeal(Double price, Integer num) {
        double mul = DoubleUtils.mul(num, 0.99);
        double mul2 = DoubleUtils.mul(num, num);
        double mul3 = DoubleUtils.mul(0.01, mul2);
        double subToPositive = DoubleUtils.subToPositive(mul, mul3);
        double mul4 = DoubleUtils.mul(subToPositive, price);
        return mul4;
    }
}
