package com.mx.goldnurse.utils;

import cn.hutool.core.util.IdUtil;

/**
 * 主键id生成类
 * <br>
 * created date 2021/8/31 11:19
 *
 * @author dgl
 */
public class MxIdUtils {


    /**
     * 获取雪花主键id
     *
     * @return
     */
    public static String getId() {
        long workerId = MxIdConfig.getWorkerId();
        long datacenterId = MxIdConfig.getDatacenterId();
        return String.valueOf(IdUtil.getSnowflake(workerId, datacenterId).nextId());
    }

    /**
     * 获取雪花主键id
     *
     * @return
     */
    public static long getLongId() {
        long workerId = MxIdConfig.getWorkerId();
        long datacenterId = MxIdConfig.getDatacenterId();
        return IdUtil.getSnowflake(workerId, datacenterId).nextId();
    }
}
