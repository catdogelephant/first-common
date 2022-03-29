package com.mx.goldnurse.Aspect;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mx.goldnurse.annotation.Desensitization;
import com.mx.goldnurse.constants.ConstantsUtils;
import com.mx.goldnurse.jwt.TokenUser;
import com.mx.goldnurse.response.SuccessResponseData;
import com.mx.goldnurse.utils.JacksonUtil;
import com.mx.goldnurse.utils.RequestLocal;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author 陈龙飚
 * @version V1.0
 * @class: DesensitizationAspect
 * @date 2022/1/4
 * @project goldnurse-commons
 * @description
 */
@Slf4j
@Component
@Aspect
public class DesensitizationAspect {

    /**
     * 切Desensitization注解
     */
    @Pointcut("@annotation(com.mx.goldnurse.annotation.Desensitization)")
    public void executeDesensitization() {
    }

    @Around("executeDesensitization()")
    public Object handleDesensitizationMethod(ProceedingJoinPoint pjp) throws Throwable {
        //获取方法
        Method method = ((MethodSignature) pjp.getSignature()).getMethod();
        //获取脱敏注解
        Desensitization desensitization = method.getAnnotation(Desensitization.class);
        String[] cardNodes = desensitization.cardNode();
        String[] phoneNodes = desensitization.phoneNode();
        Object proceed = pjp.proceed();
        String json = JacksonUtil.toJson(proceed);
        JSONObject result = JSONObject.parseObject(json);
        Object result1 = result.get("result");
        JSONObject data;
        boolean checkType =false;
        if (result1 != null){
            data = JSONObject.parseObject(JacksonUtil.toJson(result1));
            checkType = true;
        }else {
            data = result;
        }
        TokenUser tokenUser = RequestLocal.getTokenUser();
        String showPhone = tokenUser.getSubject().getStr("showPhone");
        String showIdCard = tokenUser.getSubject().getStr("showIdCard");
        if (showIdCard == null || showIdCard.equals(ConstantsUtils.STR_CODE_0)) {
            if (cardNodes != null && cardNodes.length > 0) {
                for (String cardNode : cardNodes) {
                    if (cardNode.contains("::")){ //2级结构 。第二级是对象    split 0 是对象名  split1 是字段名
                        String[] split = cardNode.split("::");
                        JSONObject jsonObject = data.getJSONObject(split[0]);
                        String string = jsonObject.getString(split[1]);
                        if (string == null){
                            continue;
                        }
                        String s = string.replaceAll("(?<=\\w{3})\\w(?=\\w{4})", "*");
                        jsonObject.put(split[1],s);
                        data.put(split[0], jsonObject);
                    }else if (cardNode.contains(";;")){//2级结构 。第二级是数组, split 0 是数组名  split1 是字段名
                        String[] split = cardNode.split(";;");
                        JSONArray jsonArray = data.getJSONArray(split[0]);
                        if (jsonArray == null){
                            continue;
                        }
                        JSONArray jsonArray1 = new JSONArray();
                        for (int i = 0;i<jsonArray.size();i++) {
                            JSONObject j1 = jsonArray.getJSONObject(i);
                            String string = j1.getString(split[1]);
                            if (string == null){
                                continue;
                            }
                            String s = string.replaceAll("(?<=\\w{3})\\w(?=\\w{4})", "*");
                            j1.put(split[1], s);
                            jsonArray1.add(j1);
                        }
                        data.put(split[0], jsonArray1);
                    }else if (cardNode.contains(";P;")){//2级结构 。第二级是分页数组, split 0 是数组名  split1 是字段名
                        String[] split = cardNode.split(";P;");
                        JSONObject jsonObject = data.getJSONObject(split[0]);
                        JSONArray list = jsonObject.getJSONArray("list");
                        JSONArray rlist = jsonObject.getJSONArray("records");
                        JSONArray jsonArray = new JSONArray();
                        if (list == null && rlist == null){
                            continue;
                        }
                        String key = "";
                        if (list == null) {
                            key = "list";
                            jsonArray = rlist;
                        } else {
                            key = "records";
                            jsonArray = list;
                        }
                        JSONArray jsonArray1 = new JSONArray();
                        for (int i = 0;i<jsonArray.size();i++) {
                            JSONObject j1 = jsonArray.getJSONObject(i);
                            String string = j1.getString(split[1]);
                            if (string == null){
                                continue;
                            }
                            String s = string.replaceAll("(?<=\\w{3})\\w(?=\\w{4})", "*");
                            j1.put(split[1], s);
                            jsonArray1.add(j1);
                        }
                        jsonObject.put(key,jsonArray1);
                        data.put(split[0], jsonObject);
                    }else if (cardNode.contains("L,,")){//2级结构 。第一级是分页数组, split 0 是数组标识符  split1 是字段名
                        String[] split = cardNode.split("L,,");
                        JSONArray list = data.getJSONArray("list");
                        JSONArray rlist = data.getJSONArray("records");
                        JSONArray jsonArray = new JSONArray();
                        if (list == null && rlist == null){
                            continue;
                        }
                        String key = "";
                        if (list == null) {
                            key = "list";
                            jsonArray = rlist;
                        } else {
                            key = "records";
                            jsonArray = list;
                        }
                        JSONArray jsonArray1 = new JSONArray();
                        for (int i = 0;i<jsonArray.size();i++) {
                            JSONObject j1 = jsonArray.getJSONObject(i);
                            String string = j1.getString(split[1]);
                            if (string == null){
                                continue;
                            }
                            String s = string.replaceAll("(?<=\\w{3})\\w(?=\\w{4})", "*");
                            j1.put(split[1], s);
                            jsonArray1.add(j1);
                        }
                        data.put(key, jsonArray1);
                    }else {
                        String string = data.getString(cardNode);
                        if (string == null){
                            continue;
                        }
                        String s = string.replaceAll("(?<=\\w{3})\\w(?=\\w{4})", "*");
                        data.put(cardNode,s);
                    }

                }
            }
        }
        if (showPhone == null || showPhone.equals(ConstantsUtils.STR_CODE_0)) {
            if (phoneNodes != null && phoneNodes.length > 0) {
                for (String phoneNode : phoneNodes) {
                    if (phoneNode.contains("::")){
                        String[] split = phoneNode.split("::");
                        JSONObject jsonObject = data.getJSONObject(split[0]);
                        String string = jsonObject.getString(split[1]);
                        if (string == null){
                            continue;
                        }
                        String s = string.replaceAll("(?<=\\w{3})\\w(?=\\w{4})", "*");
                        jsonObject.put(split[1],s);
                        data.put(split[0], jsonObject);
                    }else if (phoneNode.contains(";;")){
                        String[] split = phoneNode.split(";;");
                        JSONArray jsonArray = data.getJSONArray(split[0]);
                        if (jsonArray == null){
                            continue;
                        }
                        JSONArray jsonArray1 = new JSONArray();
                        for (int i = 0;i<jsonArray.size();i++) {
                            JSONObject j1 = jsonArray.getJSONObject(i);
                            String string = j1.getString(split[1]);
                            if (string == null){
                                continue;
                            }
                            String s = string.replaceAll("(?<=\\w{3})\\w(?=\\w{4})", "*");
                            j1.put(split[1], s);
                            jsonArray1.add(j1);
                        }
                        data.put(split[0], jsonArray1);
                    }else if (phoneNode.contains(";P;")){//2级结构 。第二级是分页数组, split 0 是数组名  split1 是字段名
                        String[] split = phoneNode.split(";P;");
                        JSONObject jsonObject = data.getJSONObject(split[0]);
                        JSONArray list = jsonObject.getJSONArray("list");
                        JSONArray rlist = jsonObject.getJSONArray("records");
                        JSONArray jsonArray = new JSONArray();
                        if (list == null && rlist == null){
                            continue;
                        }
                        String key = "";
                        if (list == null) {
                            key = "list";
                            jsonArray = rlist;
                        } else {
                            key = "records";
                            jsonArray = list;
                        }
                        JSONArray jsonArray1 = new JSONArray();
                        for (int i = 0;i<jsonArray.size();i++) {
                            JSONObject j1 = jsonArray.getJSONObject(i);
                            String string = j1.getString(split[1]);
                            if (string == null){
                                continue;
                            }
                            String s = string.replaceAll("(?<=\\w{3})\\w(?=\\w{4})", "*");
                            j1.put(split[1], s);
                            jsonArray1.add(j1);
                        }
                        jsonObject.put(key,jsonArray1);
                        data.put(split[0], jsonObject);
                    }else if (phoneNode.contains("L,,")){//2级结构 。第一级是分页数组, split 0 是数组标识符  split1 是字段名
                        String[] split = phoneNode.split("L,,");
                        JSONArray list = data.getJSONArray("list");
                        JSONArray rlist = data.getJSONArray("records");
                        JSONArray jsonArray = new JSONArray();
                        if (list == null && rlist == null){
                            continue;
                        }
                        String key = "";
                        if (list == null) {
                            key = "list";
                            jsonArray = rlist;
                        } else {
                            key = "records";
                            jsonArray = list;
                        }
                        JSONArray jsonArray1 = new JSONArray();
                        for (int i = 0;i<jsonArray.size();i++) {
                            JSONObject j1 = jsonArray.getJSONObject(i);
                            String string = j1.getString(split[1]);
                            if (string == null){
                                continue;
                            }
                            String s = string.replaceAll("(?<=\\w{3})\\w(?=\\w{4})", "*");
                            j1.put(split[1], s);
                            jsonArray1.add(j1);
                        }
                        data.put(key, jsonArray1);
                    } else {
                        String string = data.getString(phoneNode);
                        if (string == null){
                            continue;
                        }
                        String s = string.replaceAll("(?<=\\w{3})\\w(?=\\w{4})", "*");
                        data.put(phoneNode,s);
                    }
                }
            }
        }
        if (!checkType){
            return data;
        }else {
            SuccessResponseData successResponseData = new SuccessResponseData(data);
            return successResponseData;
        }
    }
}
