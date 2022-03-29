package com.mx.goldnurse.utils;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * created date 2021/10/19 14:02
 *
 * @author JiangYuhao
 */
@Slf4j
@Data
public class OrderThreadLocalUtil {


    private static ThreadLocal<String> orderIdLocal = new ThreadLocal<>();


    public static String getTokenUserId() {
        return orderIdLocal.get();
    }

    public static void setTokenUserId(String orderId) {
        orderIdLocal.set(orderId);
    }

    public static void removeTokenUserId() {
        orderIdLocal.remove();
    }

}
