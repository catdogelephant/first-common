package com.mx.goldnurse.utils;

import com.alibaba.excel.EasyExcel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * <br>
 * created date 2021/9/2 15:07
 *
 * @author JiangYuhao
 */
public class EasyExcelUtils {
    private static final Logger logger = LoggerFactory.getLogger(EasyExcelUtils.class);

    /**
     * @param fileName  文件路径名
     * @param sheetName sheet名
     * @param data      查询出来的数据
     * @param headList  传入的Excel头（例如：姓名，生日）
     * @param fileList  传入需要展示的字段（例如：姓名对应字段是name,生日对应字段是birthday）
     */
    public static void noModelWrite(String fileName, String sheetName, List data, List<String> headList, List<String> fileList){
        EasyExcel.write(fileName).head(head(headList)).sheet(sheetName).doWrite(dataList(data,fileList));
    }

    /**
     * 设置Excel头
     * @param headList  Excel头信息
     * @return
     */
    private static List<List<String>> head(List<String> headList) {
        List<List<String>> list = new ArrayList<>();

        for (String value : headList) {
            List<String> head = new ArrayList<>();
            head.add(value);
            list.add(head);
        }
        return list;
    }

    /**
     * 设置表格信息
     * @param dataList  查询出的数据
     * @param fileList  需要显示的字段
     * @return
     */
    private static List<List<Object>> dataList(List<Object> dataList, List<String> fileList) {
        List<List<Object>> list = new ArrayList<>();
        for (Object person : dataList) {
            List<Object> data = new ArrayList<>();
            for (String fieldName : fileList) {
                /**通过反射根据需要显示的字段，获取对应的属性值*/
                data.add(getFieldValue(fieldName, person));
            }
            list.add(data);
        }
        return list;
    }

    /**
     * 根据传入的字段获取对应的get方法，如name,对应的getName方法
     * @param fieldName  字段名
     * @param person    对象
     * @return
     */
    private static Object getFieldValue(String fieldName, Object person) {
        try {

            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + fieldName.substring(1);
            Method method = person.getClass().getMethod(getter);
            return method.invoke(person);
        } catch (Exception e) {
            logger.error("使用反射获取对象属性值失败", e);
            return null;
        }

    }


}