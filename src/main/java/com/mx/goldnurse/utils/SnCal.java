package com.mx.goldnurse.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.Map.Entry;

//java版计算signature签名
public class SnCal {
    public static void main(String[] args) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        SnCal snCal = new SnCal();

        // 计算sn跟参数对出现顺序有关，get请求请使用LinkedHashMap保存<key,value>，该方法根据key的插入顺序排序；post请使用TreeMap保存<key,value>，该方法会自动将key按照字母a-z顺序排序。所以get请求可自定义参数顺序（sn参数必须在最后）发送请求，但是post请求必须按照字母a-z顺序填充body（sn参数必须在最后）。以get请求为例：http://api.map.baidu.com/geocoder/v2/?address=百度大厦&output=json&ak=yourak，paramsMap中先放入address，再放output，然后放ak，放入顺序必须跟get请求中对应参数的出现顺序保持一致。
        Map<String, String> paramsMap = new LinkedHashMap<String, String>();
        paramsMap.put("address", "百度大厦");
        paramsMap.put("output", "json");
        paramsMap.put("ak", "m1WWtclDZXOme0b3IIqLDUrqWGj3IPQZ");

        // 调用下面的toQueryString方法，对LinkedHashMap内所有value作utf8编码，拼接返回结果address=%E7%99%BE%E5%BA%A6%E5%A4%A7%E5%8E%A6&output=json&ak=yourak
        String paramsStr = SnCal.toQueryString(paramsMap);

        // 对paramsStr前面拼接上/geocoder/v2/?，后面直接拼接yoursk得到/geocoder/v2/?address=%E7%99%BE%E5%BA%A6%E5%A4%A7%E5%8E%A6&output=json&ak=yourakyoursk
        String wholeStr = new String("/geocoder/v2/?" + paramsStr + "wOnCK4timyljcwhUaGyfnYt7hkXrs47A");

        // 对上面wholeStr再作utf8编码
        String tempStr = URLEncoder.encode(wholeStr, "UTF-8");

        // 调用下面的MD5方法得到最后的sn签名7de5a22212ffaa9e326444c75a58f9a0
        System.out.println(SnCal.MD5(tempStr));
    }

    // 对Map内所有value作utf8编码，拼接返回结果
    public static String toQueryString(Map<?, ?> data) throws UnsupportedEncodingException {
        StringBuffer queryString = new StringBuffer();
        for (Entry<?, ?> pair : data.entrySet()) {
            queryString.append(pair.getKey() + "=");
            queryString.append(URLEncoder.encode((String) pair.getValue(), "UTF-8") + "&");
        }
        if (queryString.length() > 0) {
            queryString.deleteCharAt(queryString.length() - 1);
        }
        return queryString.toString();
    }

    // 来自stackoverflow的MD5计算方法，调用了MessageDigest库函数，并把byte数组结果转换成16进制
    public static String MD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
        }
        return null;
    }

    /**
     * 计算sn的值
     *
     * @param paramsMap
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String result(Map paramsMap) throws UnsupportedEncodingException {
        /**
         * 计算sn跟参数对出现顺序有关，get请求请使用LinkedHashMap保存
         * <key,value>，该方法根据key的插入顺序排序；post请使用TreeMap保存
         * <key,value>，该方法会自动将key按照字母a-z顺序排序。所以get请求可自定义参数顺序（sn参数必须在最后）发送请求，
         * 但是post请求必须按照字母a-z顺序填充body（sn参数必须在最后）。以get请求为例：http://api.map.baidu.
         * com/geocoder/v2/?address=百度大厦&output=json&ak=yourak，
         * paramsMap中先放入address，再放output，然后放ak，放入顺序必须跟get请求中对应参数的出现顺序保持一致。
         */
        // 调用下面的toQueryString方法，对LinkedHashMap内所有value作utf8编码，拼接返回结果address=%E7%99%BE%E5%BA%A6%E5%A4%A7%E5%8E%A6&output=json&ak=yourak
        String paramsStr = toQueryString(paramsMap);

        // 对paramsStr前面拼接上/geocoder/v2/?，后面直接拼接yoursk得到/geocoder/v2/?address=%E7%99%BE%E5%BA%A6%E5%A4%A7%E5%8E%A6&output=json&ak=yourakyoursk
        //        String wholeStr = new String("/geocoder/v2/?" + paramsStr + "FkytCBIZMi5kVhkfTBICZp02KGa1U5tk");
        String wholeStr = new String("/geocoder/v2/?" + paramsStr + "wOnCK4timyljcwhUaGyfnYt7hkXrs47A");

        // 对上面wholeStr再作utf8编码
        String tempStr = URLEncoder.encode(wholeStr, "UTF-8");

        // 调用下面的MD5方法得到最后的sn签名7de5a22212ffaa9e326444c75a58f9a0
        // System.out.println(MD5(tempStr));
        return MD5(tempStr);
    }

    /**
     * 除去数组中的空值和签名参数
     *
     * @param sArray 签名参数组
     * @return 去掉空值与签名参数后的新签名参数组
     */
    public static Map<String, String> paraFilter(Map<String, String> sArray) {


        Map<String, String> result = new HashMap<String, String>();


        if (sArray == null || sArray.size() <= 0) {

            return result;
        }


        for (String key : sArray.keySet()) {

            String value = sArray.get(key);

            if (value == null || value.equals("") || key.equalsIgnoreCase("sign")

                    || key.equalsIgnoreCase("sign_type")) {

                continue;
            }

            result.put(key, value);
        }


        return result;
    }


    /**
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     *
     * @param params 需要排序并参与字符拼接的参数组
     * @return 拼接后字符串
     */
    public static String createLinkString(Map<String, String> params) {


        List<String> keys = new ArrayList<String>(params.keySet());

        Collections.sort(keys);

        String prestr = "";

        for (int i = 0; i < keys.size(); i++) {

            String key = keys.get(i);

            String value = params.get(key);


            if (i == keys.size() - 1) {//拼接时，不包括最后一个&字符

                prestr = prestr + key + "=" + value;

            } else {

                prestr = prestr + key + "=" + value + "&";

            }

        }

        return prestr;
    }
}