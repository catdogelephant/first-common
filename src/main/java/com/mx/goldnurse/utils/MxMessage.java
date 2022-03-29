package com.mx.goldnurse.utils;

import com.mx.goldnurse.config.MxMessageConfig;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * <br>
 * created date 2021/10/11 16:25
 *
 * @author JiangYuhao
 */
@Component
public class MxMessage {


    public static String getMsg(Integer code) {
        return MxMessageConfig.getProperties().getProperty(String.valueOf(code));
    }

    public static Properties getProperties() {
        return MxMessageConfig.getProperties();
    }


}
