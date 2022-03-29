package com.mx.goldnurse.sms.service;

/**
 * 短信发送
 * 请使用goldnurse-modules-work的服务发送WorkSmsConfigService
 * 短信发送包含护理服务中心逻辑
 * created date 2021/12/8 14:19
 *
 * @author JiangYuhao
 */
public interface DoPostSmsService {
    /**
     * 请使用goldnurse-modules-work的服务发送WorkSmsConfigService
     * 短信发送包含护理服务中心逻辑
     *
     * @param mobile
     * @param contentCode
     * @param param
     * @return
     */
    Boolean sendSms(String mobile, String contentCode, String param);
}
