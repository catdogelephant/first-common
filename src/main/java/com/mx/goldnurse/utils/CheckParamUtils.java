package com.mx.goldnurse.utils;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.mx.goldnurse.annotation.PassCheck;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.List;

/**
 * 参数校验工具
 */
@Slf4j
public class CheckParamUtils {


    /**
     * @param args 需要校验的参数
     * @description: 校验参数为空
     * @return: java.lang.Boolean true:参数为空 false:参数不为空
     * @author: sx
     * @time: 2021/2/3 19:48
     */
    public static Boolean checkNull(Object... args) {
        if (null != args) {
            for (Object arg : args) {
                if (arg instanceof String) {
                    String str = (String) arg;
                    if (StringUtils.isBlank(str)) {
                        return true;
                    }
                } else {
                    if (null == arg) {
                        return true;
                    }
                }
            }
            return false;
        }
        return true;
    }

    /**
     * @param object 被校验对象
     * @description: 对象属性值非空校验（校验对象中所有属性）
     * @return: java.lang.Boolean true:参数为空 false:参数不为空
     * @author: sx
     * @time: 2021/2/4 9:39
     */
    public static Boolean checkBeanNull(Object object) {
        if (null == object) {
            log.info("[对象属性值非空校验] - [对象为空] - object={}", object);
            return true;
        }
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            if ("serialVersionUID".equals(field.getName())) {
                continue;
            }
            try {
                field.setAccessible(true);
                PassCheck passCheck = field.getAnnotation(PassCheck.class);
                if (null != passCheck) {
                    continue;
                }
                Object value = field.get(object);
                Boolean listFlag = checkObjectOfListParamNull(value);
                if (listFlag) {
                    log.info("[对象属性值非空校验] - [集合属性中有空参数] - value={}", value);
                    return true;
                }
                Boolean flag = checkNull(value);
                if (flag) {
                    log.info("[对象属性值非空校验] - [为空属性：{}]", field.getName());
                    return true;
                }
            } catch (Exception e) {
                log.info("校验出错", e);
                return true;
            }
        }
        return false;
    }

    /**
     * @param object 被校验对象
     * @description: 对象属性值非空校验（校验对象中所有属性）
     * @return: java.lang.Boolean true:参数为空 false:参数不为空
     * @author: sx
     * @time: 2021/2/4 9:39
     */
    public static Boolean checkBeanNull(Object object, Object... args) {
        if (null == object) {
            log.info("[对象属性值非空校验] - [对象为空] - object={}", object);
            return true;
        }
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            if ("serialVersionUID".equals(field.getName())) {
                continue;
            }
            try {
                field.setAccessible(true);
                PassCheck passCheck = field.getAnnotation(PassCheck.class);
                if (null != passCheck) {
                    continue;
                }
                Object value = field.get(object);
                if (value instanceof List) {
                    Boolean listFlag = checkObjectOfListParamNull(value);
                    if (listFlag) {
                        log.info("[对象属性值非空校验] - [集合属性中有空参数] - value={}", value);
                        return true;
                    }
                } else {
                    Boolean flag = checkNull(value);
                    if (flag) {
                        log.info("[对象属性值非空校验] - [为空属性：{}]", field.getName());
                        return true;
                    }
                }
            } catch (Exception e) {
                log.info("校验出错", e);
                return true;
            }
        }
        return checkNull(args);
    }

    private static Boolean checkObjectOfListParamNull(Object obj) {
        if (obj instanceof List) {
            List list = (List) obj;
            for (Object o : list) {
                Boolean flag = checkBeanNull(o);
                if (flag) {
                    return true;
                }
            }
        }
        return false;
    }
}
