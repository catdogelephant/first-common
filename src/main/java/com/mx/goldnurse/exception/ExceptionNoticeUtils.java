package com.mx.goldnurse.exception;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.io.file.FileReader;
import cn.hutool.core.io.file.FileWriter;
import cn.hutool.core.util.RandomUtil;
import com.mx.goldnurse.dingtalk.DingTalkNotice;
import com.mx.goldnurse.dingtalk.ExceptionUtil;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author erBai
 * @version 1.0.0
 * @ClassName NoticeUtils
 * @Description TODO
 * @data 2020/11/6 5:18 下午
 */
@Service
public class ExceptionNoticeUtils {

    private static final String picUrl = "https://i.goldnurse.com/jinpaihushi/images/crm/work/studio/JP20201109155809ac5ae2e2072b409681f94c18659cbdc3.png";

    public static ErrorLog notice(HttpServletRequest request, Exception e,
                              String domain, String filePath, String accessToken,
                              String keyword, String serverName, boolean isAtAll, List<String> atMobiles){

        String fileName = System.currentTimeMillis() + "-" + RandomUtil.randomString(4);
        RandomUtil.randomString(4);
        DingTalkNotice.sendTextMessage(
                "运行时异常，请及时处理。\n ⬇️⬇️⬇️【" + fileName + "】\n",
                isAtAll,
                atMobiles,
                keyword,
                accessToken);

        // 获取请求头
        Enumeration<String> er = request.getHeaderNames();
        StringBuilder stringBuilder = new StringBuilder();
        String userId = "";
        while(er.hasMoreElements()){
            String name	= er.nextElement();
            String value = request.getHeader(name);
            if ("userId".equals(name)){
                userId = value;
            }
            stringBuilder.append(name).append("=").append(value).append("\n");
        }

        // form data
        Map<String, String[]> parameterMap = request.getParameterMap();

        String stackTrace =  ExceptionUtil.getStackTrace(e);
        String url = request.getRequestURL() + (request.getQueryString() == null ? "" : ("?" + request.getQueryString()));
        DateTime dateTime = new DateTime();
        String errorStencil = filePath + "errorLog-110.html";
        String datePath = dateTime.year() + "/" + (dateTime.month() + 1) + "/" + dateTime.dayOfMonth() + "/";
        filePath = filePath + datePath;

        ErrorLog errorLog = baleLog(url, request.getMethod(), stringBuilder.toString(), stackTrace, fileName, filePath, serverName, errorStencil,userId, parameterMap);

        DingTalkNotice.sendLinkMessage(
                domain + datePath + fileName + ".html",
                picUrl,
                "➡️ 点击查看【"+ fileName +"】错误详情",
                request.getRequestURL().toString(),
                keyword,
                accessToken);
        errorLog.setErrorLogUrl(domain + datePath + fileName + ".html");
        return errorLog;
    }

    private static ErrorLog baleLog(String url, String method, String headers, String stackTrace,
                                String fileName, String filePath, String serverName, String errorStencil,String userId,Map<String, String[]> parameterMap){

        String templateContent = "";
        // 读取模板文件
        FileReader fileReader = new FileReader(errorStencil);

        templateContent = fileReader.readString();
        templateContent = templateContent.replaceAll("##title##", fileName)
                .replaceAll("##url##", url)
                .replaceAll("##method##", method)
                .replaceAll("##headers##", headers)
                .replaceAll("##formdata##", handleFormdata(parameterMap))
                .replaceAll("##serverMsg##", getServerMsg(serverName))
                .replaceAll("##stackTrace##", Matcher.quoteReplacement(stackTrace));
        FileWriter fileWriter = new FileWriter(filePath + fileName +".html");
        fileWriter.write(templateContent);

        Map<String, String> host = getHost();
        ErrorLog errorLog = new ErrorLog();
        errorLog.setApiUrl(url);
        errorLog.setDomain(getDomain(url));
        errorLog.setUserId(userId);
        errorLog.setIp(host.get("hostAddress"));
        errorLog.setServerName(serverName);
        errorLog.setErrorLogUrl(filePath + fileName +".html");
        errorLog.setCreateTime(new DateTime());
        return errorLog;
    }

    private static String handleFormdata(Map<String, String[]> parameterMap){
        StringBuilder str = new StringBuilder();

        for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
            str.append(entry.getKey()).append(" = ").append(Arrays.toString(entry.getValue())).append("\n");
        }
        return str.toString();
    }

    private static String getDomain(String url){
        String pattern = "((http://)|(https://))?([a-zA-Z0-9]([a-zA-Z0-9\\-]{0,61}[a-zA-Z0-9])?\\.)+[a-zA-Z]{2,6}/";
        // 创建 Pattern 对象
        Pattern r = Pattern.compile(pattern);

        // 现在创建 matcher 对象
        Matcher m = r.matcher(url);

        return m.find() ? m.group(0) : "";
    }

    public static String getServerMsg(String serverName) {
        //进程号
        String processId = "进程号 : " + getProcessId() + "\n";
        Map<String, String> host = getHost();
        return processId + host.get("hostAddress") + host.get("hostName") + "服务名称 : " + serverName;
    }

    public static Map<String, String> getHost(){
        Map<String, String> map = new HashMap<>(2);
        String hostAddress;
        String hostName;
        try {
            InetAddress addr = InetAddress.getLocalHost();
            hostAddress = "Local HostAddress: " + addr.getHostAddress() + "\n";
            hostName = "Local host name: " + addr.getHostName() + "\n";
        } catch (UnknownHostException e) {
            hostAddress = "Local HostAddress: 未知" + "\n";
            hostName = "Local host name: 未知" + "\n";
        }
        map.put("hostAddress",hostAddress);
        map.put("hostName",hostName);
        return map;
    }

    public static int getProcessId() {
        return Integer.parseInt(ManagementFactory.getRuntimeMXBean().getName().split("@")[0]);
    }

}
