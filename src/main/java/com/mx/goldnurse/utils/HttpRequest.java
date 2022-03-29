package com.mx.goldnurse.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

/**
 * http请求 post get方法
 */
@Slf4j
public class HttpRequest {

    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url   发送请求的URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param, String token) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            if (!token.equals("")) {
                connection.setRequestProperty("token", token);
            }
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            // 建立实际的连接
            connection.connect();
			/*// 获取所有响应头字段
			Map<String, List<String>> map = connection.getHeaderFields();
			// 遍历所有的响应头字段
			for (String key : map.keySet()) {
				System.out.println(key + "--->" + map.get(key));
			}*/
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url   发送请求的 URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param, String token) {
        OutputStreamWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            //URLEncoder.encode(url, "UTF-8");
            //打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            //设置通用的请求属性
            if (!token.equals("")) {
                conn.setRequestProperty("token", token);
            }
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            //conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
			/* conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			   conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
			*/
            conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            //发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            //获取URLConnection对象对应的输出流
            //out = new PrintWriter(conn.getOutputStream());解决乱码问题，替换成OutputStreamWrite
            out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            //发送请求参数
//			String alias =new String(param.getBytes("utf-8"),"iso8859-1");//推送人信息
            out.write(param);
            System.out.println("编码格式UTF-8[URL]=" + url + "[param]=" + param);
//			System.out.println("编码格式-iso8859-1=alias:"+alias);
            //flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 通过post请求方式向指定的url发送请求
     *
     * @param url   指定的url
     * @param param 拼接在url上的属性格式为 字段名+“=”+值+“&”;
     * @return 返回请求之后的响应数据
     */
    public static String sendPost(String url, String param) {
        if (StringUtils.isEmpty(url) || StringUtils.isEmpty(param)) {
            log.info("参数不能为空[url=" + url + ",param=" + param + "]");
            return null;
        }
        System.out.println("【url】=" + url + "【param】=" + param);
        OutputStreamWriter outputStreamWriter = null;
        BufferedReader bufferedReader = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            URLConnection urlConnection = realUrl.openConnection();//打开和URL之间的连接
            urlConnection.setRequestProperty("accept", "*/*");//设置请求接受类型
            urlConnection.setRequestProperty("connection", "Keep-Alive");//设置请求的连接点
            urlConnection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            //conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            urlConnection.setDoOutput(true);//post请求必须的
            urlConnection.setDoInput(true);//post请求必须的
            outputStreamWriter = new OutputStreamWriter(urlConnection.getOutputStream(), "UTF-8");//获取URLConnection对象对应的输出流
            outputStreamWriter.write(param);//发送请求参数
            outputStreamWriter.flush();//flush输出流的缓冲
            bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "utf-8"));//定义BufferedReader输入流来读取URL的响应
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            log.info("通过post请求向指定的url发送请求异常");
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (outputStreamWriter != null) {
                    outputStreamWriter.close();
                }
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException ex) {
                log.info("过post请求向指定的url发送请求关闭资源异常");
                ex.printStackTrace();
                return null;
            }
        }
        return result;
    }

    /**
     *
     */
    public static String sendGetRelieve(String url, String param, String token) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            if (!token.equals("")) {
                connection.setRequestProperty("token", token);
            }
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            // 建立实际的连接
            connection.connect();
			/*// 获取所有响应头字段
			Map<String, List<String>> map = connection.getHeaderFields();
			// 遍历所有的响应头字段
			for (String key : map.keySet()) {
				System.out.println(key + "--->" + map.get(key));
			}*/
            // 定义 BufferedReader输入流来读取URL的响应
            GZIPInputStream stream = new GZIPInputStream(connection.getInputStream());
            in = new BufferedReader(new InputStreamReader(stream, "utf-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }


    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url   发送请求的URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }


    /**
     * 向指定URL发送GET方法的请求
     *
     * @param urlPath
     * @return
     */
    public static InputStream getInputStreamGet(String urlPath, String param) {
        InputStream inputStream = null;
        HttpURLConnection httpURLConnection = null;
        try {
            String urlNameString = "";
            if (param != null) {
                urlNameString = urlPath + "?" + param;
            }
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            //Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            //for (String key : map.keySet()) {
            //	//System.out.println(key + "--->" + map.get(key));
            //}

            inputStream = connection.getInputStream();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("[getInputStreamGet]发送GET请求出现异常！" + e);
        }
        return inputStream;
    }

    private static String readInputStream(InputStream instream, String charest) throws Exception {

        StringBuilder sb = new StringBuilder();
        try (
                InputStreamReader isr = new InputStreamReader(instream, charest);
                BufferedReader reader = new BufferedReader(isr);) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }
        return sb.toString();
    }

    public static String getWebcontent(String webUrl, String charest) {
        if (StringUtils.isEmpty(webUrl)) {
            return null;
        }
        int response = -1;
        HttpURLConnection conn = null;
        try {
            URL url = new URL(webUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setReadTimeout(60 * 2000);
            conn.setConnectTimeout(10 * 1000);
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36");
            conn.setDoOutput(true);
            conn.connect();
            response = conn.getResponseCode();
            if (response == 200) {
                InputStream im = null;
                try {
                    im = conn.getInputStream();
                    return readInputStream(im, charest);
                } finally {
                    IOUtils.closeQuietly(im);
                }
            }
            return null;
        } catch (Exception e) {
            log.error(String.format("下载到文件出错[url=%s][%s][responsecode=%d]", webUrl, e.getMessage(), response));
            return null;
        } finally {
            if (conn != null) {
                conn.disconnect();
                conn = null;
            }
        }
    }
}

