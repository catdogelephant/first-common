package com.mx.goldnurse.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 主键id生成类
 * <br>
 * created date 2021/8/31 11:19
 *
 * @author dgl
 */
@Component
public class MxIdConfig {

    private static long workerId;
    private static long datacenterId;

    public static long getWorkerId() {
        return workerId;
    }

    @Value("${workerId:1}")
    public static void setWorkerId(long workerId) {
        MxIdConfig.workerId = workerId;
    }

    public static long getDatacenterId() {
        return datacenterId;
    }

    @Value("${datacenterId:1}")
    public static void setDatacenterId(long datacenterId) {
        MxIdConfig.datacenterId = datacenterId;
    }


}
