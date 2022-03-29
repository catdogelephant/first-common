package com.mx.goldnurse.utils;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.BaseFont;
import lombok.extern.slf4j.Slf4j;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * PDF生成工具类
 *
 * @author Goofy http://www.xdemo.org
 */
@SuppressWarnings("all")
@Slf4j
public class PdfUtils {

    /**
     * 生成PDF到文件
     *
     * @param ftlPath       模板文件路径（不含文件名）
     * @param ftlName       模板文件吗（不含路径）
     * @param imageDiskPath 图片的磁盘路径
     * @param data          数据
     * @param outputFile    目标文件（全路径名称）
     * @throws Exception
     */
    public static void generateToFile(String ftlPath, String ftlName, String imageDiskPath, Object data, String outputFile) throws Exception {
        String html = PdfHelper.getPdfContent(ftlPath, ftlName, data);
        OutputStream out = null;
        ITextRenderer render = null;
        out = new FileOutputStream(outputFile);
        render = PdfHelper.getRender();
        render.setDocumentFromString(html);

        if (imageDiskPath != null && !imageDiskPath.equals("")) {
            // html中如果有图片，图片的路径则使用这里设置的路径的相对路径，这个是作为根路径
            render.getSharedContext().setBaseURL("file:/" + imageDiskPath);
        }
        render.layout();
        render.createPDF(out);
        render.finishPDF();
        render = null;
        out.close();
    }

    /**
     * 生成PDF到输出流中（ServletOutputStream用于下载PDF）
     *
     * @param ftlPath       ftl模板文件的路径（不含文件名）
     * @param ftlName       ftl模板文件的名称（不含路径）
     * @param imageDiskPath 如果PDF中要求图片，那么需要传入图片所在位置的磁盘路径
     * @param data          输入到FTL中的数据
     * @param response      HttpServletResponse
     * @return
     * @throws TemplateNotFoundException
     * @throws MalformedTemplateNameException
     * @throws ParseException
     * @throws IOException
     * @throws TemplateException
     * @throws DocumentException
     */
    public static OutputStream generateToServletOutputStream(String ftlPath, String ftlName, String imageDiskPath,
                                                             Object data, HttpServletResponse response) throws Exception {

        String html = PdfHelper.getPdfContent(ftlPath, ftlName, data);
        OutputStream out = null;
        ITextRenderer render = null;
        out = response.getOutputStream();
        render = PdfHelper.getRender();
        render.setDocumentFromString(html);
        if (imageDiskPath != null && !imageDiskPath.equals("")) {
            // html中如果有图片，图片的路径则使用这里设置的路径的相对路径，这个是作为根路径
            render.getSharedContext().setBaseURL("file:/" + imageDiskPath);
        }
        render.layout();
        render.createPDF(out);
        render.finishPDF();
        render = null;
        return out;
    }

    /**
     * 将html文件转化为PDF文件
     *
     * @param filePath   存储PDF文件位置
     * @param htmlString html字符串
     * @param path       图片存储的相对路径
     * @param ttf        字体路径 "F:/testPDF/ttf/arial unicode ms.ttf"
     */
    public static boolean htmlToPdf(String filePath, String htmlString, String path, String ttf) {
        try {
            OutputStream fileOutputStream = new FileOutputStream(filePath);
            ITextRenderer iTextRenderer = new ITextRenderer();
            ITextFontResolver fontResolver = iTextRenderer.getFontResolver();
            //指定字体，为了支持中文
            fontResolver.addFont(ttf, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            iTextRenderer.setDocumentFromString(htmlString);
            //解决图片的相对路径问题
            iTextRenderer.getSharedContext().setBaseURL("file:" + path);
            iTextRenderer.layout();
            iTextRenderer.createPDF(fileOutputStream);
            iTextRenderer.finishPDF();
            iTextRenderer = null;
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("将html文件转化为PDF文件异常：", e);
            return false;
        }
        return true;
    }

}
