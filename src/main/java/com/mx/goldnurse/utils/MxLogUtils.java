package com.mx.goldnurse.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

/**
 * 打印日志工具类
 * <br>
 * created date 2022/2/14 14:46
 *
 * @author sx
 */
@Slf4j
public class MxLogUtils {

    @Value("${spring.application.name}")
    public static String appName;

    public static void error(String errorMsg, Throwable t) {
        String tokenUserId = RequestLocal.getTokenUserId();
        ThreadLocal<Object> objectThreadLocal = new ThreadLocal<>();
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        String className = "";
        if (stackTrace.length > 1) {
            className = Thread.currentThread().getStackTrace()[1].getClassName();
        }
        log.error("服务名：{} - 类名：{} - 接口名：{} - 错误内容：{} - 自定义内容：{}", appName, className, Thread.currentThread().getName(), "操作失败", errorMsg);
        log.error("异常：", t);
    }
}
