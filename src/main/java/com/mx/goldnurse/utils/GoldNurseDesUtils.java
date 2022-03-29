package com.mx.goldnurse.utils;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import lombok.extern.slf4j.Slf4j;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author user
 */
@Slf4j
public class GoldNurseDesUtils {

    public final static String DES_KEY_STRING = "jpcdaf57";

    public static String encrypt(String message, String key) throws Exception {
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");

        DESKeySpec desKeySpec = new DESKeySpec(key.getBytes(StandardCharsets.UTF_8));

        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
        IvParameterSpec iv = new IvParameterSpec(key.getBytes(StandardCharsets.UTF_8));
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);

        return encodeBase64(cipher.doFinal(message.getBytes(StandardCharsets.UTF_8))).replaceAll("\r\n|\r|\n", "");
    }

    public static String decrypt(String message, String key) throws Exception {
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        byte[] bytesrc = decodeBase64(message);
        DESKeySpec desKeySpec = new DESKeySpec(key.getBytes(StandardCharsets.UTF_8));
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
        IvParameterSpec iv = new IvParameterSpec(key.getBytes(StandardCharsets.UTF_8));
        cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
        byte[] retByte = cipher.doFinal(bytesrc);
        return new String(retByte, StandardCharsets.UTF_8);
    }

    public static byte[] convertHexString(String ss) {
        byte[] digest = new byte[ss.length() / 2];
        for (int i = 0; i < digest.length; i++) {
            String byteString = ss.substring(2 * i, 2 * i + 2);
            int byteValue = Integer.parseInt(byteString, 16);
            digest[i] = (byte) byteValue;
        }
        return digest;
    }

    public static String toHexString(byte b[]) {
        StringBuilder hexString = new StringBuilder();
        for (byte value : b) {
            String plainText = Integer.toHexString(0xff & value);
            if (plainText.length() < 2) {
                plainText = "0" + plainText;
            }
            hexString.append(plainText);
        }

        return hexString.toString();
    }


    public static String encodeBase64(byte[] b) {
        return new BASE64Encoder().encode(b);
    }

    public static byte[] decodeBase64(String base64String) throws IOException {
        return new BASE64Decoder().decodeBuffer(base64String);
    }


    /**
     * 测试加密解密
     *
     * @param args
     * @throws Exception
     */

    /**
     * @description: 解密
     * @param: [data]
     * @return: java.lang.String
     */
    public static String defaultDecrypt(String data) {
        log.debug("[请求参数]-[解密参数]-[data]"+data);
        if (null == data || StringUtils.isBlank(data)) {
            return null;
        }
        try {
            log.debug("[请求参数]-[解密开始]-[data]"+data);
            String decrypt = GoldNurseDesUtils.decrypt(data, DES_KEY_STRING);
            log.debug("[请求参数]-[解密后参数]-[decrypt]"+decrypt);
            return decrypt;
        } catch (Exception e) {
            return null;
        }
    }

    public static void main(String[] args) throws Exception {
        String s = encrypt("{\"channelId\":\"27c7cbeacee94619a340cb4f636b274a\",\"cityName\":\"随州市\"}\n",GoldNurseDesUtils.DES_KEY_STRING);
        System.out.println("加密结果   "+s);
        s=decrypt(s,GoldNurseDesUtils.DES_KEY_STRING);
        System.out.println("解密结果   "+s);
    }
}
