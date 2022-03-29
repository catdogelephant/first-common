package com.mx.goldnurse.dingtalk;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author peishaopeng
 */
public class ExceptionUtil {

    /**
     * 获取异常的堆栈信息
     * @param t
     * @return 堆栈信息
     */
    public static String getStackTrace(Throwable t) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        try {
            t.printStackTrace(pw);
            return sw.toString();
        } finally {
            pw.close();
        }
    }
}
