package com.mx.goldnurse.utils;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.metadata.Sheet;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Easyexcel工具类
 * <br>
 * created date 2021/8/17 14:53
 *
 * @author JiangYuhao
 */
@Slf4j
public class EasyExcelUtil {

    private static Sheet initSheet;

    static {
        initSheet = new Sheet(1, 0);
        initSheet.setSheetName("sheet");
        //设置自适应宽度
        initSheet.setAutoWidth(Boolean.TRUE);
    }

    /**
     * 通过响应流导出文件
     * <br>
     * created date 2021/8/17 14:57
     *
     * @author JiangYuhao
     */
    public static void responseWrite(String fileName, Class aClass, List list) {
        try {
            HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
            response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes(), "iso-8859-1"));
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            EasyExcel.write(response.getOutputStream(), aClass).sheet("Sheet1").doWrite(list);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 导出文件
     * created date 2021/9/1 13:38
     *
     * @author JiangYuhao
     */
    public static void saveFileWrite(String fileName, Class aClass, List list) {

        EasyExcel.write(fileName, aClass).sheet("Sheet1").doWrite(list);

    }

    /**
     * 导出文件，排除不需要导出的字段
     * created date 2021/9/1 13:38
     *
     * @author JiangYuhao
     */
    public static void saveFileWrite(String fileName, Class aClass, List list, Set<String> excludeColumnFiledNames) {
        EasyExcel.write(fileName, aClass).excludeColumnFiledNames(excludeColumnFiledNames).sheet("Sheet1").doWrite(list);
    }

    /**
     * 读取少于1000行数据
     *
     * @param filePath 文件绝对路径
     * @return
     */
    public static List<Object> readLessThan1000Row(String filePath) {
        return readLessThan1000RowBySheet(filePath, null);
    }

    /**
     * 读小于1000行数据, 带样式
     * filePath 文件绝对路径
     * initSheet ：
     * sheetNo: sheet页码，默认为1
     * headLineMun: 从第几行开始读取数据，默认为0, 表示从第一行开始读取
     * clazz: 返回数据List<Object> 中Object的类名
     */
    public static List<Object> readLessThan1000RowBySheet(String filePath, Sheet sheet) {
        if (!StringUtils.hasText(filePath)) {
            return null;
        }

        sheet = sheet != null ? sheet : initSheet;

        InputStream fileStream = null;
        try {
            fileStream = new FileInputStream(filePath);
            return EasyExcelFactory.read(fileStream, sheet);
        } catch (FileNotFoundException e) {
            log.info("找不到文件或文件路径错误, 文件：{}", filePath);
        } finally {
            try {
                if (fileStream != null) {
                    fileStream.close();
                }
            } catch (IOException e) {
                log.info("excel文件读取失败, 失败原因：{}", e);
            }
        }
        return null;
    }

    /**
     * 读大于1000行数据
     *
     * @param filePath 文件觉得路径
     * @return
     */
    public static List<Object> readMoreThan1000Row(String filePath) {
        return readMoreThan1000RowBySheet(filePath, null);
    }

    /**
     * 读大于1000行数据, 带样式
     *
     * @param filePath 文件觉得路径
     * @return
     */
    public static List<Object> readMoreThan1000RowBySheet(String filePath, Sheet sheet) {
        if (!StringUtils.hasText(filePath)) {
            return null;
        }

        sheet = sheet != null ? sheet : initSheet;

        InputStream fileStream = null;
        try {
            fileStream = new FileInputStream(filePath);
            EasyExcelUtil.ExcelListener excelListener = new EasyExcelUtil.ExcelListener();
            EasyExcelFactory.readBySax(fileStream, sheet, excelListener);
            return excelListener.getDatas();
        } catch (FileNotFoundException e) {
            log.error("找不到文件或文件路径错误, 文件：{}", filePath);
        } finally {
            try {
                if (fileStream != null) {
                    fileStream.close();
                }
            } catch (IOException e) {
                log.error("excel文件读取失败, 失败原因：{}", e);
            }
        }
        return null;
    }

    /**
     * 生成excle
     *
     * @param filePath 绝对路径, 如：/home/chenmingjian/Downloads/aaa.xlsx
     * @param data     数据源
     * @param head     表头
     */
    public static void writeBySimple(String filePath, List<List<Object>> data, List<String> head) {
        writeSimpleBySheet(filePath, data, head, null);
    }

    /**
     * 生成excle
     *
     * @param filePath 绝对路径, 如：/home/chenmingjian/Downloads/aaa.xlsx
     * @param data     数据源
     * @param sheet    excle页面样式
     * @param head     表头
     */
    public static void writeSimpleBySheet(String filePath, List<List<Object>> data, List<String> head, Sheet sheet) {
        sheet = (sheet != null) ? sheet : initSheet;

        if (head != null) {
            List<List<String>> list = new ArrayList<>();
            head.forEach(h -> list.add(Collections.singletonList(h)));
            sheet.setHead(list);
        }

        OutputStream outputStream = null;
        ExcelWriter writer = null;
        try {
            outputStream = new FileOutputStream(filePath);
            writer = EasyExcelFactory.getWriter(outputStream);
            writer.write1(data, sheet);
        } catch (FileNotFoundException e) {
            log.error("找不到文件或文件路径错误, 文件：{}", filePath);
        } finally {
            try {
                if (writer != null) {
                    writer.finish();
                }

                if (outputStream != null) {
                    outputStream.close();
                }

            } catch (IOException e) {
                log.error("excel文件导出失败, 失败原因：{}", e);
            }
        }

    }

    /**
     * 生成excle
     *
     * @param filePath 绝对路径, 如：/home/chenmingjian/Downloads/aaa.xlsx
     * @param data     数据源
     */
    public static void writeWithTemplate(String filePath, List<? extends BaseRowModel> data) {
        writeWithTemplateAndSheet(filePath, data, null);
    }

    /**
     * 生成excle
     *
     * @param filePath 绝对路径, 如：/home/chenmingjian/Downloads/aaa.xlsx
     * @param data     数据源
     * @param sheet    excle页面样式
     */
    public static void writeWithTemplateAndSheet(String filePath, List<? extends BaseRowModel> data, Sheet sheet) {
        if (CollectionUtils.isEmpty(data)) {
            return;
        }

        sheet = (sheet != null) ? sheet : initSheet;
        sheet.setClazz(data.get(0).getClass());

        OutputStream outputStream = null;
        ExcelWriter writer = null;
        try {
            outputStream = new FileOutputStream(filePath);
            writer = EasyExcelFactory.getWriter(outputStream);
            writer.write(data, sheet);
        } catch (FileNotFoundException e) {
            log.error("找不到文件或文件路径错误, 文件：{}", filePath);
        } finally {
            try {
                if (writer != null) {
                    writer.finish();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                log.error("excel文件导出失败, 失败原因：{}", e);
            }
        }

    }

    /**
     * 生成多Sheet的excle
     *
     * @param filePath              绝对路径, 如：/home/chenmingjian/Downloads/aaa.xlsx
     * @param multipleSheelPropetys
     */
    public static void writeWithMultipleSheel(String filePath, List<EasyExcelUtil.MultipleSheelPropety> multipleSheelPropetys) {
        if (CollectionUtils.isEmpty(multipleSheelPropetys)) {
            return;
        }

        OutputStream outputStream = null;
        ExcelWriter writer = null;
        try {
            outputStream = new FileOutputStream(filePath);
            writer = EasyExcelFactory.getWriter(outputStream);
            for (EasyExcelUtil.MultipleSheelPropety multipleSheelPropety : multipleSheelPropetys) {
                Sheet sheet = multipleSheelPropety.getSheet() != null ? multipleSheelPropety.getSheet() : initSheet;
                if (!CollectionUtils.isEmpty(multipleSheelPropety.getData())) {
                    sheet.setClazz(multipleSheelPropety.getData().get(0).getClass());
                }
                writer.write(multipleSheelPropety.getData(), sheet);
            }

        } catch (FileNotFoundException e) {
            log.error("找不到文件或文件路径错误, 文件：{}", filePath);
        } finally {
            try {
                if (writer != null) {
                    writer.finish();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                log.error("excel文件导出失败, 失败原因：{}", e);
            }
        }

    }


    /*********************匿名内部类开始，可以提取出去******************************/

    @Data
    public static class MultipleSheelPropety {

        private List<? extends BaseRowModel> data;

        private Sheet sheet;
    }

    /**
     * 解析监听器，
     * 每解析一行会回调invoke()方法。
     * 整个excel解析结束会执行doAfterAllAnalysed()方法
     *
     * @author: chenmingjian
     * @date: 19-4-3 14:11
     */
    @Getter
    @Setter
    public static class ExcelListener extends AnalysisEventListener {

        private List<Object> datas = new ArrayList<>();

        /**
         * 逐行解析
         * object : 当前行的数据
         */
        @Override
        public void invoke(Object object, AnalysisContext context) {
            //当前行
            // context.getCurrentRowNum()
            if (object != null) {
                datas.add(object);
            }
        }


        /**
         * 解析完所有数据后会调用该方法
         */
        @Override
        public void doAfterAllAnalysed(AnalysisContext context) {
            //解析结束销毁不用的资源
        }

    }

    /************************匿名内部类结束，可以提取出去***************************/

}
