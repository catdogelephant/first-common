package com.mx.goldnurse.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * 文件上传
 *
 * @Author gaopeng
 * @Date 17:36 2020/3/11
 **/
@Slf4j
public class FileUpload {

    /**
     * 文件上传
     *
     * @param file           文件
     * @param firstDirectory 一级目录
     * @param twoDirectory   二级目录
     * @param url            url地址
     * @param realPath       物理存储地址
     * @return
     * @Author gaopeng
     * @Description //TODO
     * @Date 2020/3/11
     **/
    public static Map<String, Object> fileUpload(MultipartFile file, String firstDirectory, String twoDirectory, String url, String realPath) {
        //验证文件类型
        //可以在上传文件的同时接收其它参数
        System.out.println("文件myfiles=" + file);
        //如果用的是Tomcat服务器，则文件会上传到\\%TOMCAT_HOME%\\webapps\\YourWebProject\\upload\\文件夹中
        //这里实现文件上传操作用的是commons.io.FileUtils类,它会自动判断/upload是否存在,不存在会自动创建
        //设置响应给前台内容的数据格式
//        response.setContentType("text/plain; charset=UTF-8");
        //设置响应给前台内容的PrintWriter对象
        //PrintWriter out = response.getWriter();
        //上传文件的原名(即上传前的文件名字)
        String originalFilename = null;
        //如果只是上传一个文件,则只需要MultipartFile类型接收文件即可,而且无需显式指定@RequestParam注解
        //如果想上传多个文件,那么这里就要用MultipartFile[]类型来接收文件,并且要指定@RequestParam注解
        //上传多个文件时,前台表单中的所有<input
        //type="file"/>的name都应该是myfiles,否则参数里的myfiles无法获取到所有上传的文件
        if (file == null) {
            return null;
        } else {
            originalFilename = file.getOriginalFilename();
            log.info("===============上传文件基本信息===============");
            log.info("文件原名: " + originalFilename);
            log.info("文件名称: " + file.getName());
            log.info("文件长度: " + file.getSize());
            String fileType = originalFilename.substring(originalFilename.lastIndexOf("."));
            log.info("文件类型: " + fileType);
            try {
                log.info("===============上传开始===============");
                String fileName = getNewFileName(originalFilename);
                String path = realPath + firstDirectory + "/" + twoDirectory + "/";
                String newFileName = fileName + fileType;
                // 这里不必处理IO流关闭的问题,因为FileUtils.copyInputStreamToFile()方法内部会自动把用到的IO流关掉
                // 此处也可以使用Spring提供的MultipartFile.transferTo(File
                // dest)方法实现文件的上传
                FileUtils.copyInputStreamToFile(file.getInputStream(), new File(path + newFileName));
                log.info("===============上传结束===============");
                log.info("文件地址：" + path + newFileName);
                Map<String, Object> map = new HashMap<>();
                map.put("path", path + newFileName);
                map.put("url", url + firstDirectory + "/" + twoDirectory + "/" + newFileName);
                map.put("originalFilename", originalFilename.substring(0, originalFilename.indexOf(".")));
                map.put("newFileName",fileName);
                return map;
            } catch (IOException e) {
                log.error("文件[" + originalFilename + "]上传失败");
                return null;
            }
        }
    }

    public static String getNewFileName(String oldfileName) {
        if (StringUtils.isNotBlank(oldfileName)) {
            String fileName = oldfileName.substring(0, oldfileName.lastIndexOf("."));
            Integer rannum = FileUpload.getRandomNumber();
            return "JP" + fileName + "00" + "-" + rannum;
        }
        return getDateFormat();
    }

    /**
     * 获取5位随机数
     *
     * @return
     */
    public static Integer getRandomNumber() {
        Random random = new Random();
        int rannum = (int) (random.nextDouble() * (99999 - 10000 + 1)) + 10000;// 获取5位随机数
        return rannum;
    }


    public static String getDateFormat() {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        Random random = new Random();
        int rannum = (int) (random.nextDouble() * (99999 - 10000 + 1)) + 10000;// 获取5位随机数
        return "JP" + df.format(new Date()) + "-" + rannum;
    }

    public static Integer getContains(Integer size, List<Integer> numbers) {
        Random random = new Random();
        int num = random.nextInt(size);
        Boolean result = numbers.contains(num);
        if (result) {
            return getContains(size, numbers);
        }
        return num;
    }

    public static void main(String[] args) throws IOException {
//        System.out.println("-----");
//        List<String> list = new ArrayList<>();
//        list.add("a");
//        list.add("b");
//        list.add("c");
//        list.add("d");
//        List<Integer> number = new ArrayList<>();
//        for (int i = 0; i< list.size(); i++){
//            Integer num = getContains(list.size(),number);
//            number.add(num);
//            System.out.println(list.get(num));
//        }


//        File pdfFile = new File("F:\\file\\test\\20201216145001.mp3");
//        FileInputStream fileInputStream = new FileInputStream(pdfFile);
//        MultipartFile multipartFile = new MockMultipartFile(pdfFile.getName(), pdfFile.getName(),
//                ContentType.APPLICATION_OCTET_STREAM.toString(), fileInputStream);
//        Map<String, Object> stringObjectMap = FileUpload.fileUpload(multipartFile, "order", "service", "E:/file", "F:\\file\\test");



         /*//读写文件
        try {
            FileInputStream fis = new FileInputStream(file);
            FileOutputStream fos = new FileOutputStream("E:/file/test.m4a");
            int len=0;
            //一次读取多少字节的文件,这里可以选择文件的所有字节长度
            byte[] b = new byte[fis.available()];
            while((len=fis.read(b))!=-1){
                //对字节进行排序
                Arrays.sort(b);
                fos.write(b,0,len);
                fos.flush();
            }
        }catch(FileNotFoundException e){
            log.error("异常信息：", e);
        }catch(IOException e){
            log.error("异常信息：", e);
        }*/

    }


}
