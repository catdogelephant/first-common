package com.mx.goldnurse.dingtalk;

import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author erBai
 * @version 1.0.0
 * @ClassName NewD
 * @Description TODO
 * @data 2020/11/6 3:05 下午
 */
@Slf4j
@Component
public class DingTalkNotice {

    /**
     * 钉钉webHook地址
     */
    private static final String ding_talk_notice_url = "https://oapi.dingtalk.com/robot/send?access_token=";

//    @Value("${DingTalk.access_token}")
//    private void setDingTalkNoticeUrl(String accessToken){
//        ding_talk_notice_url = "https://oapi.dingtalk.com/robot/send?access_token=" + accessToken;
//    }
//
//    /** 关键字 */
//    private static String keyword;
//
//    @Value("${DingTalk.keyword}")
//    private void setKeyword(String keyword){
//        DingTalkNotice.keyword = "【" + keyword + "】";
//    }
//
//    /** 密钥 */
//    private static String TWO_SECRET;

    private static Boolean isAtAll;

    private static String accessToken;

    private static String keyword;

    /**
     * 发送文本消息 可单独@或者@所有人(需要管理员开启所有人都可以@)
     *
     * @param content     文本内容
     * @param isAtAll     是否@所有人
     * @param atMobiles   需要@的成员列表
     * @param keyword     关键字
     * @param accessToken token
     **/
    public static void sendTextMessage(String content, Boolean isAtAll,
                                       List<String> atMobiles, String keyword, String accessToken) {
        //消息内容
        Map<String, String> contentMap = new HashMap<>(1);
        contentMap.put("content", keyword + content);

        //通知人
        Map<String, Object> atMap = new HashMap<>(2);
        //1.是否通知所有人
        atMap.put("isAtAll", isAtAll);
        //2.通知具体人的手机号码列表
        atMap.put("atMobiles", atMobiles);

        Map<String, Object> reqMap = new HashMap<>(3);
        reqMap.put("msgtype", DingTalkNoticeType.TEXT.getValue());
        reqMap.put(DingTalkNoticeType.TEXT.getValue(), contentMap);
        reqMap.put("at", atMap);

        String body = HttpRequest.post(ding_talk_notice_url + accessToken).body(JSON.toJSONString(reqMap)).timeout(10000).execute().body();
        log.info(body);
    }

    /**
     * 发送文本消息 可单独@或者@所有人(需要管理员开启所有人都可以@)
     *
     * @param content   文本内容
     * @param atMobiles 需要@的成员列表
     **/
    public static void sendTextMessage(String content, List<String> atMobiles) {
        //消息内容
        Map<String, String> contentMap = new HashMap<>(1);
        contentMap.put("content", keyword + content);

        //通知人
        Map<String, Object> atMap = new HashMap<>(2);
        //1.是否通知所有人
        atMap.put("isAtAll", isAtAll);
        //2.通知具体人的手机号码列表
        atMap.put("atMobiles", atMobiles);

        Map<String, Object> reqMap = new HashMap<>(3);
        reqMap.put("msgtype", DingTalkNoticeType.TEXT.getValue());
        reqMap.put(DingTalkNoticeType.TEXT.getValue(), contentMap);
        reqMap.put("at", atMap);

        String body = HttpRequest.post(ding_talk_notice_url + accessToken).body(JSON.toJSONString(reqMap)).timeout(10000).execute().body();
        log.info(body);
    }

    /**
     * 发送卡片消息 不能@
     *
     * @param messageUrl 消息链接（点击卡片后可跳转）
     * @param picUrl     图片链接
     * @param title      标题
     * @param text       卡片文本
     * @Return: boolean
     * @Author: peishaopeng
     * @Date: 2020/8/9
     **/
    public static void sendLinkMessage(String messageUrl, String picUrl, String title, String text, String keyword, String accessToken) {

        Map<String, Object> reqMap = new HashMap<>(2);
        reqMap.put("msgtype", DingTalkNoticeType.LINK.getValue());
        //卡片信息
        Map<String, String> cardMap = new HashMap<>(4);
        cardMap.put("text", text);
        cardMap.put("title", keyword + title);
        cardMap.put("picUrl", picUrl);
        cardMap.put("messageUrl", messageUrl);
        reqMap.put(DingTalkNoticeType.LINK.getValue(), cardMap);

        String body = HttpRequest.post(ding_talk_notice_url + accessToken).body(JSON.toJSONString(reqMap)).timeout(10000).execute().body();
        log.info(body);
    }

    /**
     * 发送markdown消息
     *
     * @param title 标题
     * @param text  markdown内容
     * @Return: boolean
     * @Author: peishaopeng
     * @Date: 2020/8/9
     **/
    public static void sendMarkdownMessage(String title, String text, String keyword, String accessToken) {

        Map<String, Object> reqMap = new HashMap<>(2);
        reqMap.put("msgtype", DingTalkNoticeType.MARKDOWN.getValue());
        //markdown信息
        Map<String, String> markdownMap = new HashMap<>(2);

        markdownMap.put("title", keyword);
        markdownMap.put("text", "# " + title + "\n" + text);

        reqMap.put(DingTalkNoticeType.MARKDOWN.getValue(), markdownMap);
        System.out.println(JSON.toJSONString(reqMap));

        String body = HttpRequest.post(ding_talk_notice_url + accessToken).body(JSON.toJSONString(reqMap)).timeout(10000).execute().body();
        log.info(body);
    }

    @Value("${DingTalk.exceptionNotice.isAtAll}")
    private void setIsAtAll(Boolean isAtAll) {
        DingTalkNotice.isAtAll = isAtAll;
    }

    @Value("${DingTalk.exceptionNotice.accessToken}")
    private void setAccessToken(String accessToken) {
        DingTalkNotice.accessToken = accessToken;
    }

    @Value("${DingTalk.exceptionNotice.keyword}")
    private void setKeyword(String keyword) {
        DingTalkNotice.keyword = keyword;
    }
//
//    /** 发送消息 */
//    private static String send(String reqStr){
//        String body = HttpRequest.post(ding_talk_notice_url).body(reqStr).timeout(10000).execute().body();
//        log.info(body);
//        return body;
//    }


}
