package com.mx.goldnurse.utils;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.baomidou.mybatisplus.core.toolkit.ArrayUtils;
import org.apache.poi.ss.formula.functions.T;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class ImportExcel {

    /**
     * 读取excel表格内容返回List<Map>
     *
     * @param inputStream excel文件流
     * @param head        表头数组
     * @param headerAlias 表头别名数组
     * @return
     */
    public static List<Map<String, Object>> importExcel(InputStream inputStream, String[] head, String[] headerAlias) {
        ExcelReader excelReader = getExcelReader(inputStream, head, headerAlias);
        //读取指点行开始的表数据（以下介绍的三个参数也可以使用动态传入，根据个人业务情况修改）
        //1：表头所在行数  2：数据开始读取位置   Integer.MAX_VALUE:数据读取结束行位置
        return excelReader == null ? null : excelReader.read(1, 2, Integer.MAX_VALUE);
    }

    /**
     * 读取excel表格内容返回List<Bean>
     *
     * @param inputStream excel文件流
     * @param head        表头数组
     * @param headerAlias 表头别名数组
     * @return
     */
    public static <T> List<T> importExcel(InputStream inputStream, String[] head, String[] headerAlias, Class<T> bean) {
        ExcelReader excelReader = getExcelReader(inputStream, head, headerAlias);
        //读取指定行开始的表数据（从0开始）
        return excelReader == null ? null : excelReader.read(1, 2, bean);
    }

    private static ExcelReader getExcelReader(InputStream inputStream, String[] head, String[] headerAlias){
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        List<Object> header = reader.readRow(1);
        //替换表头关键字
        if (ArrayUtils.isEmpty(head) || ArrayUtils.isEmpty(headerAlias) || head.length != headerAlias.length) {
            return null;
        }
        for (int i = 0; i < head.length; i++) {
            if (! head[i].equals(header.get(i))) {
                return null;
            }
            reader.addHeaderAlias(head[i], headerAlias[i]);
        }
        return reader;
    }

//    public static void main(String[] args) {
//
//        try {
//            InputStream inputStream = new FileInputStream("/Users/peishaopeng/Desktop/学员导入模版.xlsx");
//            String[] excelHead = {"序号", "学员姓名", "手机号码", "证件号码", "委托公司", "报名日期"};
//            String[] excelHeadAlias = {"num", "userName", "phone", "idCard", "company", "signUpDate"};
//            List<UploadDto> objects = ImportExcel.importExcel(inputStream, excelHead, excelHeadAlias, UploadDto.class);
//
//            System.out.println(objects.size());
//            System.out.println(objects);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//
//    }
}
