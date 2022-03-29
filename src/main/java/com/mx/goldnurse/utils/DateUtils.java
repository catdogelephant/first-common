package com.mx.goldnurse.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

/**
 * Created by IntelliJ IDEA. User: garmbrood Time: 2009-4-7 16:32:56 Company:
 * 天极传媒集团
 * Descripion:日期工具类,继承自apache的DateUtils类，继承的方法参见org.apache.commons.lang.time
 * .DateUtils的文档 内置了常见的日期格式，格式化时自动适配相应类型
 */
@Slf4j
@Deprecated
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {

    public static final String FORMAT_DEFAULT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 时间格式化(格式：yyyy-MM-dd HH:mm:ss)
     */
    public static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 时间格式化(格式：yyyy年MM月dd日 HH时mm分ss秒)
     */
    public static SimpleDateFormat format_ymd = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");

    public static final String FORMAT_DATE = "yyyy-MM-dd";

    public static final String FORMAT_MONTH_WEEK = "MM-dd";

    public static final String FORMAT_MONTH = "yyyy-MM";

    public static final String FORMAT_YEAR = "yyyy";

    public static final String FORMAT_DATE_STR = "yyyyMMdd";

    public static final String FORMAT_TIME = "HH:mm:ss";

    public static final String FORMAT_HOUR = "HH:mm";

    public static final String FORMAT_SHORT_DATE_TIME = "MM-dd HH:mm";

    public static final String FORMAT_DATE_TIME = FORMAT_DEFAULT;

    public static final String FORMAT_NO_SECOND = "yyyy-MM-dd HH:mm";

    public static final String FORMAT_JAPAN = "MM.dd(EEE) HH";

    public static final String FORMAT_CHINESE_NO_SECOND = "yyyy年MM月dd日 HH:mm";

    public static final String FORMAT_CHINESE_NO_SECOND_1 = "yyyy年MM月dd日HH:mm";

    public static final String FORMAT_CHINESE = "yyyy年MM月dd日 HH点mm分";

    public static final int TYPE_HTML_SPACE = 2;

    public static final int TYPE_DECREASE_SYMBOL = 3;

    public static final int TYPE_SPACE = 4;

    public static final int TYPE_NULL = 5;

    public static final String NORMAL_START_WORK_TIME = " 8:00:00";// 规定的上班时间

    public static final String NORMAL_END_WORK_TIME1 = " 17:30:00";// 夏季下班5点半（5.4-10.7）；

    public static final String NORMAL_END_WORK_TIME2 = " 17:00:00";// 冬季下班5点（10.8-5.3）

    public static final String SUMMER_START_TIME = "-05-04 00:00:00";// 夏季开始日期

    public static final String WINTER_START_TIME = "-10-08 00:00:00";// 冬季开始日期

    /**
     * >= 前一个小时 HH:00:00 毫秒数
     *
     * @return
     */
    public static long beforeOneHourTime() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY) - 1);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:00:00");
        // System.out.println("一个小时前的时间：" + df.format(cal.getTime()));
        // System.out.println("当前的时间：" + df.format(new Date()));
        Date date = parse(df.format(cal.getTime()), "yyyy-MM-dd HH:mm:ss");
        return date.getTime();
    }

    /**
     * 获取今天开始时间
     *
     * @return
     */
    public static Date getStartTime() {
        Calendar todayStart = Calendar.getInstance();
        todayStart.set(Calendar.HOUR_OF_DAY, 0);
        todayStart.set(Calendar.MINUTE, 0);
        todayStart.set(Calendar.SECOND, 0);
        todayStart.set(Calendar.MILLISECOND, 0);
        return todayStart.getTime();
    }

    /**
     * 获取今天结束时间
     *
     * @return
     */
    public static Date getEndTime() {
        Calendar todayEnd = Calendar.getInstance();
        todayEnd.set(Calendar.HOUR_OF_DAY, 23);
        todayEnd.set(Calendar.MINUTE, 59);
        todayEnd.set(Calendar.SECOND, 59);
        todayEnd.set(Calendar.MILLISECOND, 999);
        return todayEnd.getTime();
    }

    /**
     * < 当前这个小时 HH:00:00 毫秒数
     *
     * @return
     */
    public static long currentHourTime() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:00:00");
        Date date = parse(df.format(new Date()), "yyyy-MM-dd HH:mm:ss");
        return date.getTime();
    }

    /**
     * 上一个小时的时间
     *
     * @return
     */
    public static Date beforeOneHourDate() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY) - 1);
        Date date = cal.getTime();
        return date;
    }

    // 得到昨天的日期
    public static Date getYesterdayDate() {
        Calendar cal = Calendar.getInstance();
        cal.add(5, -1);
        return cal.getTime();
    }

    public static String formartMonth(Date date) {
        SimpleDateFormat simpleDate = new SimpleDateFormat(FORMAT_MONTH);
        return simpleDate.format(date);

    }

    public static String formartYear(Date date) {
        SimpleDateFormat simpleDate = new SimpleDateFormat(FORMAT_YEAR);
        return simpleDate.format(date);

    }

    /**
     * 获取某月最大天数
     *
     * @param yearMonth
     * @return
     */
    public static int calDayByYearAndMonth(String yearMonth) {
        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyyMM");
        Calendar rightNow = Calendar.getInstance();
        try {
            rightNow.setTime(simpleDate.parse(yearMonth));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return rightNow.getActualMaximum(Calendar.DAY_OF_MONTH);// 根据年月 获取月份天数
    }

    public static String dateStrToDateStr(String str) {
        Date date = parse(str, FORMAT_DATE_STR);

        return format(date, FORMAT_DATE);
    }

    public static String dateStrToWeekDay(String str) {
        Date date = parse(str, FORMAT_DATE_STR);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        String weekDay = transformNumber(cal.get(Calendar.DAY_OF_WEEK));
        return weekDay;
    }

    public static String transformNumber(int i) {
        switch (i) {
            case 1:
                return "日";
            case 2:
                return "一";
            case 3:
                return "二";
            case 4:
                return "三";
            case 5:
                return "四";
            case 6:
                return "五";
            case 7:
                return "六";
            default:
                return "传入数字有误";
        }
    }

    // 得到昨日的 规定上班时间
    public static long getYesterdayStartTime() {

        // 一年 上班时间都为 8:00
        return yesterdayDateTime(NORMAL_START_WORK_TIME);
    }

    // 得到昨日的 规定下班时间
    public static long getYesterdayEndTime() {
        if (yesterdayDateTime(NORMAL_START_WORK_TIME) > thisYearSeasonStartTime(SUMMER_START_TIME)
                && yesterdayDateTime(NORMAL_START_WORK_TIME) < thisYearSeasonStartTime(WINTER_START_TIME)) {
            // 条件成立，为夏季，下班时间5:30
            return yesterdayDateTime(NORMAL_END_WORK_TIME1);
        } else {
            // 为冬季，下班时间 5:00
            return yesterdayDateTime(NORMAL_END_WORK_TIME2);
        }

    }

    // 获取昨日的各个时间段的 时间
    public static long yesterdayDateTime(String timeScale) {
        String yesterdayDateStr = getYesterdayDateStr("yyyy-MM-dd") + timeScale;
        Date yesterdayDate = parse(yesterdayDateStr, FORMAT_DEFAULT);
        // System.out.println(yesterdayDateStr+"字符串|||"+yesterdayDate);
        return yesterdayDate.getTime();
    }

    // 昨日所属年的季节开始时间
    public static long thisYearSeasonStartTime(String seasonDate) {
        String startDateStr = getYesterdayDateStr("yyyy") + seasonDate;
        Date startDate = parse(startDateStr, FORMAT_DEFAULT);
        // System.out.println(startDateStr+"字符串|||"+startDate);
        return startDate.getTime();
    }

    // 判断日期是否是昨日
    public static boolean isYesterday(Date date) {
        String str1 = getYesterdayDateStr("yyyy-MM-dd");
        String str2 = format(date, "yyyy-MM-dd");
        if (str1.equals(str2)) {
            return true;
        } else {
            return false;
        }

    }

    // 得到昨天的格式化日期
    public static String getYesterdayDateStr(String format) {
        Calendar cal = Calendar.getInstance();
        cal.add(5, -1);
        String str = new SimpleDateFormat(format).format(cal.getTime());

        return str;
    }

    // 根据输入的月份，得到当年的月份时间
    public static Date coverDate(int month) {

        String str = new SimpleDateFormat("yyyy").format(new Date()) + "" + month;

        return parse(str, "yyyyMM");
    }


    public static Map<String, SimpleDateFormat> getFormaters() {
        return formaters;
    }

    private static Map<String, SimpleDateFormat> formaters = new HashMap<String, SimpleDateFormat>();

    static {
        SimpleDateFormat defaultFormater = new SimpleDateFormat(FORMAT_DEFAULT, Locale.CHINA);
        formaters.put(FORMAT_DEFAULT, defaultFormater);
        formaters.put(FORMAT_DATE, new SimpleDateFormat(FORMAT_DATE, Locale.CHINA));
        formaters.put(FORMAT_TIME, new SimpleDateFormat(FORMAT_TIME, Locale.CHINA));
        formaters.put(FORMAT_SHORT_DATE_TIME, new SimpleDateFormat(FORMAT_SHORT_DATE_TIME, Locale.CHINA));
        formaters.put(FORMAT_CHINESE_NO_SECOND, new SimpleDateFormat(FORMAT_CHINESE_NO_SECOND, Locale.CHINA));
        formaters.put(FORMAT_CHINESE, new SimpleDateFormat(FORMAT_CHINESE, Locale.CHINA));
        formaters.put(FORMAT_DATE_TIME, defaultFormater);
        formaters.put(FORMAT_NO_SECOND, new SimpleDateFormat(FORMAT_NO_SECOND, Locale.CHINA));
        formaters.put(FORMAT_JAPAN, new SimpleDateFormat(FORMAT_JAPAN, Locale.JAPAN));
        formaters.put(FORMAT_CHINESE_NO_SECOND_1, new SimpleDateFormat(FORMAT_CHINESE_NO_SECOND_1, Locale.CHINA));

    }

    /**
     * 使用给定的 pattern 对日期进格式化为字符串
     *
     * @param date    待格式化的日期
     * @param pattern 格式字符串
     * @return 格式化后的日期字符串
     */
    public static String format(Date date, String pattern) {
        SimpleDateFormat dateFormat;
        if (formaters.containsKey(pattern)) {
            dateFormat = formaters.get(pattern);
        } else {
            dateFormat = new SimpleDateFormat(pattern);
        }
        return dateFormat.format(date);
    }

    /**
     * 将String类型的时间转换为LocalDateTime
     */
    public static LocalDateTime format(String Date, String pattern) {
        if (null == Date) {
            return null;
        }
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        return LocalDateTime.parse(Date, dateTimeFormatter);
    }

    /**
     * 以默认日期格式(yyyy-MM-dd HH:mm:ss)对日期进行格式化
     *
     * @param date 待格式化的日期
     * @return 格式化后的日期字符串
     */
    public static String format(Date date) {
        return formaters.get(FORMAT_DEFAULT).format(date);
    }

    /**
     * 格式化日期，
     * --------------------------------------------------------------------
     * ------<br>
     * 创建者：杨思勇<br>
     * 创建日期：2002-1-16<br>
     * <br>
     * 修改者：<br>
     * 修改日期：<br>
     * 修改原因：<br>
     * ------------------------------------------------------------------------
     * --
     *
     * @param date   要格式化的日期对象
     * @param format 格式
     * @param type   如果日期为空，定义返回的类型
     * @return 返回值，如果date为空，则type定义返回类型，如果格式化失败。则返回空串
     */
    public static String format(Date date, String format, int type) {
        if (date != null) {
            // ---------------------------------
            // 日期不为空时才格式
            // ---------------------------------
            try {
                // ---------------------------------
                // 调用SimpleDateFormat来格式化
                // ---------------------------------
                return new SimpleDateFormat(format).format(date);
            } catch (Exception e) {
                // ---------------------------------
                // 格式化失败后，返回一个空串
                // ---------------------------------
                return "";
            }
        } else {
            // ---------------------------------
            // 如果传入日期为空，则根据类型返回结果
            // ---------------------------------
            switch (type) {
                case TYPE_HTML_SPACE: // '\002'
                    return "&nbsp;";

                case TYPE_DECREASE_SYMBOL: // '\003'
                    return "-";

                case TYPE_SPACE: // '\004'
                    return " ";

                case TYPE_NULL:
                    return null;

                default:
                    // ---------------------------------
                    // 默认为空串
                    // ---------------------------------
                    return "";
            }
        }
    }

    /**
     * 将给定字符串解析为对应格式的日期,循环尝试使用预定义的日期格式进行解析
     *
     * @param str 待解析的日期字符串
     * @return 解析成功的日期，解析失败返回null
     */
    public static Date parse(String str) {
        Date date = null;
        for (String _pattern : formaters.keySet()) {
            if (_pattern.getBytes().length == str.getBytes().length) {
                try {
                    date = formaters.get(_pattern).parse(str);
                    // 格式化成功则退出
                    break;
                } catch (ParseException e) {
                    // 格式化失败，继续尝试下一个
                    log.debug("尝试将日期:" + str + "以“" + _pattern + "”格式化--失败=.=!");
                }
            } else if (_pattern.equals(FORMAT_JAPAN)) {
                try {
                    date = formaters.get(_pattern).parse(str);
                    // 格式化成功则退出
                    break;
                } catch (ParseException e) {
                }
            }
        }
        return date;
    }

    /**
     * 将String类型的时间转换为LocalDateTime
     */
    public static LocalDateTime parseLocalDateTime(String Date, String pattern) {
        if (null == Date) {
            return null;
        }
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        return LocalDate.parse(Date, dateTimeFormatter).atStartOfDay();
    }

    public static Date parse(String str, String pattern) {
        SimpleDateFormat dateFormat;
        if (formaters.containsKey(pattern)) {
            dateFormat = formaters.get(pattern);
        } else {
            dateFormat = new SimpleDateFormat(pattern);
        }
        Date date = null;
        try {
            date = dateFormat.parse(str);
        } catch (ParseException e) {
            // 是格式失败
            log.debug("尝试将日期:" + str + "以“" + pattern + "”格式化--失败=.=!");
            e.printStackTrace();
        }
        return date;
    }

    /**
     * date2 是否在date1之后
     *
     * @param date1 参照日期
     * @param date2 比较日期
     * @return true:是 false:否
     */
    public static boolean isAfter(Date date1, Date date2) {
        Calendar calendar2 = Calendar.getInstance();
        Calendar calendar1 = Calendar.getInstance();
        calendar2.setTime(date2);
        calendar1.setTime(date1);
        return calendar2.after(calendar1);
    }

    /**
     * 当前时间+指定的分钟以后的时间
     */
    public static Date getTimeByMinis(int minis) {
        Calendar calendar = Calendar.getInstance();
        int min = calendar.get(Calendar.MINUTE);
        calendar.set(Calendar.MINUTE, min + minis);
        Date cc = calendar.getTime();
        return cc;
    }

    /**
     * 指定时间+指定的分钟以后的时间
     *
     * @param time  指定时间
     * @param minis 分钟数
     * @return
     **/
    public static Date getTimeByMinis(Date time, int minis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        calendar.add(Calendar.MINUTE, minis); //数字参数可修改，+表示n分钟之后，-表示n分钟之前的时间
        Date cc = calendar.getTime();
        return cc;
    }

    /**
     * 获得当前时间的毫秒数
     *
     * @return
     */
    public static Long getMillisecond() {
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DEFAULT);
        Date beginTime = null;
        try {
            beginTime = sdf.parse(DateUtils.format(new Date(), FORMAT_DEFAULT));

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return beginTime.getTime();
    }

    /**
     * 获得当前时间的毫秒数
     *
     * @param pattern
     * @return
     */
    public static Long getMillisecond(String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date beginTime = null;
        try {
            beginTime = sdf.parse(DateUtils.format(new Date(), pattern));

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return beginTime.getTime();
    }

    /**
     * 获得指定时间的毫秒数
     *
     * @param date
     * @param pattern
     * @return
     */
    public static Long getMillisecond(String date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date beginTime = null;
        try {
            beginTime = sdf.parse(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return beginTime.getTime();
    }

    /**
     * @param date
     * @param pattern
     * @param locale
     * @return
     */
    public static Long getMillisecond(String date, String pattern, Locale locale) {
        SimpleDateFormat sdf = null;
        if (locale == null) {
            sdf = new SimpleDateFormat(pattern, locale);
        } else {
            sdf = new SimpleDateFormat(pattern, locale);
        }
        Date beginTime = null;
        try {
            beginTime = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return beginTime.getTime();
    }


    /**
     * localDateTime 转成 date
     *
     * @param dateTime
     * @return
     * @author huoht
     */
    public static Date dateTimeToDate(LocalDateTime dateTime) {
        return Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * Date 转成 LocalDateTime
     *
     * @param date
     * @return
     * @author huoht
     */
    public static LocalDateTime dateToDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    public static String getNowTime() {
        return DateUtils.format(new Date());
    }

    /**
     * LocalDateTime转字符串
     *
     * @param dateTime
     * @param pattern
     * @return
     * @author huoht
     */
    public static String format(LocalDateTime dateTime, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return dateTime.format(formatter);
    }

    /**
     * 字符串装LocalDateTime
     *
     * @param date
     * @param pattern
     * @return
     * @author huoht
     */
    public static LocalDateTime parseDateTime(String date, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return LocalDateTime.parse(date, formatter);
    }

    public static long getDistanceDays(Date str1, Date str2) throws Exception {
        long days = 0;
        try {
            long time1 = str1.getTime();
            long time2 = str2.getTime();
            long diff;
            if (time1 < time2) {
                diff = time2 - time1;
            } else {
                diff = time1 - time2;
            }
            days = diff / (1000 * 60 * 60 * 24);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return days;
    }

    public static Date plusDay(int num, Date currdate) throws ParseException {
        Calendar ca = Calendar.getInstance();
        ca.add(Calendar.DATE, num);// num为增加的天数，可以改变的
        currdate = ca.getTime();
        return currdate;
    }

    static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 获取月份起始日期
     *
     * @param date
     * @return
     * @throws ParseException
     */
    public static String getMinMonthDate(String date) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateFormat.parse(date));
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        return dateFormat.format(calendar.getTime());
    }

    /**
     * 获取月份最后日期
     *
     * @param date
     * @return
     * @throws ParseException
     */
    public static String getMaxMonthDate(String date) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateFormat.parse(date));
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return dateFormat.format(calendar.getTime());
    }

    /**
     * 两个时间相减-返回负数默认为零
     *
     * @param d1 当前时间-被减时间
     * @param d2 减数
     * @return
     */
    public static long differenceMinute(Date d1, Date d2) {
        long d1m = d1.getTime();
        long d2m = d2.getTime();
        return (d1m - d2m) / 1000 < 1 ? 0 : (d1m - d2m) / 1000;
    }

    /**
     * @param beginTime 开始时间
     * @param date      判断时间
     * @param endTime   结束时间
     * @return
     */
    public static int ifDatebetween(String beginTime, Date date, String endTime) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            /**
             * false	str 大 date 小
             * true	date 大 str 小
             * date2 是否在date1之后
             *	date1 参照日期
             *	date2 比较日期
             *	true:是 false:否
             */

            boolean bg_b = isAfter(formatter.parse(beginTime), date);
            boolean en_b = isAfter(date, formatter.parse(endTime));

            System.out.println("bg_b--" + bg_b);
            System.out.println("en_b--" + en_b);
            //	date 在两个时间段内
            if (bg_b && en_b) {
                return 1;
            }
            //	date 不在
            if (!bg_b && !en_b) {
                return 2;
            }
            //	date 未到bg 开始时间
            if (!bg_b && en_b) {
                return 3;
            }
            //	date 已过end结束时间
            if (bg_b && !en_b) {
                return 4;
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return 0;
    }

    /**
     * 获取当前月份
     *
     * @return 返回当前月份int类型
     */
    public static Integer getMonth() {
        Calendar calendar = Calendar.getInstance();
        //获得当前时间的月份，月份从0开始所以结果要加1
        int month = calendar.get(Calendar.MONTH) + 1;
        return month;
    }

    /**
     * 获得该月第一天
     *
     * @param year
     * @param month
     * @return
     */
    public static String getFirstDayOfMonth(int year, int month) {
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
        return firstDayOfMonth + " 00:00:00";
    }

    /**
     * 获得该月最后一天
     *
     * @param year
     * @param month
     * @return
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
        return lastDayOfMonth + " 23:59:59";
    }

    /**
     * 获取当前年份
     *
     * @return
     */
    public static Integer getSysYear() {
        Calendar date = Calendar.getInstance();
        String year = String.valueOf(date.get(Calendar.YEAR));
        return Integer.valueOf(year);
    }

    /**
     * 获取精确到秒的时间差
     *
     * @param now:当前时间（被减数的时间）
     * @param old:旧时间（减数的时间）
     * @return
     * @Author gaopeng
     * @Description
     * @date 17:25 2019/6/21
     **/
    public static Long getSecond(Date now, Date old) {
        long between = now.getTime() - old.getTime();
        long s = between / 1000;
        /*long day = between / (24 * 60 * 60 * 1000);
        long hour = (between / (60 * 60 * 1000) - day * 24);
        long min = ((between / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long s = (between / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);*/
        return s;
    }


    public static Date addDate(Date date, long day) throws ParseException {
        long time = date.getTime(); // 得到指定日期的毫秒数
        day = day * 24 * 60 * 60 * 1000; // 要加上的天数转换成毫秒数
        time += day; // 相加得到新的毫秒数
        return new Date(time); // 将毫秒数转换成日期
    }


    /**
     * 将长时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
     *
     * @param strDate
     * @return
     */
    public static Date strToDate(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }

    /**
     * 获取从当天到输入num天的日期信息(day=yyyy-MM-dd date=时间戳 week=星期)
     *
     * @param num     时间长度
     * @param pattern 时间转换规则
     * @param addNum  推后天数
     */
    public static List<Map<String, Object>> getSchedulDayList(Integer num, String pattern, Integer addNum) {
        List<Map<String, Object>> list = new ArrayList<>();
        for (int i = (0 + addNum); i < (num + addNum); i++) {
            Calendar c = Calendar.getInstance();
            c.setTime(new Date());
            c.add(Calendar.DATE, +i);
            Map<String, Object> dayAndWeek = getDayAndWeek(c.getTime(), pattern);
            list.add(dayAndWeek);
        }
        return list;
    }

    /**
     * 获取传入时间的日期及星期
     *
     * @param date
     * @return
     */
    public static Map<String, Object> getDayAndWeek(Date date, String pattern) {
        HashMap<String, Object> map = new HashMap<>();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        String day = format(c.getTime(), pattern);
        map.put("day", day);
        map.put("date", c.getTime().getTime());
        //long time = c.getTime().getTime();
        String week = transformNumber(c.get(Calendar.DAY_OF_WEEK));
        map.put("week", week);
        return map;
    }

    /**
     * 获取时间字符串（yyyy-MM-dd HH:mm:ss）
     *
     * @param
     * @return
     * @Author gaopeng
     * @Description
     * @date 15:49 2019/7/20
     **/
    public static String getStringDate(Date time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = format.format(time);
        return str;
    }

    /**
     * 毫秒转时分秒
     *
     * @param times 秒(不是毫秒数)
     * @return HH:mm:ss
     */
    public static String parseDate(long times) {
        long ms = times * 1000 - TimeZone.getDefault().getRawOffset();
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        return formatter.format(ms);
    }

    /**
     * 验证开始时间是否大于结束时间
     *
     * @param startTime 开始时间 (格式yyyy-MM-dd)
     * @param endTime   结束时间 (格式yyyy-MM-dd)
     * @return true/false  是/否
     */
    public static Boolean proofTime(String startTime, String endTime) {
        if (StringUtils.isNotEmpty(startTime) && StringUtils.isNotEmpty(endTime)) {
            Date start = DateUtils.parse(startTime, DateUtils.FORMAT_DATE);
            Date end = DateUtils.parse(endTime, DateUtils.FORMAT_DATE);
            if (start.getTime() > end.getTime()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * 计算两个时间的差值为多少小时（不足1小时按1小时计算）
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return
     */
    public static String getHoursDifference(Date startTime, Date endTime) {
        long different = endTime.getTime() - startTime.getTime();
        long hoursInMilli = 1000 * 60 * 60;
        long hours = different / hoursInMilli;
        different = different % hoursInMilli;
        if (different > 0) {
            hours = hours + 1;
        }
        return hours + "";
    }

    /**
     * 计算两个时间的差值为多少天（不足1天按1天计算）
     *
     * @param startTime 开始时间 (格式yyyy-MM-dd)
     * @param endTime   结束时间 (格式yyyy-MM-dd)
     * @return
     */
    public static Integer getDayDifference(String startTime, String endTime) {
        if (StringUtils.isBlank(startTime) || StringUtils.isBlank(endTime)) {
            return null;
        }
        Date start = DateUtils.parse(startTime, DateUtils.FORMAT_DATE);
        Date end = DateUtils.parse(endTime, DateUtils.FORMAT_DATE);
        long different = end.getTime() - start.getTime();
        if (different < 0) {
            return null;
        }
        long dayInMilli = 1000 * 60 * 60 * 24;
        int day = (int) (different / dayInMilli);
        different = different % dayInMilli;
        if (different > 0) {
            day++;
        }
        return day;
    }

    /**
     * Date转换为LocalDateTime
     *
     * @param date 时间
     * @return
     **/
    public static LocalDateTime convertDateToLocalDateTime(Date date) {
        if (date == null) {
            return null;
        }
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    /**
     * LocalDateTime转换为Date
     *
     * @param time 时间
     * @return
     **/
    public static Date convertLocalDateTimeToDate(LocalDateTime time) {
        if (time == null) {
            return null;
        }
        return Date.from(time.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static void main(String[] args) {
        Date parse = DateUtils.parse("2021-06-05",FORMAT_DATE);
        System.out.println(parse.getTime());
        /*int i = 1;
        if(i == 1){
            System.out.println("1");
        }else if(i == 2){
            System.out.println("2");
        }else{
            System.out.println("3");
        }*/

        /*try {
            Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2020-07-15 19:00:00");
            Long second = DateUtils.getSecond(new Date(), date);
            System.out.println(second);
        } catch (ParseException e) {
            e.printStackTrace();
        }*/


//        Calendar cal = Calendar.getInstance();
//        cal.setTime(new Date());
//        cal.add(Calendar.MINUTE, 10); //数字参数可修改，+表示n分钟之后，-表示n分钟之前的时间
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        System.out.println("前一分钟："+sdf.format(cal.getTime()));
//        System.out.println(sdf.format(cal.getTime()));
//        System.out.println(format.format(DateUtils.getTimeByMinis(10)));

    }

}
