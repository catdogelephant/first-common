package com.mx.goldnurse.utils;

import com.alibaba.fastjson.JSON;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Common {

    //定义每页条目
    public static final int PAGE = 15;//每页 条数
    //定义每页条目
    public static final int PAGE_5 = 5;//每页 条数
    //定义金牌护士导航栏分页页码
    public static final int GN_PAGE = 20;
    //定义金牌护士数量
    public static final int GN_NUMBER = 50;//测试方便使用4,后期改成50
    //VIP-获得积分倍数
    public static final int VIP_TIMES = 2;
    //定义默认地点id
    public static final int DEFAULT_LOCATION_ID = 100103;
    //定义默认医院id
    public static final int DEFAULT_HOSPITAL_ID = 1;
    //定义默认等级id
    public static final int DEFAULT_GRADE_ID = 1;
    //定义默认部门id
    public static final int DEFAULT_DEPARTMENT_ID = 1;
    //定义默认职称id
    public static final int DEFAULT_JOBTITLE_ID = 1;
    //定义默认工作年限id
    public static final int DEFAULT_WORKYEARS_ID = 1;
    //定义资讯的条数
    public static final int DEFAULT_BULLETIN = 5;

    public static DateFormat fd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static DecimalFormat df = new DecimalFormat("#.00");

    /**
     * 判断输入String时间是否早于当前时间
     *
     * @param datetime 传入时间字符串
     * @return -1.失败，1.有效 ，0.过期
     */
    public static int beforeNow(String datetime) {
        Date date = new Date();
        try {
            if (date.before(fd.parse(datetime))) {
                return 1;//当前时间早于有效时间, 有效
            } else {
                return 0;//过期 无效
            }
        } catch (ParseException e) {
            return -1;//失败
        }

    }

    //密码加密方法
    public static String Encrypt(String clear) {
        String cipher = "";
        try {
            cipher = MD5.md5crypt(MD5.md5crypt(clear));
        } catch (Exception e) {
            return null;
        }
        return cipher;
    }

    //登录认证
    public static boolean CheckPerson(String phone, String personPwd, String token) {
        //生成token
        String check = "";
        check = Common.getToken(phone, personPwd);

        //判断
        if (check.equals(token)) {
            return true;
        }

        return false;
    }

    //token生成方法
    public static String getToken(String username, String password) {
        //生成方法
        String token = "";
        token = username + password;
        //返回值
        return token;
    }

    /**
     * 生成订单编号
     *
     * @return
     */
    public static String getOrderNumber() {
        //生成方法
        int max = 99999;
        int min = 10000;
        Random random = new Random();
        int s = random.nextInt(max) % (max - min + 1) + min;
        Date date = new Date();
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        String time = df.format(date);

        //int i = (int)(Math.random()*100000+100000);
        //String d = String.valueOf(i);
        //String t = String.valueOf(System.currentTimeMillis());
        //int d = Integer.valueOf(t);
        String no = "JP" + time + s;
        //返回值
        return no;
    }

    /**
     * 生成订单编号
     *
     * @return
     */
    public static String getOrderGuidanceNumber() {
        //生成方法
        int max = 99999;
        int min = 10000;
        Random random = new Random();
        int s = random.nextInt(max) % (max - min + 1) + min;
        Date date = new Date();
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        String time = df.format(date);

        //int i = (int)(Math.random()*100000+100000);
        //String d = String.valueOf(i);
        //String t = String.valueOf(System.currentTimeMillis());
        //int d = Integer.valueOf(t);
        String no = "YC" + time + s;
        //返回值
        return no;
    }

    //生成团购orderNumber
    public static String getCollageOrderNumber() {
        //生成方法
        int max = 99999;
        int min = 10000;
        Random random = new Random();
        int s = random.nextInt(max) % (max - min + 1) + min;
        Date date = new Date();
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        String time = df.format(date);

        //int i = (int)(Math.random()*100000+100000);
        //String d = String.valueOf(i);
        //String t = String.valueOf(System.currentTimeMillis());
        //int d = Integer.valueOf(t);
        String no = "PT" + time + s;
        //返回值
        return no;
    }

    // 生成orderNumber
    public static String getOrderJKNumber() {
        // 生成方法
        int max = 99999;
        int min = 10000;
        Random random = new Random();
        int s = random.nextInt(max) % (max - min + 1) + min;
        Date date = new Date();
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        String time = df.format(date);

        // int i = (int)(Math.random()*100000+100000);
        // String d = String.valueOf(i);
        // String t = String.valueOf(System.currentTimeMillis());
        // int d = Integer.valueOf(t);
        String no = "JK" + time + s;
        // 返回值
        return no;
    }

    // 生成vip订单编号
    public static String getVipOrderNumber() {
        // 生成方法
        int max = 99999;
        int min = 10000;
        Random random = new Random();
        int s = random.nextInt(max) % (max - min + 1) + min;
        Date date = new Date();
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        String time = df.format(date);

        // int i = (int)(Math.random()*100000+100000);
        // String d = String.valueOf(i);
        // String t = String.valueOf(System.currentTimeMillis());
        // int d = Integer.valueOf(t);
        String no = "VIP" + time + s;
        // 返回值
        return no;
    }

    //生成会话No
    public static String getMessageNo() {
        //生成方法
        int max = 999;
        int min = 100;
        Random random = new Random();
        int s = random.nextInt(max) % (max - min + 1) + min;
        Date date = new Date();
        DateFormat df = new SimpleDateFormat("yyyyMMddhhmmss");
        String time = df.format(date);

        //int i = (int)(Math.random()*100000+100000);
        //String d = String.valueOf(i);
        //String t = String.valueOf(System.currentTimeMillis());
        //int d = Integer.valueOf(t);
        String no = time + s;
        //返回值
        return no;
    }

    // 生成文件名
    public static String getMessageFileName() {
        // 生成方法
        int max = 999999999;
        int min = 100000000;
        Random random = new Random();
        int s = random.nextInt(max) % (max - min + 1) + min;
        Date date = new Date();
        DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss_");
        String time = df.format(date);

        // int i = (int)(Math.random()*100000+100000);
        // String d = String.valueOf(i);
        // String t = String.valueOf(System.currentTimeMillis());
        // int d = Integer.valueOf(t);
        String no = time + s;
        // 返回值
        return no;
    }

    /**
     * 优惠券加天数
     *
     * @param d   yyyy-MM-dd HH:mm:ss.fff
     * @param day
     * @return
     * @throws ParseException
     */

    public static Date addDate(Date d, long day) throws ParseException {
        long time = d.getTime();
        day = day * 24 * 60 * 60 * 1000;
        time += day;
        return new Date(time);
    }

    /**
     * 购买天使币交易号
     */
    public static String getAngelDealNumber() {

        // 生成方法
        int max = 99999;
        int min = 10000;
        Random random = new Random();
        int s = random.nextInt(max) % (max - min + 1) + min;
        Date date = new Date();
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        String time = df.format(date);
        String no = "AL" + time + s;

        // 返回值
        return no;
    }

    /**
     * 生成订单编号
     *
     * @return
     */
    public static String getOrderClassroomNumber() {
        //生成方法
        int max = 99999;
        int min = 10000;
        Random random = new Random();
        int s = random.nextInt(max) % (max - min + 1) + min;
        Date date = new Date();
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        String time = df.format(date);

        String no = "KK" + time + s;
        //返回值
        return no;
    }

    /**
     * 生成自定义充值返现编号
     *
     * @return
     */
    public static String getActivityRechargeNumber() {
        Date date = new Date();
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        String time = df.format(date);
        int max = 99999;
        int min = 10000;
        Random random = new Random();
        int s = random.nextInt(max) % (max - min + 1) + min;
        String no = "CZFX" + time + s;
        return no;
    }

    /**
     * 通过指定时间生成订单编号
     *
     * @return
     */
    public static String getBrushOrderNumber(Date date) {
        int max = 99999;
        int min = 10000;
        Random random = new Random();
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        return "JP" + df.format(date) + (random.nextInt(max) % (max - min + 1) + min);
    }

    /**
     * 生成指定前缀编号
     *
     * @param prefix 编号前缀
     * @return
     */
    public static String getSomeNumber(String prefix) {
        //生成方法
        int max = 99999;
        int min = 10000;
        Random random = new Random();
        int s = random.nextInt(max) % (max - min + 1) + min;
        Date date = new Date();
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        String time = df.format(date);
		/*int i = (int)(Math.random()*100000+100000);
		String d = String.valueOf(i);
		String t = String.valueOf(System.currentTimeMillis());
		int d = Integer.valueOf(t);*/
        String no = prefix + time + s;
        return no;
    }

    /**
     * 生成3位随机数
     *
     * @return
     **/
    public static Integer getRandom() {
        int i = new Random().nextInt(900) + 100;
//		int i=(int)(Math.random()*900)+100; 该方法也可以
        return i;
    }

    public static void main(String[] args) {
        Integer random = Common.getRandom();
//int i= new java.util.Random().nextInt(900)+100;也可以
        System.out.println(random);
    }

    /**
     * 将请求的连接或者fromdata信息转为实体
     * <br>
     * created date 2021/8/17 14:59
     *
     * @author JiangYuhao
     */
    public static <T> T getParameterToBean(HttpServletRequest request, Class<T> aClass) {
        // 参数Map
        Map<?, ?> properties = request.getParameterMap();
        // 返回值Map
        Map<String, Object> parameterMap = new HashMap<String, Object>();
        Iterator<?> entries = properties.entrySet().iterator();

        Map.Entry<String, Object> entry;
        String name = "";
        String value = "";
        Object valueObj = null;
        while (entries.hasNext()) {
            entry = (Map.Entry<String, Object>) entries.next();
            name = (String) entry.getKey();
            valueObj = entry.getValue();
            if (null == valueObj) {
                value = "";
            } else if (valueObj instanceof String[]) {
                String[] values = (String[]) valueObj;
                for (int i = 0; i < values.length; i++) {
                    value = values[i] + ",";
                }
                value = value.substring(0, value.length() - 1);
            } else {
                value = valueObj.toString();
            }
            parameterMap.put(name, value);
        }
        return JSON.parseObject(JSON.toJSONString(parameterMap), aClass);
    }

    /**
     * 将字符集合转换为SQL中in的形式
     * @param ids
     * @return
     */
    public static String idListToStr(List<String> ids){
        StringBuffer idsStr = new StringBuffer();
        for (int i = 0; i < ids.size(); i++) {
            if (i > 0) {
                idsStr.append(",");
            }
            idsStr.append("'").append(ids.get(i)).append("'");
        }
        return idsStr.toString();
    }

}
