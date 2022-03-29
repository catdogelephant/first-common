package com.mx.goldnurse.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * 二维码工具类
 * <br>
 * created date 2021/1/26 13:50
 *
 * @author Devil
 */
@Slf4j
public class QrCodeUtils {

    /**
     * 线上环境
     */
//    private static final String DATA_RESOURCE = "https://resource.goldnurse.com/images/qrcode/";
//    private static final String QR_CODE_PATH = "/mnt/data/resource/images/qrcode/";
    /**
     * 测试环境
     */
    private static final String DATA_RESOURCE = "http://lan-resource.goldnurse.com/commons/images/qrcode/";
    private static final String QR_CODE_PATH = "/nas/data/resource/images/qrcode/";
//    private static final String QR_CODE_PATH = "D:\\erweima\\";
    /**
     * 默认是黑色
     */
    private static final int QR_CODE_COLOR = 0xFF000000;
    /**
     * 背景颜色
     */
    private static final int BACKGROUND_COLOR = 0xFFFFFFFF;

    /**
     * 二维码参数
     */
    private static final Map<EncodeHintType, Object> HINTS = new HashMap<>();

    static {
        //设置QR二维码的纠错级别（H为最高级别）具体级别信息
        HINTS.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        //设置编码方式
        HINTS.put(EncodeHintType.CHARACTER_SET, StandardCharsets.UTF_8);
    }

    /**
     * 生成二维码图片
     *
     * @param qrUrl      二维码链接url
     * @param qrCodeName 二维码名称
     * @param width      宽
     * @param height     高
     */
    public static String drawLogoQrCode(String qrUrl, String qrCodeName, Integer width, Integer height) {
        try {
            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
            // 参数顺序分别为：编码内容，编码类型，生成图片宽度，生成图片高度，设置参数
            BitMatrix bm = multiFormatWriter.encode(qrUrl, BarcodeFormat.QR_CODE, width, height, HINTS);
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

            // 开始利用二维码数据创建Bitmap图片，分别设为黑（0xFFFFFFFF）白（0xFF000000）两色
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    image.setRGB(x, y, bm.get(x, y) ? QR_CODE_COLOR : BACKGROUND_COLOR);
                }
            }

            image.flush();
            boolean flag = ImageIO.write(image, "png", new File(QR_CODE_PATH + qrCodeName + ".png"));
            if (!flag) {
                return null;
            }
        } catch (Exception e) {
            log.error("[生成二维码] - [生成异常]", e);
            return null;
        }
        return DATA_RESOURCE + qrCodeName + ".png";
    }
}
