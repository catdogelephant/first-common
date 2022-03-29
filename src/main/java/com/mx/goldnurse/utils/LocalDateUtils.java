package com.mx.goldnurse.utils;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static java.time.temporal.TemporalAdjusters.firstDayOfMonth;
import static java.time.temporal.TemporalAdjusters.firstDayOfYear;
import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;
import static java.time.temporal.TemporalAdjusters.lastDayOfYear;

public class LocalDateUtils {
    private static final Logger log = LoggerFactory.getLogger(DateUtils.class);
    public static final String DEFAULT_FORMATTER = "yyyy-MM-dd HH:mm:ss";
    public static final String DEFAULT_FORMATTER_YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
    public static final String DEFAULT_FORMATTER_YYYY_MM_DD = "yyyy-MM-dd";
    public static final String DEFAULT_FORMATTER_LINK = "yyyyMMddHHmm";
    public static final String DEFAULT_yyyyMMddHHmmss = "yyyyMMddHHmmss";

    public static LocalDateTime now() {
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        String ldTime = localDateTime.format(dateTimeFormatter);
        return LocalDateTime.parse(ldTime, dateTimeFormatter);
    }

    public static LocalDateTime now(String formatter) {
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(formatter, Locale.CHINA);
        String ldTime = localDateTime.format(dateTimeFormatter);
        return LocalDateTime.parse(ldTime, dateTimeFormatter);
    }

    public static String format(Date date, String formatter) {
        if (null == date || org.apache.commons.lang3.StringUtils.isBlank(formatter)) {
            return "";
        }
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.of(ZoneId.SHORT_IDS.get("CTT"));
        LocalDateTime localDateTime = instant.atZone(zoneId).toLocalDateTime();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(formatter, Locale.CHINA);
        return localDateTime.format(dateTimeFormatter);
    }

    public static String nowString() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        LocalDateTime localDateTime = LocalDateTime.now();
        return dateTimeFormatter.format(localDateTime);
    }

    /**
     * 当前时间加随机8位字符
     *
     * @return
     */
    public static String nowStringRandom() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss", Locale.CHINA);
        LocalDateTime localDateTime = LocalDateTime.now();
        int[] ints = RandomUtil.randomInts(8);
        StringBuilder str = new StringBuilder(dateTimeFormatter.format(localDateTime));
        for (int anInt : ints) {
            str.append(anInt);
        }
        return str.toString();
    }

    public static String nowString(String formatter) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(formatter, Locale.CHINA);
        LocalDateTime localDateTime = LocalDateTime.now();
        return dateTimeFormatter.format(localDateTime);
    }


    public static LocalDateTime parse(String Date, String pattern) {
        if (null == Date) {
            return null;
        } else {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
            return LocalDateTime.parse(Date, dateTimeFormatter);
        }
    }

    public static Date stringToDate(String date) {
        if (StringUtils.isBlank(date)) {
            return null;
        } else {
            try {
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                return format.parse(date);
            } catch (ParseException var2) {
                var2.printStackTrace();
                return null;
            }
        }
    }

    public static Date stringToDate(String date, String formatter) {
        if (StringUtils.isBlank(date)) {
            return null;
        } else {
            try {
                DateFormat format = new SimpleDateFormat(formatter);
                return format.parse(date);
            } catch (ParseException var3) {
                var3.printStackTrace();
                return null;
            }
        }
    }

    public static String formatDate(String date) {
        if (StringUtils.isBlank(date)) {
            return null;
        } else {
            try {
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                Date parse = format.parse(date);
                return format.format(parse);
            } catch (ParseException var3) {
                var3.printStackTrace();
                return null;
            }
        }
    }

    public static LocalDateTime format(String Date) {
        if (null == Date) {
            return null;
        } else {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            return LocalDateTime.parse(Date, dateTimeFormatter);
        }
    }

    /**
     * 当周首日
     *
     * @return
     */
    public static String firstDayOfTheWeek() {
        return LocalDate.now().with(DayOfWeek.MONDAY).toString();
    }

    /**
     * 当周尾日
     *
     * @return
     */
    public static String lastDayOfTheWeek() {
        return LocalDate.now().with(DayOfWeek.SUNDAY).toString();
    }


    /**
     * 当月首日
     *
     * @return
     */
    public static String firstDayOfTheMonth() {
        return LocalDate.now().with(firstDayOfMonth()).toString();
    }

    /**
     * 当月尾日
     *
     * @return
     */
    public static String lastDayOfTheMonth() {
        return LocalDate.now().with(lastDayOfMonth()).toString();
    }


    /**
     * 当季首日
     *
     * @return
     */
    public static String firstDayOfTheSeason() {
        return getStartOrEndDayOfQuarter(true).toString();
    }

    /**
     * 当季尾日
     *
     * @return
     */
    public static String lastDayOfTheSeason() {
        return getStartOrEndDayOfQuarter(false).toString();
    }

    /**
     * 当年首日
     *
     * @return
     */
    public static String firstDayOfTheYear() {
        return LocalDate.now().with(firstDayOfYear()).toString();
    }


    /**
     * 获取某年某月的第一天
     *
     * @throws
     * @Title:getFisrtDayOfMonth
     * @Description:
     * @param:@param year
     * @param:@param month
     * @param:@return
     * @return:String
     */
    public static String getFisrtDayOfMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        //设置年份
        cal.set(Calendar.YEAR, year);
        //设置月份
        cal.set(Calendar.MONTH, month - 1);
        //获取某月最小天数
        int firstDay = cal.getActualMinimum(Calendar.DAY_OF_MONTH);
        //设置日历中月份的最小天数
        cal.set(Calendar.DAY_OF_MONTH, firstDay);
        //格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String firstDayOfMonth = sdf.format(cal.getTime());
        return firstDayOfMonth;
    }

    /**
     * 获取某月的最后一天
     *
     * @throws
     * @Title:getLastDayOfMonth
     * @Description:
     * @param:@param year
     * @param:@param month
     * @param:@return
     * @return:String
     */
    public static String getLastDayOfMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        //设置年份
        cal.set(Calendar.YEAR, year);
        //设置月份
        cal.set(Calendar.MONTH, month - 1);
        //获取某月最大天数
        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        //设置日历中月份的最大天数
        cal.set(Calendar.DAY_OF_MONTH, lastDay);
        //格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String lastDayOfMonth = sdf.format(cal.getTime());
        return lastDayOfMonth;
    }


    /**
     * 当年尾日
     *
     * @return
     */
    public static String lastDayOfTheYear() {
        return LocalDate.now().with(lastDayOfYear()).toString();
    }

    /**
     * 获取当前日期所在季度的开始日期和结束日期
     * 季度一年四季， 第一季度：1月-3月， 第二季度：4月-6月， 第三季度：7月-9月， 第四季度：10月-12月
     *
     * @param isFirst true表示查询本季度开始日期  false表示查询本季度结束日期
     * @return
     */
    private static LocalDate getStartOrEndDayOfQuarter(Boolean isFirst) {
        LocalDate today = LocalDate.now();
        LocalDate resDate = LocalDate.now();
        if (today == null) {
            today = resDate;
        }
        Month month = today.getMonth();
        Month firstMonthOfQuarter = month.firstMonthOfQuarter();
        Month endMonthOfQuarter = Month.of(firstMonthOfQuarter.getValue() + 2);
        if (isFirst) {
            resDate = LocalDate.of(today.getYear(), firstMonthOfQuarter, 1);
        } else {
            resDate = LocalDate.of(today.getYear(), endMonthOfQuarter, endMonthOfQuarter.length(today.isLeapYear()));
        }
        return resDate;
    }


    public static void main(String[] args) {
        String nowStringRandom = nowStringRandom();
        String s = nowString();
        System.out.println(s);
        String str = "2020-09-04 15:03:27";
        LocalDateTime parse = parse(str, "yyyy-MM-dd HH:mm:ss");
        System.out.println(parse);
        String s1 = nowString("yyyyMMddHHmmss");
        System.out.println(s1);
        System.out.println(format(new Date(), DEFAULT_FORMATTER));

        String year = String.valueOf(LocalDate.now().getYear());
        String lastYear = String.valueOf(LocalDate.now().minusYears(1).getYear());
        BigDecimal totalOfCurrentPeriod = BigDecimal.valueOf(Double.valueOf("2016.20"));
        BigDecimal totalForTheSamePeriod = BigDecimal.valueOf(Double.valueOf("0"));
        BigDecimal yearOnYear = BigDecimalUtils.sub(totalOfCurrentPeriod, totalForTheSamePeriod);


        String nearly7DaysSalesDataStart = LocalDate.now().with(DayOfWeek.MONDAY).toString();
        String nearly7DaysSalesDataEnd = LocalDate.now().with(DayOfWeek.SUNDAY).toString();


        /**
         * 30天内的销售额
         */
        String nearly30DaysSalesDataStart = LocalDate.now().with(firstDayOfMonth()).toString();
        String nearly30DaysSalesDataEnd = LocalDate.now().with(lastDayOfMonth()).toString();


        /**
         * 季度内的销售额
         */
        String currentYearSalesDataStart = LocalDateUtils.firstDayOfTheSeason();
        String currentYearSalesDataEnd = LocalDateUtils.lastDayOfTheSeason();

        String fisrtDayOfMonthYearStart = LocalDateUtils.getFisrtDayOfMonth(2021, 11);
        String lastDayOfMonthYearEnd = LocalDateUtils.getLastDayOfMonth(2021, 11);
        System.out.println();

    }
}
