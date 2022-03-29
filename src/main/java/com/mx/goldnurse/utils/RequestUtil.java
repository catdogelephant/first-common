package com.mx.goldnurse.utils;

import cn.hutool.core.io.IoUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.util.TypeUtils;
import com.mx.goldnurse.config.SpringContextHolder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * created date 2021/10/19 14:02
 *
 * @author JiangYuhao
 */
@Slf4j
@Data
public class RequestUtil {


    private RequestUtil() {

    }

    public static RequestUtil build() {
        RequestUtil requestUtil = new RequestUtil();
        return requestUtil;
    }

    public HttpServletRequest getRequest() {
        HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return req;
    }

    public HttpServletResponse getResponse() {
        HttpServletResponse resp = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        return resp;
    }

    public Map<String, String> getHeaders() {
        HttpServletRequest request = getRequest();
        Enumeration<String> headerNames = request.getHeaderNames();
        Map<String, String> returnMap = new HashMap();

        if (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            returnMap.put(headerName, request.getHeader(headerName));
        }
        return returnMap;
    }

    public Map<String, String> getParameters() {
        // 参数Map
        Map properties = getRequest().getParameterMap();
        // 返回值Map
        Map<String, String> returnMap = new HashMap();
        Iterator entries = properties.entrySet().iterator();
        Map.Entry entry;
        String name = "";
        String value = "";
        while (entries.hasNext()) {
            entry = (Map.Entry) entries.next();
            name = (String) entry.getKey();
            Object valueObj = entry.getValue();
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
            returnMap.put(name, value);
        }
        return returnMap;
    }

    public String getBodyStr() {
        HttpServletRequest request = getRequest();
        String contentType = request.getHeader("content-type");
        if (null != contentType && contentType.toLowerCase().contains("application/json")) {
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
            } catch (IOException e) {
                log.error("请求body流读取失败" + e.getMessage(), e);
            }
            try {
                String body = IoUtil.read(reader);
                try {

                    return body;
                } catch (Exception e) {
                    log.error("JSON转换失败：" + body + e.getMessage(), e);
                    return "{}";
                }
            } catch (Exception e) {
                log.error("BufferedReader转换失败" + e.getMessage(), e);
                return "{}";
            }
        }
        return "{}";
    }

    public String getBodyDecryptStr(String decrypt) {
        String isEncry = getParameters().get("isEncry");
        if (Objects.equals(isEncry, "true")) {
            String bodyStr = getBodyStr();
            return bodyStr;
        }

        String bodyStr = JSON.parseObject(getBodyStr()).getString("str");
        try {
            bodyStr = GoldNurseDesUtils.decrypt(bodyStr, decrypt);
        } catch (Exception e) {
            e.printStackTrace();
            return "{}";
        }
        return bodyStr;
    }

    public JSONObject getBody(String decrypt) {
        String isEncry = getParameters().get("isEncry");
        if (Objects.equals(isEncry, "true")) {
            String bodyStr = getBodyStr();
            JSONObject result = JSON.parseObject(bodyStr);
            return result;
        }
        String bodyStr = getBodyDecryptStr(decrypt);
        JSONObject result = JSON.parseObject(bodyStr);
        return result;
    }

    public <T> T getBody(Class<T> clazz, String decrypt) {
        String isEncry = getParameters().get("isEncry");
        if (Objects.equals(isEncry, "true")) {
            String bodyStr = getBodyStr();
            T t = JSON.parseObject(bodyStr, clazz);
            return t;
        }
        String bodyStr = getBodyDecryptStr(decrypt);
        T t = JSON.parseObject(bodyStr, clazz);
        return t;
    }

    public JSONObject getBody() {
        String bodyStr = getBodyStr();
        JSONObject result = JSON.parseObject(bodyStr);
        return result;
    }

    public <T> T getBody(Class<T> clazz) {
        String bodyStr = getBodyStr();
        T t = JSON.parseObject(bodyStr, clazz);
        return t;
    }

    public <T> Optional<T> getBodyValue(String key, Class<T> clazz) {

        JSONObject body = getBody();
        if (null == body) {
            return Optional.empty();
        }
        Object value = body.get(key);
        if (null == value) {
            return Optional.empty();
        }
        Optional<T> optional = Optional.of(TypeUtils.castToJavaBean(value, clazz));
        return optional;

    }

    public <T> Optional<T> getParameterValue(String key, Class<T> clazz) {

        Map<String, String> parameters = getParameters();
        String value = parameters.get(key);
        if (null == value) {
            return Optional.empty();
        }
        Optional<T> optional = Optional.of(TypeUtils.castToJavaBean(value, clazz));
        return optional;

    }

    @Override
    public String toString() {
        Map<String, String> parameters = getParameters();
        JSONObject body = getBody();
        Map<String, String> headers = getHeaders();
        return JSON.toJSONString(
                new HashMap<String, Object>() {{
                    put("url", getRequest().getRequestURL());
                    put("headers", headers);
                    put("parameters", parameters);
                    put("body", body);
                }}
        );
    }

}
