package com.mx.goldnurse.sms.service.impl;

import com.alibaba.fastjson.JSON;
import com.mx.goldnurse.sms.DoPostSms;
import com.mx.goldnurse.sms.service.DoPostSmsService;
import com.mx.goldnurse.utils.OrderThreadLocalUtil;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service("doPostSmsServiceCommon")
public class DoPostSmsServiceImpl implements DoPostSmsService {

    //阿里大于短信通道相关参数

    @Override
    public Boolean sendSms(String mobile, String contentCode, String param) {
        try {
            TaobaoClient client = new DefaultTaobaoClient(DoPostSms.ALIYUN_DYSMS_URL, DoPostSms.ALIYUN_DYSMS_APPKEY, DoPostSms.ALIYUN_DYSMS_SECRET);
            AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
            req.setExtend("123456");
            req.setSmsType("normal");
            req.setSmsFreeSignName("金牌护士");//短信签名，传入的短信签名必须是在阿里大于“管理中心-验证码/短信通知/推广短信-配置短信签名
            req.setSmsParamString(param);
            req.setRecNum(mobile);//短信接收号码。支持单个或多个手机号码，传入号码为11位手机号码，不能加0或+86。群发短信需传入多个号码，以英文逗号分隔
            req.setSmsTemplateCode(contentCode);//短信模板ID，传入的模板必须是在阿里大于“管理中心-短信模板管理”中的可用模板。
            AlibabaAliqinFcSmsNumSendResponse rsp;
            rsp = client.execute(req);
            Map<String, Object> map = (Map) JSON.parse(rsp.getBody());
            if (map != null && map.get("error_response") != null) {
                //Map<String,Object> msg = (Map)map.get("error_response");
                //String string = msg.get("sub_msg").toString();
                log.error("发送失败：" + rsp.getBody());
                return false;
            }
            log.info(rsp.getBody());
            return true;
        } catch (ApiException e) {
            // TODO Auto-generated catch block
            log.error("异常信息:", e);
            return false;
        } finally {
            OrderThreadLocalUtil.removeTokenUserId();
        }
    }
}
